package com.example.bookstore.controller;

import com.example.bookstore.dto.UserDTO;
import com.example.bookstore.entities.User;
import com.example.bookstore.exceptions.AccountNotVerifiedException;
import com.example.bookstore.exceptions.InvalidPasswordException;
import com.example.bookstore.mapper.UserMapper;
import com.example.bookstore.repository.UserRepository;
import com.example.bookstore.service.UserService;
import com.example.bookstore.validation.BasicValidation;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("appUsers")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    UserRepository userRepository;

    @PostMapping
    public ResponseEntity<?> create(@Validated(BasicValidation.class) @RequestBody UserDTO userDTO) {
        User userToCreate = UserMapper.userDTO2User(userDTO);
        User createdUser = userService.create(userToCreate);
        return ResponseEntity.ok(UserMapper.user2UserDTO(createdUser));
    }

    @PostMapping("/resend-verification")
    public ResponseEntity<?> resendVerificationCode(@RequestParam("email") String email) {
        User user = userService.resendVerificationCode(email);
        return ResponseEntity.ok("Verification code resent to " + user.getEmail());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getById(@PathVariable(name = "userId") Long userIdToSearchFor) {
        User foundUser = userService.getById(userIdToSearchFor);
        return ResponseEntity.ok(UserMapper.user2UserDTO(foundUser));
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users.stream()
                .map(UserMapper::user2UserDTO)
                .toList());
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateById(@PathVariable(name = "userId") Long userIdToUpdate, @RequestBody UserDTO userBody) {
        User userEntity = UserMapper.userDTO2User(userBody);
        User updatedUser = userService.updateById(userIdToUpdate, userEntity);
        return ResponseEntity.ok(UserMapper.user2UserDTO(updatedUser));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteById(@PathVariable(name = "userId") Long userIdToDelete) {
        userService.deleteById(userIdToDelete);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/verify")
    public ResponseEntity<?> verifyAccount(@RequestParam("userId") Long userId, @RequestParam("code") String code) {
        User verifiedUser = userService.verifyAccount(userId, code);
        return ResponseEntity.ok(UserMapper.user2UserDTO(verifiedUser));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO) throws InvalidPasswordException, AccountNotVerifiedException {
        User userToLogin = UserMapper.userDTO2User(userDTO);
        User loggedUser = userService.login(userToLogin.getEmail(), userToLogin.getPassword());
        return ResponseEntity.ok(loggedUser.getId());
    }
}
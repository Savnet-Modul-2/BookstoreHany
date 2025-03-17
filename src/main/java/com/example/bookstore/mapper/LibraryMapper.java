package com.example.bookstore.mapper;

import com.example.bookstore.dto.LibraryDTO;
import com.example.bookstore.entities.Library;

import java.util.ArrayList;

public class LibraryMapper {
    public static Library libraryDto2Library(LibraryDTO libraryDTO) {
        Library library = new Library();
        library.setName(libraryDTO.getName());
        library.setAddress(libraryDTO.getAddress());
        library.setPhoneNumber(libraryDTO.getPhoneNumber());
        return library;
    }

    public static LibraryDTO library2LibraryDto(Library library) {
        LibraryDTO libraryDTO = new LibraryDTO();
        libraryDTO.setId(library.getId());
        libraryDTO.setName(library.getName());
        libraryDTO.setAddress(library.getAddress());
        libraryDTO.setPhoneNumber(library.getPhoneNumber());
        return libraryDTO;
    }
}

package com.example.bookstore.unitTest.service;

import com.project.bookstore.entity.Book;
import com.project.bookstore.entity.Librarian;
import com.project.bookstore.entity.Library;
import com.project.bookstore.exceptions.EntityNotVerifiedException;
import com.project.bookstore.repository.BookRepository;
import com.project.bookstore.repository.LibraryRepository;
import com.project.bookstore.service.LibraryService;
import jakarta.persistence.EntityNotFoundException;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class LibraryServiceTests {
    @Mock
    private LibraryRepository libraryRepository;
    @Mock
    private BookRepository bookRepository;
    @InjectMocks
    private LibraryService libraryService;
    private Library testLibrary;

    @BeforeEach
    public void setUp() {
        testLibrary = new Library();
        testLibrary.setId(1L);
        testLibrary.setLibrarian(new Librarian());
    }

    @Test
    public void givenLibrarianId_FindById_ReturnLibrarian() {
        Mockito.when(libraryRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(testLibrary));

        Library foundLibrary = libraryService.findById(testLibrary.getId());
        AssertionsForClassTypes.assertThat(foundLibrary).isEqualTo(testLibrary);

        Mockito.verify(libraryRepository, Mockito.times(1)).findById(testLibrary.getId());
    }

    @Test
    public void givenWrongLibrarianId_FindById_ThrowException() {
        Mockito.when(libraryRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> libraryService.findById(testLibrary.getId()))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    public void givenNothing_FindAll_VerifyCalledMethod() {
        libraryService.findAll();

        Mockito.verify(libraryRepository).findAll();
    }

    @Test
    public void givenBookAndVerifiedAccount_AddBookToLibrary_ReturnContainsBook() {
        Book testBook = new Book();
        testLibrary.getLibrarian().setVerifiedAccount(true);
        Mockito.when(libraryRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(testLibrary));

        libraryService.addBookToLibrary(testLibrary.getId(), testBook);
        ArgumentCaptor<Library> libraryArgumentCaptor = ArgumentCaptor.forClass(Library.class);
        Mockito.verify(libraryRepository).save(libraryArgumentCaptor.capture());
        Library capturedLibrary = libraryArgumentCaptor.getValue();

        Assertions.assertThat(capturedLibrary.getBooks()).contains(testBook);
    }

    @Test
    public void givenBookAndUnverifiedAccount_AddBookToLibrary_ThrowException() {
        Book testBook = new Book();
        Mockito.when(libraryRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(testLibrary));

        Assertions.assertThatThrownBy(() -> libraryService.addBookToLibrary(testLibrary.getId(), testBook))
                .isInstanceOf(EntityNotVerifiedException.class)
                .hasMessageContaining("The user's account is not verified to perform this action");
        Mockito.verify(libraryRepository, Mockito.never()).save(testLibrary);
    }

    @Test
    public void givenWrongLibraryId_AddBookToLibrary_ThrowException() {
        Book testBook = new Book();
        Mockito.when(libraryRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> libraryService.addBookToLibrary(testLibrary.getId(), testBook))
                .isInstanceOf(EntityNotFoundException.class);
        Mockito.verify(libraryRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    public void givenExistingBook_AddExistingBookToLibrary_ReturnContainsBook() {
        Book testBook = new Book();
        testLibrary.getLibrarian().setVerifiedAccount(true);
        Mockito.when(bookRepository.findById(testBook.getId())).thenReturn(Optional.of(testBook));
        Mockito.when(libraryRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(testLibrary));

        libraryService.addExistingBookToLibrary(testLibrary.getId(), testBook.getId());
        ArgumentCaptor<Library> libraryArgumentCaptor = ArgumentCaptor.forClass(Library.class);
        Mockito.verify(libraryRepository).save(libraryArgumentCaptor.capture());
        Library capturedLibrary = libraryArgumentCaptor.getValue();

        Assertions.assertThat(capturedLibrary.getBooks()).contains(testBook);
    }

    @Test
    public void givenBookAndUnverifiedAccount_AddExistingBookToLibrary_ThrowException() {
        Book testBook = new Book();
        Mockito.when(bookRepository.findById(testBook.getId())).thenReturn(Optional.of(testBook));
        Mockito.when(libraryRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(testLibrary));

        Assertions.assertThatThrownBy(() -> libraryService.addExistingBookToLibrary(testLibrary.getId(), testBook.getId()))
                .isInstanceOf(EntityNotVerifiedException.class)
                .hasMessageContaining("The user's account is not verified to perform this action");
    }

    @Test
    public void givenLibrary_UpdateLibrary_ReturnLibrary() {
        Library newLibrary = new Library();
        newLibrary.setId(1L);
        newLibrary.setName("newLibraryName");
        Mockito.when(libraryRepository.findById(testLibrary.getId())).thenReturn(Optional.of(testLibrary));

        libraryService.updateLibrary(testLibrary.getId(), newLibrary);
        ArgumentCaptor<Library> libraryArgumentCaptor = ArgumentCaptor.forClass(Library.class);
        Mockito.verify(libraryRepository).save(libraryArgumentCaptor.capture());
        Library capturedLibrary = libraryArgumentCaptor.getValue();

        AssertionsForClassTypes.assertThat(capturedLibrary).isEqualTo(testLibrary);
    }
}

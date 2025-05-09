package com.example.bookstore.unitTest.service;

import com.project.bookstore.entity.Book;
import com.project.bookstore.entity.Library;
import com.project.bookstore.repository.BookRepository;
import com.project.bookstore.repository.LibraryRepository;
import com.project.bookstore.service.BookService;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class BookServiceTests {
    @Mock
    private BookRepository bookRepository;
    @Mock
    private LibraryRepository libraryRepository;
    @InjectMocks
    private BookService bookService;
    private Book testBook;
    private List<Book> testBookList;

    @BeforeEach
    public void setUp() {
        testBook = new Book();
        testBook.setId(1L);
        testBook.setTitle("testTitle");
        testBook.setAuthor("testAuthor");
        testBookList = List.of(testBook);
    }

    @Test
    public void givenBook_CreateBook_ReturnBook() {
        bookService.createBook(testBook);

        ArgumentCaptor<Book> bookArgumentCaptor = ArgumentCaptor.forClass(Book.class);
        Mockito.verify(bookRepository).save(bookArgumentCaptor.capture());
        Book capturedBook = bookArgumentCaptor.getValue();

        AssertionsForClassTypes.assertThat(capturedBook).isEqualTo(testBook);
    }

    @Test
    public void givenLibraryId_AddBookToLibrary_ReturnBook() {
        Library testLibrary = new Library();
        testLibrary.setId(1L);
        Mockito.when(libraryRepository.findById(testLibrary.getId())).thenReturn(Optional.of(testLibrary));

        bookService.addBookToLibrary(testLibrary.getId(), testBook);
        ArgumentCaptor<Book> bookArgumentCaptor = ArgumentCaptor.forClass(Book.class);
        Mockito.verify(bookRepository).save(bookArgumentCaptor.capture());
        Book capturedBook = bookArgumentCaptor.getValue();

        Assertions.assertThat(capturedBook).isEqualTo(testBook);
    }

    @Test
    public void givenWrongLibraryId_AddBookToLibrary_ThrowException() {
        Mockito.when(libraryRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> bookService.addBookToLibrary(Mockito.anyLong(), testBook))
                .isInstanceOf(EntityNotFoundException.class);
        Mockito.verify(libraryRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    public void givenBookId_FindById_ReturnBook() {
        Mockito.when(bookRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(testBook));

        Book foundBook = bookService.findById(testBook.getId());

        AssertionsForClassTypes.assertThat(foundBook).isEqualTo(testBook);
        Mockito.verify(bookRepository, Mockito.times(1)).findById(testBook.getId());
    }

    @Test
    public void givenWrongBookId_FindById_ThrowException() {
        Mockito.when(bookRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> bookService.findById(testBook.getId()))
                .isInstanceOf(EntityNotFoundException.class);
        Mockito.verify(bookRepository, Mockito.times(1)).findById(testBook.getId());
    }

    @Test
    public void givenNothing_FindAll_VerifyCalledMethod() {
        bookService.findAll();

        Mockito.verify(bookRepository).findAll();
    }

    @Test
    public void givenPagination_FindAll_VerifyMethodCalled() {
        Pageable testPage = PageRequest.of(0, testBookList.size());
        Mockito.when(bookRepository.findAll(testPage))
                .thenReturn(new PageImpl<>(testBookList, testPage, testBookList.size()));

        bookService.findAll(testPage);

        Mockito.verify(bookRepository).findAll(testPage);
    }

    @Test
    public void givenBookId_DeleteById_VerifyCalledMethod() {
        bookService.deleteById(testBook.getId());

        Mockito.verify(bookRepository).deleteById(testBook.getId());
    }

    @Test
    public void givenBook_UpdateBook_ReturnBook() {
        Book newBook = new Book();
        newBook.setId(1L);
        newBook.setTitle("newTestTitle");
        newBook.setAuthor("newTestAuthor");
        Mockito.when(bookRepository.findById(testBook.getId())).thenReturn(Optional.of(testBook));

        bookService.updateBook(newBook.getId(), newBook);
        ArgumentCaptor<Book> bookArgumentCaptor = ArgumentCaptor.forClass(Book.class);
        Mockito.verify(bookRepository).save(bookArgumentCaptor.capture());
        Book capturedBook = bookArgumentCaptor.getValue();

        AssertionsForClassTypes.assertThat(capturedBook).isEqualTo(testBook);
    }

    @Test
    public void givenPagination_FindAll_ReturnIsNotEmpty() {
        Pageable testPage = PageRequest.of(0, testBookList.size());
        Mockito.when(bookRepository.findAll(testPage))
                .thenReturn(new PageImpl<>(testBookList, testPage, testBookList.size()));

        Page<Book> page = bookRepository.findAll(testPage);

        Assertions.assertThat(page).isNotEmpty();
        Mockito.verify(bookRepository).findAll(testPage);
    }

    @Test
    public void givenTitleAndAuthor_FindBooks_ReturnIsNotEmpty() {
        Pageable testPage = PageRequest.of(0, testBookList.size());
        Mockito.when(bookRepository.findBooks(testBook.getTitle(), testBook.getAuthor(), testPage))
                .thenReturn(new PageImpl<>(testBookList, testPage, testBookList.size()));

        Page<Book> page = bookService.findBooks(testBook.getTitle(), testBook.getAuthor(), testPage);

        Assertions.assertThat(page).isNotEmpty();
        Mockito.verify(bookRepository).findBooks(testBook.getTitle(), testBook.getAuthor(), testPage);
    }
}

package controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;

import model.Book;
import dao.BookDAO;

import java.util.Arrays;
import java.util.List;

@RunWith(org.mockito.junit.MockitoJUnitRunner.class)
public class BookControllerTest {
    @Mock
    private BookDAO bookDAO;

    @InjectMocks
    private BookController bookController;

    @Test
    public void testGetAllBooks() {
        List<Book> books = Arrays.asList(
            new Book(1, "Book 1", "Author 1", "1234567890", true),
            new Book(2, "Book 2", "Author 2", "0987654321", false)
        );

        when(bookDAO.getAllBooks()).thenReturn(books);

        List<Book> result = bookController.getAllBooks();
        assertEquals(2, result.size());
        verify(bookDAO, times(1)).getAllBooks();
    }

    @Test
    public void testGetBookById() {
        Book book = new Book(1, "Book 1", "Author 1", "1234567890", true);

        when(bookDAO.getBookById(1)).thenReturn(book);

        Book result = bookController.getBookById(1);
        assertEquals("Book 1", result.getTitle());
        verify(bookDAO, times(1)).getBookById(1);
    }

    @Test
    public void testAddBook() {
        Book book = new Book(1, "Book 1", "Author 1", "1234567890", true);

        doNothing().when(bookDAO).addBook(book);

        bookController.addBook(book);
        verify(bookDAO, times(1)).addBook(book);
    }

    @Test
    public void testUpdateBook() {
        Book book = new Book(1, "Book 1", "Author 1", "1234567890", true);

        doNothing().when(bookDAO).updateBook(book);

        bookController.updateBook(book);
        verify(bookDAO, times(1)).updateBook(book);
    }

    @Test
    public void testDeleteBook() {
        int bookId = 1;

        doNothing().when(bookDAO).deleteBook(bookId);

        bookController.deleteBook(bookId);
        verify(bookDAO, times(1)).deleteBook(bookId);
    }
}

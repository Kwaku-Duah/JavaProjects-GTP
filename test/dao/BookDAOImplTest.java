package dao;

import model.Book;
import database.DatabaseManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.MockedStatic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class BookDAOImplTest {

    private BookDAOImpl bookDAO;
    private AutoCloseable closeable;
    private MockedStatic<DatabaseManager> databaseManagerMock;

    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockPreparedStatement;
    @Mock
    private ResultSet mockResultSet;

    @Before
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        bookDAO = new BookDAOImpl();
        databaseManagerMock = mockStatic(DatabaseManager.class);
        databaseManagerMock.when(DatabaseManager::getConnection).thenReturn(mockConnection);
    }

    @After
    public void tearDown() throws Exception {
        closeable.close();
        databaseManagerMock.close();
    }

    @Test
    public void testGetAllBooks() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getInt("id")).thenReturn(1, 2);
        when(mockResultSet.getString("title")).thenReturn("Book1", "Book2");
        when(mockResultSet.getString("author")).thenReturn("Author1", "Author2");
        when(mockResultSet.getString("isbn")).thenReturn("ISBN1", "ISBN2");
        when(mockResultSet.getBoolean("isAvailable")).thenReturn(true, false);

        List<Book> books = bookDAO.getAllBooks();
        assertEquals(2, books.size());

        verify(mockPreparedStatement, times(1)).executeQuery();
        verify(mockResultSet, times(3)).next();
    }

    @Test
    public void testGetBookById() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getString("title")).thenReturn("Book1");
        when(mockResultSet.getString("author")).thenReturn("Author1");
        when(mockResultSet.getString("isbn")).thenReturn("ISBN1");
        when(mockResultSet.getBoolean("isAvailable")).thenReturn(true);

        Book book = bookDAO.getBookById(1);
        assertNotNull(book);
        assertEquals("Book1", book.getTitle());

        verify(mockPreparedStatement, times(1)).setInt(1, 1);
        verify(mockPreparedStatement, times(1)).executeQuery();
        verify(mockResultSet, times(1)).next();
    }

    @Test
    public void testAddBook() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        Book book = new Book(1, "Book1", "Author1", "ISBN1", true);
        bookDAO.addBook(book);

        verify(mockPreparedStatement, times(1)).setString(1, book.getTitle());
        verify(mockPreparedStatement, times(1)).setString(2, book.getAuthor());
        verify(mockPreparedStatement, times(1)).setString(3, book.getIsbn());
        verify(mockPreparedStatement, times(1)).setBoolean(4, book.isAvailable());
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void testUpdateBook() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        Book book = new Book(1, "UpdatedBook", "UpdatedAuthor", "UpdatedISBN", false);
        bookDAO.updateBook(book);

        verify(mockPreparedStatement, times(1)).setString(1, book.getTitle());
        verify(mockPreparedStatement, times(1)).setString(2, book.getAuthor());
        verify(mockPreparedStatement, times(1)).setString(3, book.getIsbn());
        verify(mockPreparedStatement, times(1)).setBoolean(4, book.isAvailable());
        verify(mockPreparedStatement, times(1)).setInt(5, book.getId());
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }



    @Test
    public void testSearchBooks() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getInt("id")).thenReturn(1, 2);
        when(mockResultSet.getString("title")).thenReturn("Book1", "Book2");
        when(mockResultSet.getString("author")).thenReturn("Author1", "Author2");
        when(mockResultSet.getString("isbn")).thenReturn("ISBN1", "ISBN2");
        when(mockResultSet.getBoolean("isAvailable")).thenReturn(true, false);

        List<Book> books = bookDAO.searchBooks("Book");
        assertEquals(2, books.size());

        verify(mockPreparedStatement, times(1)).setString(1, "%Book%");
        verify(mockPreparedStatement, times(1)).setString(2, "%Book%");
        verify(mockPreparedStatement, times(1)).executeQuery();
        verify(mockResultSet, times(3)).next();
    }
}

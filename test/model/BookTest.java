package model;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

public class BookTest {

    private Book book;

    @Before
    public void setUp() {
        book = new Book(1, "Effective Java", "Joshua Bloch", "978-0134685991", true);
    }

    @Test
    public void testGettersAndSetters() {
        // Test getTitle and setTitle
        assertEquals("Effective Java", book.getTitle());
        book.setTitle("Clean Code");
        assertEquals("Clean Code", book.getTitle());

        // Test getAuthor and setAuthor
        assertEquals("Joshua Bloch", book.getAuthor());
        book.setAuthor("Robert C. Martin");
        assertEquals("Robert C. Martin", book.getAuthor());

        // Test getIsbn and setIsbn
        assertEquals("978-0134685991", book.getIsbn());
        book.setIsbn("978-0132350884");
        assertEquals("978-0132350884", book.getIsbn());

        // Test isAvailable and setIsAvailable (inherited from LibraryItem)
        assertTrue(book.isAvailable());
        book.setAvailable(false);
        assertFalse(book.isAvailable());
    }

    @Test
    public void testBorrowHistory() {
        // Test getBorrowHistory
        LinkedList<String> history = book.getBorrowHistory();
        assertTrue(history.isEmpty());

        // Test addBorrowDate and getBorrowHistory
        book.addBorrowDate("2024-07-01");
        book.addBorrowDate("2024-07-15");
        
        history = book.getBorrowHistory();
        assertEquals(2, history.size());
        assertEquals("2024-07-01", history.get(0));
        assertEquals("2024-07-15", history.get(1));
    }
}

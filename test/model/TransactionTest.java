package model;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

public class TransactionTest {
    private Transaction transaction;

    @Before
    public void setUp() {
        transaction = new Transaction(1, 101, 201, LocalDate.of(2023, 7, 1), LocalDate.of(2023, 7, 15));
    }

    @Test
    public void testGettersAndSetters() {
        // Test id
        assertEquals(1, transaction.getId());
        transaction.setId(2);
        assertEquals(2, transaction.getId());

        // Test bookId
        assertEquals(101, transaction.getBookId());
        transaction.setBookId(102);
        assertEquals(102, transaction.getBookId());

        // Test patronId
        assertEquals(201, transaction.getPatronId());
        transaction.setPatronId(202);
        assertEquals(202, transaction.getPatronId());

        // Test issueDate
        assertEquals(LocalDate.of(2023, 7, 1), transaction.getIssueDate());
        transaction.setIssueDate(LocalDate.of(2023, 7, 2));
        assertEquals(LocalDate.of(2023, 7, 2), transaction.getIssueDate());

        // Test returnDate
        assertEquals(LocalDate.of(2023, 7, 15), transaction.getReturnDate());
        transaction.setReturnDate(LocalDate.of(2023, 7, 16));
        assertEquals(LocalDate.of(2023, 7, 16), transaction.getReturnDate());
    }
}

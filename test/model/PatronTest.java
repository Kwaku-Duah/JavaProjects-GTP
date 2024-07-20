package model;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Stack;
import java.util.Queue;

public class PatronTest {
    private Patron patron;

    @Before
    public void setUp() {
        patron = new Patron(1, "John Doe", "johndoe@example.com", "1234567890");
    }

    @Test
    public void testGettersAndSetters() {
        // Test id
        assertEquals(1, patron.getId());
        patron.setId(2);
        assertEquals(2, patron.getId());

        // Test name
        assertEquals("John Doe", patron.getName());
        patron.setName("Jane Doe");
        assertEquals("Jane Doe", patron.getName());

        // Test email
        assertEquals("johndoe@example.com", patron.getEmail());
        patron.setEmail("janedoe@example.com");
        assertEquals("janedoe@example.com", patron.getEmail());

        // Test phone
        assertEquals("1234567890", patron.getPhone());
        patron.setPhone("0987654321");
        assertEquals("0987654321", patron.getPhone());
    }

    @Test
    public void testRecentActivities() {
        // Test adding activities
        patron.addActivity("Checked out a book");
        patron.addActivity("Attended a workshop");

        Stack<String> activities = patron.getRecentActivities();
        assertEquals(2, activities.size());
        assertEquals("Attended a workshop", activities.peek());

        // Test getting the last activity
        assertEquals("Attended a workshop", patron.getLastActivity());
        assertEquals(1, activities.size());
        assertEquals("Checked out a book", patron.getLastActivity());
        assertEquals(0, activities.size());

        // Test getting the last activity when there are no activities
        assertEquals("No recent activities", patron.getLastActivity());
    }

    @Test
    public void testBookRequests() {
        // Test requesting books
        patron.requestBook("The Catcher in the Rye");
        patron.requestBook("To Kill a Mockingbird");

        Queue<String> requests = patron.getBookRequests();
        assertEquals(2, requests.size());
        assertEquals("The Catcher in the Rye", requests.peek());

        // Test getting the next book request
        assertEquals("The Catcher in the Rye", patron.getNextBookRequest());
        assertEquals(1, requests.size());
        assertEquals("To Kill a Mockingbird", patron.getNextBookRequest());
        assertEquals(0, requests.size());

        // Test getting the next book request when there are no requests
        assertEquals("No book requests", patron.getNextBookRequest());
    }

    @Test
    public void testToString() {
        String expected = "Patron{id=1, name='John Doe', email='johndoe@example.com', phone='1234567890'}";
        assertEquals(expected, patron.toString());
    }
}

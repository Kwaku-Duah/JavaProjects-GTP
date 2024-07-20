package model;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class LibraryItemTest {
    private LibraryItem libraryItem;

    @Before
    public void setUp() {
        libraryItem = new LibraryItem(1, true);
    }

    @Test
    public void testGettersAndSetters() {
        // Test id
        assertEquals(1, libraryItem.getId());
        libraryItem.setId(2);
        assertEquals(2, libraryItem.getId());

        // Test availability
        assertTrue(libraryItem.isAvailable());
        libraryItem.setAvailable(false);
        assertFalse(libraryItem.isAvailable());
    }
}

package controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;

import model.Patron;
import dao.PatronDAO;

import java.util.Arrays;
import java.util.List;

@RunWith(org.mockito.junit.MockitoJUnitRunner.class)
public class PatronControllerTest {
    @Mock
    private PatronDAO patronDAO;

    @InjectMocks
    private PatronController patronController;

    @Test
    public void testGetAllPatrons() {
        List<Patron> patrons = Arrays.asList(
            new Patron(1, "Patron 1", "Address 1", "email1@example.com"),
            new Patron(2, "Patron 2", "Address 2", "email2@example.com")
        );

        when(patronDAO.getAllPatrons()).thenReturn(patrons);

        List<Patron> result = patronController.getAllPatrons();
        assertEquals(2, result.size());
        verify(patronDAO, times(1)).getAllPatrons();
    }

    @Test
    public void testGetPatronById() {
        Patron patron = new Patron(1, "Patron 1", "Address 1", "email1@example.com");

        when(patronDAO.getPatronById(1)).thenReturn(patron);

        Patron result = patronController.getPatronById(1);
        assertEquals("Patron 1", result.getName());
        verify(patronDAO, times(1)).getPatronById(1);
    }

    @Test
    public void testAddPatron() {
        Patron patron = new Patron(1, "Patron 1", "Address 1", "email1@example.com");

        doNothing().when(patronDAO).addPatron(patron);

        patronController.addPatron(patron);
        verify(patronDAO, times(1)).addPatron(patron);
    }

    @Test
    public void testUpdatePatron() {
        Patron patron = new Patron(1, "Patron 1", "Address 1", "email1@example.com");

        doNothing().when(patronDAO).updatePatron(patron);

        patronController.updatePatron(patron);
        verify(patronDAO, times(1)).updatePatron(patron);
    }

    @Test
    public void testDeletePatron() {
        int patronId = 1;

        doNothing().when(patronDAO).deletePatron(patronId);

        patronController.deletePatron(patronId);
        verify(patronDAO, times(1)).deletePatron(patronId);
    }
}

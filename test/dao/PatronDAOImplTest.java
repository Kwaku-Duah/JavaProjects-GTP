package dao;

import model.Patron;
import database.DatabaseManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.MockedStatic;
import org.mockito.junit.MockitoJUnitRunner;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runners.Parameterized.Parameter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PatronDAOImplTest {

    private PatronDAOImpl patronDAO;
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
        patronDAO = new PatronDAOImpl();
        databaseManagerMock = mockStatic(DatabaseManager.class);
        databaseManagerMock.when(DatabaseManager::getConnection).thenReturn(mockConnection);
    }

    @After
    public void tearDown() throws Exception {
        closeable.close();
        databaseManagerMock.close();
    }

    @Test
    public void testGetAllPatrons() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getInt("id")).thenReturn(1, 2);
        when(mockResultSet.getString("name")).thenReturn("Patron1", "Patron2");
        when(mockResultSet.getString("email")).thenReturn("patron1@example.com", "patron2@example.com");
        when(mockResultSet.getString("phone")).thenReturn("1234567890", "0987654321");

        List<Patron> patrons = patronDAO.getAllPatrons();
        assertEquals(2, patrons.size());

        verify(mockPreparedStatement, times(1)).executeQuery();
        verify(mockResultSet, times(3)).next();
    }

    @Test
    public void testGetPatronById() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getString("name")).thenReturn("Patron1");
        when(mockResultSet.getString("email")).thenReturn("patron1@example.com");
        when(mockResultSet.getString("phone")).thenReturn("1234567890");

        Patron patron = patronDAO.getPatronById(1);
        assertNotNull(patron);
        assertEquals("Patron1", patron.getName());

        verify(mockPreparedStatement, times(1)).setInt(1, 1);
        verify(mockPreparedStatement, times(1)).executeQuery();
        verify(mockResultSet, times(1)).next();
    }

    @RunWith(Parameterized.class)
    public static class AddPatronParameterizedTest {

        private PatronDAOImpl patronDAO;
        private AutoCloseable closeable;
        private MockedStatic<DatabaseManager> databaseManagerMock;

        @Mock
        private Connection mockConnection;
        @Mock
        private PreparedStatement mockPreparedStatement;

        @Parameter(0)
        public String name;
        @Parameter(1)
        public String email;
        @Parameter(2)
        public String phone;

        @Before
        public void setUp() {
            closeable = MockitoAnnotations.openMocks(this);
            patronDAO = new PatronDAOImpl();
            databaseManagerMock = mockStatic(DatabaseManager.class);
            databaseManagerMock.when(DatabaseManager::getConnection).thenReturn(mockConnection);
        }

        @After
        public void tearDown() throws Exception {
            closeable.close();
            databaseManagerMock.close();
        }

        @Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Patron1", "patron1@example.com", "1234567890"},
                    {"Patron2", "patron2@example.com", "0987654321"},
                    {"Patron3", "patron3@example.com", "1122334455"}
            });
        }

        @Test
        public void testAddPatron() throws SQLException {
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

            Patron patron = new Patron(1, name, email, phone);
            patronDAO.addPatron(patron);

            verify(mockPreparedStatement, times(1)).setString(1, patron.getName());
            verify(mockPreparedStatement, times(1)).setString(2, patron.getEmail());
            verify(mockPreparedStatement, times(1)).setString(3, patron.getPhone());
            verify(mockPreparedStatement, times(1)).executeUpdate();
        }
    }

    @RunWith(Parameterized.class)
    public static class UpdatePatronParameterizedTest {

        private PatronDAOImpl patronDAO;
        private AutoCloseable closeable;
        private MockedStatic<DatabaseManager> databaseManagerMock;

        @Mock
        private Connection mockConnection;
        @Mock
        private PreparedStatement mockPreparedStatement;

        @Parameter(0)
        public int id;
        @Parameter(1)
        public String name;
        @Parameter(2)
        public String email;
        @Parameter(3)
        public String phone;

        @Before
        public void setUp() {
            closeable = MockitoAnnotations.openMocks(this);
            patronDAO = new PatronDAOImpl();
            databaseManagerMock = mockStatic(DatabaseManager.class);
            databaseManagerMock.when(DatabaseManager::getConnection).thenReturn(mockConnection);
        }

        @After
        public void tearDown() throws Exception {
            closeable.close();
            databaseManagerMock.close();
        }

        @Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {1, "UpdatedPatron1", "updated1@example.com", "1234567890"},
                    {2, "UpdatedPatron2", "updated2@example.com", "0987654321"},
                    {3, "UpdatedPatron3", "updated3@example.com", "1122334455"}
            });
        }

        @Test
        public void testUpdatePatron() throws SQLException {
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

            Patron patron = new Patron(id, name, email, phone);
            patronDAO.updatePatron(patron);

            verify(mockPreparedStatement, times(1)).setString(1, patron.getName());
            verify(mockPreparedStatement, times(1)).setString(2, patron.getEmail());
            verify(mockPreparedStatement, times(1)).setString(3, patron.getPhone());
            verify(mockPreparedStatement, times(1)).setInt(4, patron.getId());
            verify(mockPreparedStatement, times(1)).executeUpdate();
        }
    }

    @Test
    public void testDeletePatron() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        patronDAO.deletePatron(1);

        verify(mockPreparedStatement, times(1)).setInt(1, 1);
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }
// regression tests
    @Test
    public void testGetPatronById_NotFound() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        Patron patron = patronDAO.getPatronById(1);
        assertNull(patron);

        verify(mockPreparedStatement, times(1)).setInt(1, 1);
        verify(mockPreparedStatement, times(1)).executeQuery();
        verify(mockResultSet, times(1)).next();
    }
}

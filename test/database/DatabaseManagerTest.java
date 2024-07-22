package database;

import static org.junit.Assert.*;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManagerTest {

    @Test
    public void testGetConnection_Success() {
        try {
            Connection connection = DatabaseManager.getConnection();
            assertNotNull("Connection should not be null", connection);
            connection.close();
        } catch (SQLException e) {
            fail("SQLException should not be thrown: " + e.getMessage());
        }
    }



    @Test
    public void testGetConnection_Properties() {
        try (MockedStatic<DriverManager> mockedDriverManager = Mockito.mockStatic(DriverManager.class)) {
            mockedDriverManager.when(() -> DriverManager.getConnection(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
                    .thenAnswer(invocation -> {
                        String url = invocation.getArgument(0);
                        String user = invocation.getArgument(1);
                        String password = invocation.getArgument(2);

                        assertEquals("jdbc:postgresql://localhost:5432/library", url);
                        assertEquals("postgres", user);
                        assertEquals("duaSHKH!229", password);

                        return Mockito.mock(Connection.class);
                    });

            Connection connection = DatabaseManager.getConnection();
            assertNotNull("Connection should not be null", connection);
        } catch (SQLException e) {
            fail("SQLException should not be thrown: " + e.getMessage());
        }
    }
}

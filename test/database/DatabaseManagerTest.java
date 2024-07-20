package database;

import static org.junit.Assert.*;
import org.junit.Test;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseManagerTest {

    @Test
    public void testGetConnection() {
        try {
            Connection connection = DatabaseManager.getConnection();
            assertNotNull("Connection should not be null", connection);
            connection.close(); 
        } catch (SQLException e) {
            fail("SQLException should not be thrown: " + e.getMessage());
        }
    }
}

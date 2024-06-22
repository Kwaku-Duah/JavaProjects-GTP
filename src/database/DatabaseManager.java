package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private static final String URL = "jdbc:postgresql://localhost:5432/library";
    private static final String USER = "postgres";
    private static final String PASSWORD = "duaSHKH!229";

    
    /** 
     * @return Connection
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
/**3NF Check
Book: All attributes are dependent only on the primary key (id). No transitive dependencies.
Patron: All attributes are dependent only on the primary key (id). No transitive dependencies. */
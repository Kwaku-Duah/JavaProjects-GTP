import java.sql.*;

public class App {
     static final String DB_URL = "jdbc:postgresql://localhost:5432/projectjava";
    static final String USER = "postgres";
    static final String PASS = "duaSHKH!229";
    static final String QUERY = "SELECT * FROM stud";


    
    /** 
     * @param args
     */
    public static void main(String[] args){
        // opening a connection
        try (
            Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(QUERY);
        ) {
            while (rs.next()){
                // Retrieve By Column Name
                System.out.print("Roll: " + rs.getString("roll"));
                System.out.print(", Name: " + rs.getString("name"));
                System.out.print(", CGPA: " + rs.getString("cgpa"));
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
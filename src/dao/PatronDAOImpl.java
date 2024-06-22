package dao;

import model.Patron;
import database.DatabaseManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class PatronDAOImpl implements PatronDAO {

    // Use LinkedList for managing patrons
    private LinkedList<Patron> patronsLinkedList;

    public PatronDAOImpl() {
        this.patronsLinkedList = new LinkedList<>(); 
    }

    /**
     * Retrieve all patrons from the database and store them in a LinkedList.
     *
     * @return List<Patron> containing all patrons from the database
     */
    @Override
    public List<Patron> getAllPatrons() {
        patronsLinkedList.clear(); // Clear existing data
        try (Connection conn = DatabaseManager.getConnection()) {
            String query = "SELECT * FROM Patron";
            try (PreparedStatement stmt = conn.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Patron patron = new Patron(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("phone")
                    );
                    patronsLinkedList.add(patron);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new LinkedList<>(patronsLinkedList); // Return as LinkedList
    }

    @Override
    public Patron getPatronById(int id) {
        Patron patron = null;
        try (Connection conn = DatabaseManager.getConnection()) {
            String query = "SELECT * FROM Patron WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        patron = new Patron(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getString("email"),
                                rs.getString("phone")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patron;
    }

    @Override
    public void addPatron(Patron patron) {
        try (Connection conn = DatabaseManager.getConnection()) {
            String query = "INSERT INTO Patron (name, email, phone) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, patron.getName());
                stmt.setString(2, patron.getEmail());
                stmt.setString(3, patron.getPhone());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updatePatron(Patron patron) {
        try (Connection conn = DatabaseManager.getConnection()) {
            String query = "UPDATE Patron SET name = ?, email = ?, phone = ? WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, patron.getName());
                stmt.setString(2, patron.getEmail());
                stmt.setString(3, patron.getPhone());
                stmt.setInt(4, patron.getId());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletePatron(int id) {
        try (Connection conn = DatabaseManager.getConnection()) {
            String query = "DELETE FROM Patron WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

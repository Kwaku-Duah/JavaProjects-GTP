package dao;

import model.Book;
import database.DatabaseManager;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList; // Import LinkedList
import java.util.List;

public class BookDAOImpl implements BookDAO {

    // Use LinkedList for managing books
    private LinkedList<Book> booksLinkedList;

    public BookDAOImpl() {
        this.booksLinkedList = new LinkedList<>(); 
    }

    /**
     * Retrieve all books from the database and store them in a LinkedList.
     *
     * @return List<Book> containing all books from the database
     */
    @Override
    public List<Book> getAllBooks() {
        booksLinkedList.clear(); // Clear existing data
        try (Connection conn = DatabaseManager.getConnection()) {
            String query = "SELECT * FROM Book";
            try (PreparedStatement stmt = conn.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Book book = new Book(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("author"),
                            rs.getString("isbn"),
                            rs.getBoolean("isAvailable"));
                    booksLinkedList.add(book); // Add book to LinkedList
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new LinkedList<>(booksLinkedList); // Return as LinkedList
    }

    @Override
    public Book getBookById(int id) {
        Book book = null;
        try (Connection conn = DatabaseManager.getConnection()) {
            String query = "SELECT * FROM Book WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        book = new Book(
                                rs.getInt("id"),
                                rs.getString("title"),
                                rs.getString("author"),
                                rs.getString("isbn"),
                                rs.getBoolean("isAvailable"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return book;
    }

    @Override
    public void addBook(Book book) {
        try (Connection conn = DatabaseManager.getConnection()) {
            String query = "INSERT INTO Book (title, author, isbn, isAvailable) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, book.getTitle());
                stmt.setString(2, book.getAuthor());
                stmt.setString(3, book.getIsbn());
                stmt.setBoolean(4, book.isAvailable());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateBook(Book book) {
        try (Connection conn = DatabaseManager.getConnection()) {
            String query = "UPDATE Book SET title = ?, author = ?, isbn = ?, isAvailable = ? WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, book.getTitle());
                stmt.setString(2, book.getAuthor());
                stmt.setString(3, book.getIsbn());
                stmt.setBoolean(4, book.isAvailable());
                stmt.setInt(5, book.getId());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteBook(int id) {
        try (Connection conn = DatabaseManager.getConnection()) {
            // Retrieve the book to check availability
            Book bookToDelete = getBookById(id);
            if (bookToDelete == null) {
                System.out.println("Book with ID " + id + " does not exist.");
                return;
            }

            // Check if the book is available
            if (!bookToDelete.isAvailable()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Cannot Delete Book");
                alert.setHeaderText(null);
                alert.setContentText("Cannot delete book with ID " + id + ". Book has been borrowed.");
                alert.show();
                return;
            }

            // Proceed with deletion if the book is available
            String query = "DELETE FROM Book WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void borrowBook(int id) {
        try (Connection conn = DatabaseManager.getConnection()) {
            String checkQuery = "SELECT isAvailable FROM Book WHERE id = ?";
            boolean currentAvailability = false;
            try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
                checkStmt.setInt(1, id);
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next()) {
                        currentAvailability = rs.getBoolean("isAvailable");
                    }
                }
            }

            if (currentAvailability) {
                String updateQuery = "UPDATE Book SET isAvailable = false WHERE id = ?";
                try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                    updateStmt.setInt(1, id);
                    updateStmt.executeUpdate();

                    // Show confirmation message
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Book Borrowed");
                    alert.setHeaderText(null);
                    alert.setContentText("Book with ID " + id + " is borrowed!");
                    alert.showAndWait();
                }
            } else {
                throw new IllegalStateException("Book with ID " + id + " is not available for borrowing.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Book> searchBooks(String searchText) {
        List<Book> foundBooks = new ArrayList<>();
        try (Connection conn = DatabaseManager.getConnection()) {
            if (searchText.isEmpty()) {
                return getAllBooks(); 
            }
            String query = "SELECT * FROM Book WHERE title LIKE ? OR author LIKE ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, "%" + searchText + "%");
                stmt.setString(2, "%" + searchText + "%");
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        Book book = new Book(
                                rs.getInt("id"),
                                rs.getString("title"),
                                rs.getString("author"),
                                rs.getString("isbn"),
                                rs.getBoolean("isAvailable"));
                        foundBooks.add(book);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return foundBooks;
    }
}

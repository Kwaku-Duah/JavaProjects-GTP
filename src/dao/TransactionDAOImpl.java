package dao;

import model.Transaction;
import database.DatabaseManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class TransactionDAOImpl implements TransactionDAO {

    private Deque<Transaction> transactionStack; // Stack for LIFO operations
    private Deque<Transaction> transactionQueue; // Queue for FIFO operations

    public TransactionDAOImpl() {
        this.transactionStack = new ArrayDeque<>();
        this.transactionQueue = new ArrayDeque<>();
    }

    @Override
    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        try (Connection conn = DatabaseManager.getConnection()) {
            String query = "SELECT * FROM Transaction";
            try (PreparedStatement stmt = conn.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Transaction transaction = new Transaction(
                            rs.getInt("id"),
                            rs.getInt("book_id"),
                            rs.getInt("patron_id"),
                            rs.getDate("issue_date").toLocalDate(),
                            rs.getDate("return_date").toLocalDate()
                    );
                    transactions.add(transaction);
                    transactionQueue.offer(transaction); 
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    @Override
    public Transaction getTransactionById(int id) {
        Transaction transaction = null;
        try (Connection conn = DatabaseManager.getConnection()) {
            String query = "SELECT * FROM Transaction WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        transaction = new Transaction(
                                rs.getInt("id"),
                                rs.getInt("book_id"),
                                rs.getInt("patron_id"),
                                rs.getDate("issue_date").toLocalDate(),
                                rs.getDate("return_date").toLocalDate()
                        );
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transaction;
    }

    @Override
    public void addTransaction(Transaction transaction) {
        try (Connection conn = DatabaseManager.getConnection()) {
            String query = "INSERT INTO Transaction (book_id, patron_id, issue_date, return_date) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, transaction.getBookId());
                stmt.setInt(2, transaction.getPatronId());
                stmt.setDate(3, java.sql.Date.valueOf(transaction.getIssueDate()));
                stmt.setDate(4, java.sql.Date.valueOf(transaction.getReturnDate()));
                stmt.executeUpdate();

 
                transactionQueue.offer(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateTransaction(Transaction transaction) {
        try (Connection conn = DatabaseManager.getConnection()) {
            String query = "UPDATE Transaction SET book_id = ?, patron_id = ?, issue_date = ?, return_date = ? WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, transaction.getBookId());
                stmt.setInt(2, transaction.getPatronId());
                stmt.setDate(3, java.sql.Date.valueOf(transaction.getIssueDate()));
                stmt.setDate(4, java.sql.Date.valueOf(transaction.getReturnDate()));
                stmt.setInt(5, transaction.getId());
                stmt.executeUpdate();

        
                transactionQueue.offer(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteTransaction(int id) {
        try (Connection conn = DatabaseManager.getConnection()) {
            String query = "DELETE FROM Transaction WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

  
    public Transaction getLastTransaction() {
        return transactionStack.peek();
    }


    public Transaction getNextTransaction() {
        return transactionQueue.poll(); 
    }
}

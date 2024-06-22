package controller;

import model.Transaction;
import dao.TransactionDAO;
import java.util.List;

/**
 * Controller class for managing operations related to transactions.
 * Encapsulates TransactionDAO interactions through public methods.
 * Supports CRUD operations on transactions.
 */
public class TransactionController {
    private TransactionDAO transactionDAO;

    /**
     * Constructor to initialize TransactionController with a specific TransactionDAO implementation.
     * @param transactionDAO The data access object responsible for transaction operations.
     */
    public TransactionController(TransactionDAO transactionDAO) {
        this.transactionDAO = transactionDAO;
    }

    /**
     * Retrieves all transactions stored in the data source.
     * @return List of all transactions.
     */
    public List<Transaction> getAllTransactions() {
        return transactionDAO.getAllTransactions();
    }

    /**
     * Retrieves a transaction by its unique identifier.
     * @param id The identifier of the transaction to retrieve.
     * @return The transaction object corresponding to the given ID.
     */
    public Transaction getTransactionById(int id) {
        return transactionDAO.getTransactionById(id);
    }

    /**
     * Adds a new transaction to the data source.
     * @param transaction The transaction object to be added.
     */
    public void addTransaction(Transaction transaction) {
        transactionDAO.addTransaction(transaction);
    }

    /**
     * Updates an existing transaction in the data source.
     * @param transaction The updated transaction object.
     */
    public void updateTransaction(Transaction transaction) {
        transactionDAO.updateTransaction(transaction);
    }

    /**
     * Deletes a transaction from the data source by its ID.
     * @param id The ID of the transaction to be deleted.
     */
    public void deleteTransaction(int id) {
        transactionDAO.deleteTransaction(id);
    }
}

package dao;

import model.Transaction;
import java.util.List;

public interface TransactionDAO {
    List<Transaction> getAllTransactions();
    Transaction getTransactionById(int id);
    void addTransaction(Transaction transaction);
    void updateTransaction(Transaction transaction);
    void deleteTransaction(int id);
}

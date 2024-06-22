package application;

import dao.TransactionDAO;
import dao.TransactionDAOImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Transaction;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class TransactionController {

    @FXML
    private TableView<Transaction> transactionsTableView;
    @FXML
    
    private TableColumn<Transaction, Integer> transactionIdColumn;
    @FXML
    private TableColumn<Transaction, Integer> bookIdColumn;
    @FXML
    private TableColumn<Transaction, Integer> patronIdColumn;
    @FXML
    private TableColumn<Transaction, LocalDate> issueDateColumn;
    @FXML
    private TableColumn<Transaction, LocalDate> returnDateColumn;
    @FXML
    private TextField bookIdField;
    @FXML
    private TextField patronIdField;
    @FXML
    private TextField issueDateField;
    @FXML
    private TextField returnDateField;

    private TransactionDAO transactionDAO;
    private ObservableList<Transaction> transactionsList;



    /**
 * Controller class for managing transactions in a library application.
 * Utilizes encapsulation, abstraction
 */

    public TransactionController() {
        this.transactionDAO = new TransactionDAOImpl();
    }

    @FXML
    private void initialize() {
        transactionIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        bookIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        patronIdColumn.setCellValueFactory(new PropertyValueFactory<>("patronId"));
        issueDateColumn.setCellValueFactory(new PropertyValueFactory<>("issueDate"));
        returnDateColumn.setCellValueFactory(new PropertyValueFactory<>("returnDate"));

        transactionsList = FXCollections.observableArrayList();
        loadTransactionData();
    }

        /**
     * Loads transaction data from the database and sets it to the TableView.
     * Utilizes abstraction to hide the details of data retrieval from the UI.
     */

    private void loadTransactionData() {
        List<Transaction> transactionData = transactionDAO.getAllTransactions();
        transactionsList.setAll(transactionData);
        transactionsTableView.setItems(transactionsList);
    }

    @FXML
    private void handleAddTransaction() {
        try {
            int bookId = Integer.parseInt(bookIdField.getText().trim());
            int patronId = Integer.parseInt(patronIdField.getText().trim());
            LocalDate issueDate = LocalDate.parse(issueDateField.getText().trim());
            LocalDate returnDate = LocalDate.parse(returnDateField.getText().trim());

            Transaction newTransaction = new Transaction(0, bookId, patronId, issueDate, returnDate);
            transactionDAO.addTransaction(newTransaction);
            loadTransactionData();

            showAlert(Alert.AlertType.INFORMATION, "Transaction Added", "New transaction has been added successfully.");
            clearFields();
        } catch (Exception e) {
            showAlert(Alert.AlertType.WARNING, "Invalid Input", "Please provide valid input for all fields.");
        }
    }

    @FXML
    private void handleDeleteTransaction() {
        Transaction selectedTransaction = transactionsTableView.getSelectionModel().getSelectedItem();
        if (selectedTransaction != null) {
            transactionDAO.deleteTransaction(selectedTransaction.getId());
            loadTransactionData();

            showAlert(Alert.AlertType.INFORMATION, "Transaction Deleted", "The transaction has been deleted successfully.");
        } else {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a transaction to delete.");
        }
    }

    
    /** 
     * @param type
     * @param title
     * @param content
     */
    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void clearFields() {
        bookIdField.clear();
        patronIdField.clear();
        issueDateField.clear();
        returnDateField.clear();
    }

    @FXML
    private void switchToTransaction() {
        switchView("hellofx.fxml");
    }

    private void switchView(String fxml) {
        try {
            Parent view = FXMLLoader.load(getClass().getResource(fxml));
            Scene scene = new Scene(view);
            Stage stage = (Stage) transactionsTableView.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

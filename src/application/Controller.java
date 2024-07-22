package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Book;
import dao.BookDAO;
import dao.BookDAOImpl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class Controller {

    @FXML
    private TableView<Book> bookTableView;
    @FXML
    private TableColumn<Book, Integer> idColumn;
    @FXML
    private TableColumn<Book, String> titleColumn;
    @FXML
    private TableColumn<Book, String> authorColumn;
    @FXML
    private TableColumn<Book, String> isbnColumn;
    @FXML
    private TableColumn<Book, Boolean> availableColumn;

    @FXML TextField titleField;
    @FXML TextField authorField;
    @FXML TextField isbnField;
    @FXML TextField searchField;
    @FXML
    private Button addButton;
    @FXML
    private Button searchButton;
    @FXML
    private Button borrowButton;
    @FXML
    private Button deleteButton;

    BookDAO bookDAO;

    public Controller() {
        this.bookDAO = new BookDAOImpl();
    }

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        availableColumn.setCellValueFactory(new PropertyValueFactory<>("available"));

        loadBookData();
    }

    private void loadBookData() {
        List<Book> books = bookDAO.getAllBooks();
        bookTableView.getItems().setAll(books);
    }

    @FXML void handleAddBook() {
        String title = titleField.getText();
        String author = authorField.getText();
        String isbn = isbnField.getText();
        Book newBook = new Book(0, title, author, isbn, true);
        bookDAO.addBook(newBook);
        loadBookData();
 
        titleField.clear();
        authorField.clear();
        isbnField.clear();
    }

    @FXML void handleSearch() {
        String searchText = searchField.getText().trim();
        if (searchText.isEmpty()) {
            loadBookData(); 
        } else {
            List<Book> foundBooks = bookDAO.searchBooks(searchText);
            bookTableView.getItems().setAll(foundBooks);
        }
    }

    @FXML
    private void handleBorrowBook() {
        Book selectedBook = bookTableView.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            if (!selectedBook.isAvailable()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Book Not Available");
                alert.setHeaderText(null);
                alert.setContentText("This book is already borrowed.");
                alert.showAndWait();
            } else {
                bookDAO.borrowBook(selectedBook.getId());
                selectedBook.setAvailable(false);
                bookTableView.refresh(); 
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Book Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a book to borrow.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleDeleteBook() {
        Book selectedBook = bookTableView.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Delete");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete the selected book?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                bookDAO.deleteBook(selectedBook.getId());
                bookTableView.getItems().remove(selectedBook);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Book Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a book to delete.");
            alert.showAndWait();
        }
    }

    @FXML
    private void switchToPatrons() {
        switchView("patronfx.fxml");
    }

    @FXML
    private void switchToTransaction() {
        switchView("transactfx.fxml");
    }

    /**
     * @param fxml
     */
    private void switchView(String fxml) {
        try {
            Parent view = FXMLLoader.load(getClass().getResource(fxml));
            Scene scene = new Scene(view);
            Stage stage = (Stage) bookTableView.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

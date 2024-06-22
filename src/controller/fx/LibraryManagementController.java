package controller.fx;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import model.Book;
import dao.BookDAO;
import dao.BookDAOImpl;
import java.util.List;

public class LibraryManagementController {
    
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

    private BookDAO bookDAO;

    public LibraryManagementController() {
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

    @FXML
    private void handleAddBook() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddBookDialog.fxml"));
            Parent parent = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Add New Book");
            stage.setScene(new Scene(parent));

            AddBookDialogController controller = loader.getController();
            controller.setDialogStage(stage);

            stage.showAndWait();
            loadBookData(); // Refresh the table after adding a book
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleUpdateBook() {
        // Implement the logic to update a book
        // For example, show a dialog to input new details for the selected book and then call bookDAO.updateBook()
    }

    @FXML
    private void handleDeleteBook() {
        Book selectedBook = bookTableView.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            bookDAO.deleteBook(selectedBook.getId());
            loadBookData(); // Refresh the table
        }
    }
}

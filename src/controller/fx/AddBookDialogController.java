package controller.fx;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Book;
import dao.BookDAO;
import dao.BookDAOImpl;

public class AddBookDialogController {

    @FXML
    private TextField titleField;
    @FXML
    private TextField authorField;
    @FXML
    private TextField isbnField;

    private Stage dialogStage;
    private BookDAO bookDAO;

    public AddBookDialogController() {
        this.bookDAO = new BookDAOImpl();
    }

    
    /** 
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    private void handleSave() {
        String title = titleField.getText();
        String author = authorField.getText();
        String isbn = isbnField.getText();

        Book newBook = new Book(0, title, author, isbn, true);
        bookDAO.addBook(newBook);

        dialogStage.close();
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
}

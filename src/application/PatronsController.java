package application;

import dao.PatronDAO;
import dao.PatronDAOImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import model.Patron;

import java.io.IOException;
import java.util.List;

public class PatronsController {

    @FXML
    private TableView<Patron> patronsTableView;
    @FXML
    private TableColumn<Patron, Integer> idColumn;
    @FXML
    private TableColumn<Patron, String> nameColumn;
    @FXML
    private TableColumn<Patron, String> emailColumn;
    @FXML
    private TableColumn<Patron, String> phoneColumn;
    @FXML
    private TextField patronNameField;
    @FXML
    private TextField patronEmailField;
    @FXML
    private TextField patronPhoneField;

    @FXML
    private ListView<Patron> patronsListView;
    @FXML
    private BorderPane mainPane;

    private PatronDAO patronDAO;
    private ObservableList<Patron> patronsList;

    public PatronsController() {
        this.patronDAO = new PatronDAOImpl();
    }

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));

        patronsList = FXCollections.observableArrayList();

        loadPatronData();
    }

    private void loadPatronData() {
        List<Patron> patronData = patronDAO.getAllPatrons();
        patronsList.setAll(patronData);
        patronsTableView.setItems(patronsList);
    }

    @FXML
    private void handleAddPatron() {
        String name = patronNameField.getText().trim();
        String email = patronEmailField.getText().trim();
        String phone = patronPhoneField.getText().trim();

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Missing Information", "Please fill in all fields.");
            return;
        }

        Patron newPatron = new Patron(0, name, email, phone);
        patronDAO.addPatron(newPatron);
        patronsList.add(newPatron);
        
        loadPatronData();

        showAlert(Alert.AlertType.INFORMATION, "Patron Added", "New patron has been added successfully.");

        clearFields();
    }

    @FXML
    private void handleDeletePatron() {
        Patron selectedPatron = patronsTableView.getSelectionModel().getSelectedItem();
        if (selectedPatron != null) {
            patronDAO.deletePatron(selectedPatron.getId());
            patronsList.remove(selectedPatron);

            showAlert(Alert.AlertType.INFORMATION, "Patron Deleted", "The patron has been deleted successfully.");
        } else {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a patron to delete.");
        }
    }

    @FXML
    private void switchToBooks() {
        switchView("hellofx.fxml");
    }

    
    /** 
     * @param fxml
     */
    private void switchView(String fxml) {
        try {
            Parent view = FXMLLoader.load(getClass().getResource(fxml));
            Scene scene = new Scene(view);
            Stage stage = (Stage) patronsTableView.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void clearFields() {
        patronNameField.clear();
        patronEmailField.clear();
        patronPhoneField.clear();
    }
}

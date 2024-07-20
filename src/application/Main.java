package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Main class that extends the Application class and serves as the entry point for the JavaFX application.
 */
public class Main extends Application {

    /**
     * The main entry point for all JavaFX applications.
     *
     * @param stage the primary stage for this application, onto which
     * the application scene can be set.
     * @throws Exception if an error occurs during loading the FXML resource.
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("hellofx.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initStyle(StageStyle.DECORATED);
        stage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves as fallback in case the application can not be launched through deployment artifacts, 
     *
     * @param args the command line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
}

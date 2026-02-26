package nock;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Nock using FXML.
 */
public class Main extends Application {

    private final Nock nock = new Nock();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);

            // Inject Nock instance into controller
            MainWindow controller = fxmlLoader.getController();
            controller.setNock(nock);

            stage.setTitle("Nock");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
package nock;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.application.Platform;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Nock nock;

    private final Image userImage = new Image(getClass().getResourceAsStream("/images/NewDaUser.png"));
    private final Image nockImage = new Image(getClass().getResourceAsStream("/images/NewDaNock.png")); // you can rename file later

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Nock instance */
    public void setNock(Nock n) {
        nock = n;
        formatWelcomeMessage();
    }

    private void formatWelcomeMessage() {
        String welcomeMessage = "Hello! I'm Nock\nWhat can I do for you?";
        dialogContainer.getChildren().add(
                DialogBox.getNockDialog(welcomeMessage, nockImage)
        );
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Nock's reply,
     * and then appends them to the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = nock.getResponse(input);
        if (input.equals("bye")) {
            Platform.exit();
            return;
        }
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getNockDialog(response, nockImage)
        );
        userInput.clear();
    }
}

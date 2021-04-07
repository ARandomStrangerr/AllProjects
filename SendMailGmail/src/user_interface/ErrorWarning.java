package user_interface;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ErrorWarning extends SuperWindow {
    private final Label messageLabel;
    private final Button closeButton = new Button("Đóng");
    private final VBox primePane = new VBox();

    public ErrorWarning(Stage stage, String message) {
        super(stage, "");
        messageLabel = new Label(message);
    }

    @Override
    protected Pane setupPane() {
        closeButton.setOnAction(this::handle);
        primePane.getChildren().addAll(messageLabel, closeButton);
        primePane.setStyle("-fx-padding: 25; -fx-alignment: CENTER");
        return primePane;
    }

    private void handle(ActionEvent event) {
        stage.close();
    }
}

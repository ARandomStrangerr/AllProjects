package user_interface;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public final class ErrorPane extends PrimePane {
    private final Label messageLabel;
    private final Button closeButton = new Button("Đóng");
    private final Region region = new Region();

    private final HBox upperBox = new HBox(),
            lowerBox = new HBox(region, closeButton);
    private final VBox primePane = (VBox) super.getPrimePane();

    private final Scene scene = new Scene(primePane);
    private final Stage stage = new Stage();

    public ErrorPane(String message, Stage ownerStage) {
        super(new VBox(), ownerStage);
        messageLabel = new Label(message);
        construct();
    }

    private void construct() {
        HBox.setHgrow(region, Priority.ALWAYS);

        primePane.getStylesheets().addAll("user_interface/stylesheet/utilities.css",
                "user_interface/stylesheet/error.css");
        upperBox.getStyleClass().addAll("upper-pane", "padding");
        lowerBox.getStyleClass().addAll("lower-pane", "padding");
        closeButton.getStyleClass().add("disagree-button");

        closeButton.setOnAction(event -> stage.hide());


        upperBox.getChildren().add(messageLabel);

        primePane.getChildren().addAll(upperBox, lowerBox);

        stage.initOwner(ownerStage);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}

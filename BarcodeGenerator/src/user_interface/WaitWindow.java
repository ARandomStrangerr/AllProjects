package user_interface;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class WaitWindow extends PrimePane {
    private final Label nameWindowLabel = new Label(),
            displayMessageLabel = new Label();

    private final VBox primePane = (VBox) super.getPrimePane();

    private final Scene scene = new Scene(primePane);
    private final Stage stage = new Stage();

    public WaitWindow(Stage ownerStage, String nameLabel) {
        super(new VBox(), ownerStage);
        nameWindowLabel.setText(nameLabel);
        construct();
    }

    private void construct() {
        primePane.getChildren().addAll(nameWindowLabel, displayMessageLabel);
        primePane.getStylesheets().add("user_interface/stylesheet/utilities.css");
        primePane.getStyleClass().add("pane");

        stage.setScene(scene);
        stage.setResizable(false);
        stage.initOwner(super.ownerStage);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
    }

    public void setDisplayMessageLabel(String msg) {
        this.displayMessageLabel.setText(msg);
    }

    public void showWindow() {
        this.stage.show();
    }

    public void closeWindow() {
        this.stage.hide();
    }
}
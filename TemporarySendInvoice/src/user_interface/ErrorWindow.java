package user_interface;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class ErrorWindow extends SuperWindow{
    private final String message;
    public ErrorWindow (Stage ownerStage, String message){
        super(ownerStage, "Thiếu thông tin");
        this.message = message;
    }

    @Override
    protected Pane pane() {
        Button closeButton = new Button("Đóng");
        Label msg = new Label(message);
        Region region = new Region();
        HBox buttonBox = new HBox(region, closeButton);
        VBox primePane = new VBox(msg, buttonBox);

        HBox.setHgrow(region, Priority.ALWAYS);

        primePane.getStylesheets().add("stylesheet/unify.css");
        primePane.getStyleClass().add("pane");

        return primePane;
    }
}

package user_interface;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public final class WaitWindow extends SuperWindow {

    public WaitWindow(Stage ownerStage) {
        super(ownerStage, "Đợi");
    }

    @Override
    protected Pane pane() {
        Label label = new Label("Vui lòng đợi");
        HBox primePane = new HBox(label);
        primePane.getStylesheets().add("stylesheet/unify.css");
        primePane.getStyleClass().addAll("pane-padding", "pane");
        return primePane;
    }
}

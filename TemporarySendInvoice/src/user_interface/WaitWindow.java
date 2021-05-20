package user_interface;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public final class WaitWindow extends SuperWindow {

    private final Label label = new Label("Vui lòng đợi");
    public WaitWindow(Stage ownerStage) {
        super(ownerStage, "Đợi");
    }

    @Override
    protected Pane pane() {
        HBox primePane = new HBox(label);
        primePane.getStylesheets().add("stylesheet/unify.css");
        primePane.getStyleClass().addAll("pane-padding", "pane");
        return primePane;
    }

    public void setDisplayMessage(String msg) {
        label.setText(msg);
    }
}

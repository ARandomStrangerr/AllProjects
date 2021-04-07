package user_interface;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class HoldStage extends SuperWindow {
    private final Label label = new Label("Vui lòng đợi");
    private final HBox primePane = new HBox(label);
    private final Stage ownerStage;

    public HoldStage(Stage stage, Stage ownerStage, String windowName) {
        super(stage, windowName);
        this.ownerStage = ownerStage;
    }

    @Override
    protected Pane setupPane() {
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(ownerStage);
        stage.initStyle(StageStyle.UNDECORATED);
        primePane.setStyle("-fx-padding: 25");
        return primePane;
    }

    public void close(){
        stage.close();
    }
}

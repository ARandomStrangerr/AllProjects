package user_interface;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public abstract class SuperPane {
    private final Pane pane;
    private final Stage ownerStage;

    public SuperPane(Stage ownerStage, Pane pane) {
        this.pane = pane;
        this.ownerStage = ownerStage;
    }

    public Pane getPane() {
        return pane;
    }

    public Stage getOwnerStage() {
        return ownerStage;
    }
}
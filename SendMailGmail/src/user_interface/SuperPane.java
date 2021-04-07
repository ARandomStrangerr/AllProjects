package user_interface;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public abstract class SuperPane {
    private final Stage ownerStage;
    private final Pane primePane;

    public SuperPane(Stage ownerStage, Pane primePane) {
        this.ownerStage = ownerStage;
        this.primePane = primePane;
    }

    public abstract Pane getPrimePane();
}

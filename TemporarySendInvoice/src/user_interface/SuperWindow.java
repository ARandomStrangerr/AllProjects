package user_interface;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public abstract class SuperWindow {
    private final Stage stage = new Stage(),
            ownerStage;
    private final String title;

    public SuperWindow(Stage ownerStage, String title) {
        this.ownerStage = ownerStage;
        this.title = title;
    }

    public void show() {
        stage.setScene(new Scene(pane()));
        stage.setTitle(title);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(ownerStage);
        stage.show();
    }

    public void close() {
        stage.close();
    }

    abstract protected Pane pane();
}

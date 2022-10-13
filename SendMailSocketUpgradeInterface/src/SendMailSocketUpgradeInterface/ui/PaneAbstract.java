package SendMailSocketUpgradeInterface.ui;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public abstract class PaneAbstract {
    private final Pane pane;
    private final Stage stage;

    protected PaneAbstract(Stage stage, Pane pane) {
        this.stage = stage;
        this.pane = pane;
        create();
        stage.setScene(new Scene(pane));
    }

    protected PaneAbstract(Pane pane){
        this(new Stage(), pane);
    }

    public Pane getPane() {
        return pane;
    }

    public Stage getStage(){
        return stage;
    }

    protected abstract void create();
}

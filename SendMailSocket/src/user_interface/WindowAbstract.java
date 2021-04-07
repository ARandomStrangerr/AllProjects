package user_interface;

import javafx.stage.Stage;

public abstract class WindowAbstract {
    private final Stage thisStage, ownerStage;
    private PaneAbstract pane;

    protected WindowAbstract(Stage ownerStage) {
        this.ownerStage = ownerStage;
        thisStage = new Stage();
    }

    protected WindowAbstract(Stage ownerStage, PaneAbstract pane) {
        this(ownerStage);
        this.pane = pane;
    }

    public abstract void setup();

    public PaneAbstract getPane() {
        return pane;
    }

    public Stage getOwnerStage() {
        return ownerStage;
    }

    public Stage getThisStage() {
        return thisStage;
    }

    public void setThisStage(PaneAbstract pane){
        this.pane = pane;
    }

    public void show() {
        thisStage.show();
    }

    public void close() {
        thisStage.close();
    }
}

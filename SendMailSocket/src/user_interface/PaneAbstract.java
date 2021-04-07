package user_interface;

import javafx.scene.layout.Pane;
import user_interface.command.CommandInterface;

public abstract class PaneAbstract {
    private final Pane pane;
    private WindowAbstract thisWindow;

    protected PaneAbstract(Pane pane){
        this.pane = pane;
    }

    protected PaneAbstract(WindowAbstract thisWindow, Pane pane) {
        this.pane = pane;
        this.thisWindow = thisWindow;
    }

    protected abstract void setting();

    public Pane getPane() {
        return pane;
    }

    protected WindowAbstract getOwnerWindow() {
        return thisWindow;
    }

    public void setOwnerWindow(WindowAbstract windowAbstract){
        thisWindow = windowAbstract;
    }

    protected void execute(CommandInterface command) {
        command.execute();
    }
}

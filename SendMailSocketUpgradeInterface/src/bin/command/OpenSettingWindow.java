package bin.command;

import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ui.PaneSetting;

public class OpenSettingWindow implements Command{
    private final Stage ownerWindow;

    public OpenSettingWindow(Stage ownerWindow) {
        this.ownerWindow = ownerWindow;
    }

    @Override
    public boolean execute() {
        PaneSetting pane = new PaneSetting();
        pane.getStage().initStyle(StageStyle.UNDECORATED);
        pane.getStage().initModality(Modality.APPLICATION_MODAL);
        pane.getStage().initOwner(ownerWindow);
        pane.getStage().show();
        return true;
    }
}

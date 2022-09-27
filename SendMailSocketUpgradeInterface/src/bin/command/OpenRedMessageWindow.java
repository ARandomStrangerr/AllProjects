package bin.command;

import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ui.PaneMessage;

public class OpenRedMessageWindow implements Command{
    private final PaneMessage pane;
    public OpenRedMessageWindow(Stage stage, String message){
        pane = new PaneMessage("red-message-pane", message);
        pane.getStage().initOwner(stage);
    }
    @Override
    public boolean execute() {
        pane.getStage().initStyle(StageStyle.UNDECORATED);
        pane.getStage().initModality(Modality.APPLICATION_MODAL);
        pane.getStage().show();
        return false;
    }
}

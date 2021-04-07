package user_interface.command;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import user_interface.Help;

public class OpenHelpWindow implements Command {
    private Stage beneathStage;
    private Pane beneathPane;

    public OpenHelpWindow(Stage beneathStage, Pane pane) {
        this.beneathStage = beneathStage;
        this.beneathPane = pane;
    }

    @Override
    public void execute() {
        new Help(new Stage(), beneathStage, beneathPane).openWindow();
    }
}

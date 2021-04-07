package user_interface.command;

import javafx.stage.Stage;
import user_interface.PaneMain;
import user_interface.WindowRegular;

public class StartingSequence implements CommandInterface{
    private final Stage primeStage;

    public StartingSequence(Stage primeStage) {
        this.primeStage = primeStage;
    }

    @Override
    public void execute() {
        PaneMain paneMain = new PaneMain();

        WindowRegular windowRegular = new WindowRegular(primeStage);
        paneMain.setOwnerWindow(windowRegular);
        windowRegular.setThisStage(paneMain);
        windowRegular.setup();
        windowRegular.show();
    }
}

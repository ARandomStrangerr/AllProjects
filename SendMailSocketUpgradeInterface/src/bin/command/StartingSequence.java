package bin.command;

import command.CommandInterface;
import javafx.stage.Stage;
import user_interface.PaneAbstract;
import user_interface.PaneMain;
import user_interface.WindowAbstract;
import user_interface.WindowRegular;

public final class StartingSequence implements CommandInterface {
    private final Stage primeStage;

    public StartingSequence(Stage primeStage) {
        this.primeStage = primeStage;
    }

    @Override
    public void execute() {
        WindowAbstract mainWindow = new WindowRegular(primeStage);
        PaneAbstract paneMain = PaneMain.getInstance(mainWindow);
        mainWindow.setPane(paneMain);
        mainWindow.openCurrentStage();
    }
}

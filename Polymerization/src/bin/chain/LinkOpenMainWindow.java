package bin.chain;

import chain_of_responsibility.Link;
import javafx.stage.Stage;
import user_interface.PaneLeft;
import user_interface.WindowRegular;
import user_interface.concrete_pane.PaneLeftRight;

class LinkOpenMainWindow extends Link {
    private final PaneLeftRight mainPane;
    private final WindowRegular mainWindow;
    private final PaneLeft paneLeft;

    LinkOpenMainWindow(Stage primeStage) {
        mainPane = PaneLeftRight.getInstance();
        paneLeft = PaneLeft.getInstance();
        mainWindow = new WindowRegular(primeStage);
    }

    @Override
    protected boolean handler(Object o) {
        mainWindow.getCurrentStage().setHeight(500);
        mainWindow.getCurrentStage().setWidth(1000);
        mainPane.setWindow(mainWindow);
        mainPane.setLeftPane(paneLeft.getMainPane());
        mainWindow.setPane(mainPane);
        mainWindow.openCurrentStage();
        return true;
    }
}
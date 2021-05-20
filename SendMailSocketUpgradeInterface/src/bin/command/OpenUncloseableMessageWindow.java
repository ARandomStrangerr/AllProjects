package bin.command;

import command.CommandInterface;
import user_interface.PaneUpperMessage;
import user_interface.WindowAbstract;
import user_interface.WindowPopup;

public final class OpenUncloseableMessageWindow implements CommandInterface {
    private final WindowAbstract thisWindow;
    private final PaneUpperMessage paneUpperMessage;
    public OpenUncloseableMessageWindow(String displayMsg, WindowAbstract ownerWindow){
        paneUpperMessage = PaneUpperMessage.getInstance();
        paneUpperMessage.setDisplayMessageLabel(displayMsg);
        if (paneUpperMessage.getWindow() == null){
            WindowAbstract temp = new WindowPopup();
            temp.setup();
            temp.setPane(paneUpperMessage);
            temp.setOwnerStage(ownerWindow.getCurrentStage());
            paneUpperMessage.setWindow(temp);
        }
        thisWindow = paneUpperMessage.getWindow();
    }
    @Override
    public void execute() {
        thisWindow.openCurrentStage();
    }
}
package bin.command;

import command.CommandInterface;
import user_interface.PaneUpperMessage;
import user_interface.PaneMessageConcrete;
import user_interface.WindowAbstract;
import user_interface.WindowPopup;

public final class OpenMessageWindow implements CommandInterface {
    private final String displayMsg;
    private final WindowAbstract ownerWindow;

    public OpenMessageWindow(String displayMsg, WindowAbstract ownerWindow) {
        this.displayMsg = displayMsg;
        this.ownerWindow = ownerWindow;
    }

    @Override
    public void execute() {
        PaneUpperMessage upperMessage = PaneUpperMessage.getInstance();
        upperMessage.setDisplayMessageLabel(displayMsg);
        PaneMessageConcrete paneMessageConcrete = PaneMessageConcrete.getInstance();
        paneMessageConcrete.setUpperPane(upperMessage);
        if (paneMessageConcrete.getWindow() == null){
            WindowPopup popupWindow = new WindowPopup();
            popupWindow.setPane(paneMessageConcrete);
            popupWindow.setOwnerStage(ownerWindow.getCurrentStage());
            popupWindow.setup();
            paneMessageConcrete.setWindow(popupWindow);
        }
        paneMessageConcrete.getWindow().openCurrentStage();
    }
}

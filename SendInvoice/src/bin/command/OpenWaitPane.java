package bin.command;

import command.CommandInterface;
import user_interface.PaneWait;
import user_interface.WindowPopup;

public final class OpenWaitPane implements CommandInterface {
    @Override
    public void execute() {
        PaneWait paneWait = PaneWait.getInstance();
        if (paneWait.getWindow() == null){
            WindowPopup windowPopup = new WindowPopup();
            windowPopup.setPane(paneWait);
            windowPopup.setup();
            paneWait.setWindow(windowPopup);
        }
        paneWait.getWindow().openCurrentStage();
    }
}

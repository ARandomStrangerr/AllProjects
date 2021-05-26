package bin.command;

import command.CommandInterface;
import user_interface.PaneMessageConcrete;
import user_interface.PaneTableViewErrorMessage;
import user_interface.WindowAbstract;
import user_interface.WindowPopup;
import user_interface.table_item.ReceiverInfo;

import java.util.List;

public class OpenLeftOverMailWindow implements CommandInterface {
    private final List<ReceiverInfo> items;
    private final WindowAbstract ownerWindow;

    public OpenLeftOverMailWindow(List<ReceiverInfo> items, WindowAbstract ownerWindow) {
        this.items = items;
        this.ownerWindow= ownerWindow;
    }

    @Override
    public void execute() {
        PaneTableViewErrorMessage tableViewErrorMessage = PaneTableViewErrorMessage.getInstance();
        PaneMessageConcrete paneMessageConcrete = PaneMessageConcrete.getInstance();
        tableViewErrorMessage.setItems(items);
        paneMessageConcrete.setUpperPane(tableViewErrorMessage);
        if (paneMessageConcrete.getWindow() == null){
            WindowPopup window = new WindowPopup();
            window.setup();
            window.setOwnerStage(ownerWindow.getCurrentStage());
            window.setPane(paneMessageConcrete);
            paneMessageConcrete.setWindow(window);
        }
        paneMessageConcrete.getWindow().openCurrentStage();
    }
}

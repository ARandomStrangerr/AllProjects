package bin.command;

import bin.thread.ReadExcelThread;
import command.CommandInterface;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import user_interface.PaneMain;
import user_interface.table_item.ReceiverInfo;

public final class AddFromExcelButton implements CommandInterface {
    private final ObservableList<ReceiverInfo> items;

    public AddFromExcelButton(ObservableList<ReceiverInfo> items) {
        this.items = items;
    }

    @Override
    public void execute() {
        FileChooser fileChooser = new FileChooser();
        String path;
        try {
            path = fileChooser.showOpenDialog(PaneMain.getInstance().getWindow().getCurrentStage()).getAbsolutePath();
        } catch (NullPointerException e) {
            System.err.println("FILE HAS NOT BEEN PICKED");
            return;
        }
        ReadExcelThread read = new ReadExcelThread(items, path);
        Thread readThread = new Thread(read);
        readThread.start();
    }
}

package user_interface.command;

import javafx.collections.ObservableList;
import user_interface.table_item.ReceiverInfo;

public class AddSingleEntryToTable implements CommandInterface {
    private final ObservableList<ReceiverInfo> items;

    public AddSingleEntryToTable(ObservableList<ReceiverInfo> items) {
        this.items = items;
    }

    @Override
    public void execute() {
        items.add(new ReceiverInfo("Trống", "Trống"));
    }
}

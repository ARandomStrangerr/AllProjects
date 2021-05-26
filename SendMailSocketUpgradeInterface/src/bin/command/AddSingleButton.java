package bin.command;

import command.CommandInterface;
import javafx.collections.ObservableList;
import user_interface.table_item.ReceiverInfo;

public final class AddSingleButton implements CommandInterface {
    private final ObservableList<ReceiverInfo> items;

    public AddSingleButton(ObservableList<ReceiverInfo> items) {
        this.items = items;
    }

    @Override
    public void execute() {
        items.add(new ReceiverInfo("Trống", "Trống"));
    }
}

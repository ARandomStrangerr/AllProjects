package bin.command;

import command.CommandInterface;
import javafx.scene.control.TableView;
import user_interface.table_item.ReceiverInfo;

public final class RemoveButton implements CommandInterface {
    private final TableView<ReceiverInfo> tableView;

    public RemoveButton(TableView<ReceiverInfo> tableView) {
        this.tableView = tableView;
    }

    @Override
    public void execute() {
        tableView.getItems().removeAll(tableView.getSelectionModel().getSelectedItems());
    }
}

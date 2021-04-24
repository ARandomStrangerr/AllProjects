package user_interface.command;

import javafx.scene.control.TableView;
import user_interface.table_item.ReceiverInfo;


public class DeleteSelectedItem implements CommandInterface{
    private final TableView<ReceiverInfo> table;

    public DeleteSelectedItem(TableView<ReceiverInfo> table) {
        this.table = table;
    }

    @Override
    public void execute() {
        table.getItems().removeAll(table.getSelectionModel().getSelectedItems());
    }
}

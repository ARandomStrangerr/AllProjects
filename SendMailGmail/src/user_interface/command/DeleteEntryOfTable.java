package user_interface.command;

import javafx.scene.control.TableView;
import user_interface.table_datatype.ReceiverList;

public class DeleteEntryOfTable implements Command{
    private TableView<ReceiverList> table;

    public DeleteEntryOfTable(TableView<ReceiverList> table) {
        this.table = table;
    }

    @Override
    public void execute() {
        table.getItems().remove(table.getSelectionModel().getSelectedItem());
    }
}

package bin.command;

import command.CommandInterface;
import javafx.scene.control.TableView;
import ui.table_structure.BarcodeInstance;

public class AddSingleInstanceToTable implements CommandInterface {
    private final TableView<BarcodeInstance> table;

    public AddSingleInstanceToTable(TableView<BarcodeInstance> table) {
        this.table = table;
    }

    @Override
    public void execute() {
        table.getItems().add(new BarcodeInstance("Trống", "Trống"));
    }
}

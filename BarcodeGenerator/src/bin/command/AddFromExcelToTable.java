package bin.command;

import command.CommandInterface;
import file_operation.ExcelFile;
import javafx.scene.control.TableView;
import ui.table_structure.BarcodeInstance;

import java.io.IOException;
import java.util.List;

public class AddFromExcelToTable implements CommandInterface {
    private final TableView<BarcodeInstance> table;
    private final String path;

    public AddFromExcelToTable(TableView<BarcodeInstance> table,
                               String path) {
        this.table = table;
        this.path = path;
    }

    @Override
    public void execute() {
        try {
            for (List<String> row : ExcelFile.getInstance().read(path))
                table.getItems().add(new BarcodeInstance(row.get(0), row.get(1)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

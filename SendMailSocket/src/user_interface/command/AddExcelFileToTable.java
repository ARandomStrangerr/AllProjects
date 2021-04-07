package user_interface.command;

import bin.file.ExcelFile;
import bin.file.FileInterface;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import user_interface.table_item.ReceiverInfo;

import java.io.IOException;
import java.util.List;

public class AddExcelFileToTable implements CommandInterface {
    private final ObservableList<ReceiverInfo> items;
    private final Stage stage;

    public AddExcelFileToTable(ObservableList<ReceiverInfo> items, Stage stage) {
        this.items = items;
        this.stage = stage;
    }

    @Override
    public void execute() {
        FileChooser fileChooser = new FileChooser();
        FileInterface excelFile = ExcelFile.getInstance();
        List<String> rows;
        try {   //try to read data
            rows = excelFile.read(fileChooser.showOpenDialog(stage).getPath());
        } catch (IOException e) {
            return;
        }
        //insert data into table
        for (String row : rows) {
            String[] cell = row.split(":");
            try {
                items.add(new ReceiverInfo(cell[0], cell[1]));
            } catch (IndexOutOfBoundsException ignore) {
            }
        }
    }
}

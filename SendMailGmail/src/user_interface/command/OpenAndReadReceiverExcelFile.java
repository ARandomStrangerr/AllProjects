package user_interface.command;

import file_operation.ExcelFile;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import user_interface.table_datatype.ReceiverList;

import java.io.IOException;
import java.util.List;

public class OpenAndReadReceiverExcelFile implements Command {
    private ObservableList<ReceiverList> list;
    private final Stage ownerStage;

    public OpenAndReadReceiverExcelFile(Stage ownerStage,
                                        ObservableList<ReceiverList> list) {
        this.ownerStage = ownerStage;
        this.list = list;
    }

    @Override
    public void execute() {
        FileChooser fileChooser = new FileChooser();
        try {
            for (List<String> line : ExcelFile.getInstance().read(fileChooser.showOpenDialog(ownerStage).getAbsolutePath()))
                list.add(new ReceiverList(line.get(0).trim(), line.get(1).trim(), line.get(2).trim()));
        } catch (IOException e) {
            System.err.println("Cannot read the excel file");
            
        }
    }
}
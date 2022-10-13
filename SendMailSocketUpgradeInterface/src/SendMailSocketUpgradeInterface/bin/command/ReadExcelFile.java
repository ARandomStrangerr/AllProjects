package SendMailSocketUpgradeInterface.bin.command;

import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.*;
import SendMailSocketUpgradeInterface.ui.EmailTableData;

import java.io.File;

public class ReadExcelFile implements Command {
    private final Stage stage;
    private final ObservableList<EmailTableData> tableData;

    public ReadExcelFile(Stage stage, ObservableList<EmailTableData> tableData) {
        this.stage = stage;
        this.tableData = tableData;
    }

    @Override
    public boolean execute() {
        FileChooser fc = new FileChooser();
        File file;
        try {
            file = fc.showOpenDialog(stage).getAbsoluteFile();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        new Thread(() -> {
            Workbook workbook;
            try {
                workbook = WorkbookFactory.create(file);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
            Sheet sheet = workbook.getSheetAt(0);
            DataFormatter df = new DataFormatter();
            for (Row row : sheet) {
                String email = df.formatCellValue(row.getCell(0));
                String attachment = df.formatCellValue(row.getCell(1));
                tableData.add(new EmailTableData(email, attachment));
            }
            try {
                workbook.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        return true;
    }
}

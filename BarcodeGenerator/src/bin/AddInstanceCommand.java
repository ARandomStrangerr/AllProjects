package bin;

import bin.file_operation.Excel;
import bin.file_operation.FileOperation;
import bin.file_operation.TextFile;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import user_interface.ErrorPane;
import user_interface.TableData;

import java.io.IOException;

public final class AddInstanceCommand implements Command {
    private final ObservableList<TableData> data;
    private final Stage ownerStage;

    public AddInstanceCommand(ObservableList<TableData> data, Stage ownerStage) {
        this.data = data;
        this.ownerStage = ownerStage;
    }

    @Override
    public void execute() {
        try {
            String path = new FileChooser().showOpenDialog(new Stage()).getAbsolutePath();
            FileOperation fileOperation = null;
            if (path.contains(".txt")) {
                fileOperation = new TextFile(path);
            } else if (path.contains(".xlsx") || path.contains(".xls")) {
                fileOperation = new Excel(path);
            }
            for (String line : fileOperation.read()) {
                String[] split = line.split("::");
                data.add(new TableData(split[0], split[1]));
            }
        } catch (NullPointerException e) {
            new ErrorPane("Không có tệp tin nào được chỉ định", ownerStage);
        } catch (IOException e) {
            new ErrorPane("Không thể đọc tệp tin chỉ định", ownerStage);
        }
    }
}

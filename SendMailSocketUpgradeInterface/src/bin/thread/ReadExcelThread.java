package bin.thread;

import bin.command.OpenMessageWindow;
import bin.file.ExcelFile;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import user_interface.conrete_pane.MainPane;
import user_interface.table_item.ReceiverInfo;

import java.io.IOException;
import java.util.List;

public final class ReadExcelThread implements Runnable {
    private final ObservableList<ReceiverInfo> items;
    private final String path;

    public ReadExcelThread(ObservableList<ReceiverInfo> items, String path) {
        this.items = items;
        this.path = path;
    }

    @Override
    public void run() {
        ExcelFile excelFile = ExcelFile.getInstance();
        try {
            for (List<String> row : excelFile.read(path)) items.add(new ReceiverInfo(row.get(0), row.get(1)));
        } catch (IOException e) {
            System.err.println("Cannot read file");
            Platform.runLater(() -> new OpenMessageWindow("Không đọc được file chỉ định", MainPane.getInstance().getWindow()));
        }
    }
}

package bin.thread;

import file_operation.ExcelFile;
import javafx.collections.ObservableList;
import user_interface.PaneCreateInvoiceFromExcel;
import user_interface.table_content.Invoice;

import java.io.IOException;
import java.util.List;

public final class ReadInvoiceFromExcel extends ReadFileRunnable {
    @Override
    public void run() {
        String filePath = fileChooser.getPath();
        List<List<String>> excelTable;
        try {
            excelTable = ExcelFile.getInstance().read(filePath);
        } catch (IOException e) {
            return;
        }
        ObservableList<Invoice> tableItems = PaneCreateInvoiceFromExcel.getInstance().getInvoiceTableItems();
        int index = 0;
        for (List<String> row : excelTable) {
            index++;
            try {
                tableItems.add(
                        new Invoice(row.get(0),
                                row.get(1),
                                row.get(2),
                                row.get(3),
                                row.get(4),
                                row.get(5)
                        )
                );
            } catch (IndexOutOfBoundsException e) {
                System.err.println(index + " missing info");
            }
        }
    }
}

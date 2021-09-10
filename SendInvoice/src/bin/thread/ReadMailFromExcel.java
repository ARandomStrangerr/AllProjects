package bin.thread;

import file_operation.ExcelFile;
import javafx.collections.ObservableList;
import user_interface.PaneCreateInvoiceFromExcel;
import user_interface.table_content.Mail;

import java.io.IOException;
import java.util.List;
import java.util.TreeMap;

public final class ReadMailFromExcel extends ReadFileRunnable {
    //Singleton
    static private ReadMailFromExcel cache;

    public static ReadMailFromExcel getInstance() {
        if (cache == null) cache = new ReadMailFromExcel();
        return cache;
    }

    private final TreeMap<String, String> treeMap;
    private  ReadMailFromExcel(){
        treeMap = new TreeMap<>();
    }
    @Override
    public void run() {
        String id,
                mail,
                path;
        try {
            path = fileChooser.getPath();
        } catch (NullPointerException e){
            System.err.println("NO FILE HAS BEEN SELECTED");
            return;
        }
        ExcelFile excelFile = ExcelFile.getInstance();
        ObservableList<Mail> items = PaneCreateInvoiceFromExcel.getInstance().getMailTableItems();
        try {
            for (List<String> row : excelFile.read(path)) {
                try {
                    id = row.get(0);
                    mail = row.get(1);
                } catch (IndexOutOfBoundsException e){
                    continue;
                }
                treeMap.put(id, mail);
                items.add(new Mail(id, mail));
            }
        } catch (IOException e) {
            System.err.println("GIVEN FILE COULD NOT BE WRITTEN");
            return;
        }
    }

    public TreeMap<String ,String> getMailMap(){
        return treeMap;
    }
}

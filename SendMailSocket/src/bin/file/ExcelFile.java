package bin.file;

import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class ExcelFile implements FileInterface {
    public static ExcelFile instance;

    public static ExcelFile getInstance() {
        if (instance == null) instance = new ExcelFile();
        return instance;
    }

    private ExcelFile() {

    }

    @Override
    public List<String> read(String path) throws IOException {
        LinkedList<String> rows = new LinkedList<>();   //list of rows
        Workbook workbook = WorkbookFactory.create(new File(path)); //workbook excel file
        Sheet sheet = workbook.getSheetAt(0);   //open the 1st sheet
        DataFormatter formatter = new DataFormatter();  //data formatter
        for (Row row : sheet) {  //loop through rows
            StringBuilder sb = new StringBuilder(); //build String value for each row
            for (Cell cell : row) {
                if (sb.length() != 0) sb.append(":");
                sb.append(formatter.formatCellValue(cell));
            }
            rows.add(sb.toString());    //add String of rows into row
        }
        return rows;
    }

    @Override
    public void write(String path, List<String> lines) throws IOException {
        //todo add write to file
    }
}
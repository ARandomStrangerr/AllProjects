package bin.file;

import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class ExcelFile {
    private static ExcelFile cache;

    public static ExcelFile getInstance() {
        if (cache == null) cache = new ExcelFile();
        return cache;
    }

    private ExcelFile() {
    }

    public List<List<String>> read(String path) throws IOException {
        List<List<String>> rows = new LinkedList<>();
        Workbook workbook = WorkbookFactory.create(new File(path));
        Sheet sheet = workbook.cloneSheet(0);
        DataFormatter dataFormatter = new DataFormatter();
        for(Row row : sheet){
            List<String> cells = new LinkedList<>();
            for(Cell cell: row) cells.add(dataFormatter.formatCellValue(cell));
            rows.add(cells);
        }
        return rows;
    }
}

package bin.file;

import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public final class ExcelFile implements FileOperation {
    private static ExcelFile cache;

    public static ExcelFile getInstance() {
        if (cache == null) cache = new ExcelFile();
        return cache;
    }

    @Override
    public List<String> read(String path) throws IOException {
        LinkedList<String> data = new LinkedList<>();
        Workbook workbook = WorkbookFactory.create(new File(path));
        Sheet sheet = workbook.getSheetAt(0);
        DataFormatter dataFormatter = new DataFormatter();
        for (Row row : sheet) {
            StringBuilder thisRow = new StringBuilder();
            for (Cell cell : row) {
                if (thisRow.length() != 0) thisRow.append(":");
                thisRow.append(dataFormatter.formatCellValue(cell));
            }
            data.add(thisRow.toString());
        }
        workbook.close();
        return data;
    }

    public List<List<String>> readList(String path) throws IOException{
        Workbook workbook = WorkbookFactory.create(new File(path));
        Sheet sheet = workbook.getSheetAt(0);
        DataFormatter dataFormatter = new DataFormatter();
        List<List<String>> rows = new LinkedList<>();
        for(Row row : sheet) {
            List<String> cells = new LinkedList<>();
            for(Cell cell : row) cells.add(dataFormatter.formatCellValue(cell));
            rows.add(cells);
        }
        return rows;
    }

    @Override
    public void writeNew(String path, String data) {

    }

    @Override
    public void append(String path, String data) {

    }
}

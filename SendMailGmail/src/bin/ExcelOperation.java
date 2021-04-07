package bin;

import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class ExcelOperation implements FileOperation {
    @Override
    public List<String> read(String path) throws IOException {
        LinkedList<String> data = new LinkedList<>();
        Workbook workbook = WorkbookFactory.create(new File(path));
        Sheet sheet = workbook.getSheetAt(0);
        DataFormatter formatter = new DataFormatter();
        for (Row row : sheet) {
            String sb = formatter.formatCellValue(row.getCell(0)) +
                    ":" + formatter.formatCellValue(row.getCell(1)) +
                    ":" + formatter.formatCellValue(row.getCell(2));
            data.add(sb);
        }
        workbook.close();
        return data;
    }

    @Override
    public void write(String path) throws IOException {

    }
}

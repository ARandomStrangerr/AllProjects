package bin.file_operation;

import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public final class Excel implements FileOperation {
    private final String path;

    public Excel(String path) {
        this.path = path;
    }

    @Override
    public List<String> read() throws IOException {
        List<String> data = new LinkedList<>();
        Workbook workbook = WorkbookFactory.create(new File(path));
        Sheet sheet = workbook.getSheetAt(0);
        DataFormatter formatter = new DataFormatter();
        for (Row row : sheet) {
            StringBuilder sb = new StringBuilder();
            for (Cell cell : row) {
                if (sb.length() != 0) {
                    sb.append("::");
                }
                sb.append(formatter.formatCellValue(cell));
            }
            data.add(sb.toString());
        }
        return data;
    }

    @Override
    public void write(String data) throws IOException {

    }
}

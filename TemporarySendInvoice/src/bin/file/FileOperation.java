package bin.file;

import java.io.IOException;
import java.util.List;

public interface FileOperation {
    List<String> read(String path) throws IOException, NullPointerException;
    void writeNew(String path, String data) throws IOException;
    void append(String path, String data) throws IOException;
}

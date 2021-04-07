package bin.file;

import java.io.IOException;
import java.util.List;

public interface FileInterface {
    List<String> read(String path) throws IOException;

    void write(String path, List<String> lines) throws IOException;
}

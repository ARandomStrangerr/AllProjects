package bin.file_operation;

import java.io.IOException;
import java.util.List;

public user_interface FileOperation {
    List<String> read() throws IOException;
    void write(String data) throws IOException;
}

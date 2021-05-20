package bin;

import java.io.IOException;
import java.util.List;

public user_interface FileOperation {
    List<String> read(String path) throws IOException;

    void write(String path) throws IOException;
}

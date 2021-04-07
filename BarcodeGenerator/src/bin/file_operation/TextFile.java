package bin.file_operation;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public final class TextFile implements FileOperation {
    private final String path;

    public TextFile(String path) {
        this.path = path;
    }

    @Override
    public List<String> read() throws IOException {
        List<String> data = new LinkedList<>();
        BufferedReader br = new BufferedReader(new FileReader(path));
        for(String line = br.readLine() ; line != null ; line = br.readLine()){
            data.add(line);
        }
        br.close();
        return data;
    }

    @Override
    public void write(String data) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(path));
        bw.write(data);
        bw.flush();
        bw.close();
    }
}

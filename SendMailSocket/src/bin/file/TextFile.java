package bin.file;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class TextFile implements FileInterface {
    private static TextFile instance;

    public static TextFile getInstance() {
        if (instance == null) instance = new TextFile();
        return instance;
    }

    private TextFile() {
    }

    @Override
    public List<String> read(String path) throws IOException {
        List<String> lines = new LinkedList<>();
        BufferedReader br = new BufferedReader(new FileReader(path));
        for (String line = br.readLine(); line != null; line = br.readLine()) lines.add(line);
        br.close();
        return lines;
    }

    @Override
    public void write(String path, List<String> lines) throws IOException {
        BufferedWriter bw = new BufferedWriter((new FileWriter(path)));
        for (String line : lines) {
            bw.write(line);
            bw.flush();
        }
        bw.close();
    }
}

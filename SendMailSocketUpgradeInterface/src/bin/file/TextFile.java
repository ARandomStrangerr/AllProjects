package bin.file;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public final class TextFile {
    private static TextFile cache;

    public static TextFile getInstance() {
        if (cache == null) cache = new TextFile();
        return cache;
    }

    private TextFile() {
    }

    public void write(String path, String data) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(path));
        bw.write(data);
        bw.newLine();
        bw.flush();
        bw.close();
    }

    public List<String> read(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        LinkedList<String> data = new LinkedList<>();
        for (String line = br.readLine(); line != null; line = br.readLine()) data.add(line);
        br.close();
        return data;
    }
}

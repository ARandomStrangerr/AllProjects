package bin.file;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public final class TextFile implements FileOperation {
    private static TextFile cache;

    public static TextFile getInstance() {
        if (cache == null) cache = new TextFile();
        return cache;
    }

    private TextFile() {

    }

    @Override
    public List<String> read(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        LinkedList<String> data = new LinkedList<>();
        for (String line = br.readLine(); line != null; line = br.readLine()) {
            data.add(line);
        }
        br.close();
        return data;
    }

    @Override
    public void writeNew(String path, String data) throws IOException {
        BufferedWriter br = new BufferedWriter(new FileWriter(path));
        br.write(data);
        br.flush();
        br.close();
    }

    @Override
    public void append(String path, String data) throws IOException {
        BufferedWriter br = new BufferedWriter(new FileWriter(path, true));
        br.write(data);
        br.flush();
        br.close();
    }

    public void append(String path, List<String> data) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (String item : data) {
            if (sb.length() != 0) sb.append("\n");
            sb.append(item);
        }
        append(path, sb.toString());
    }
}

package bin;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class TextFileOperation implements FileOperation {
    private BufferedWriter bw;
    @Override
    public List<String> read(String path) throws IOException {
        LinkedList<String> lines = new LinkedList<>();
        BufferedReader br = new BufferedReader(new FileReader(path));
        for (String line = br.readLine(); line != null; line = br.readLine()) {
            lines.add(line);
        }
        br.close();
        return lines;
    }

    @Override
    public void write(String path) { }

    public void createLogFile()throws IOException{
        bw = new BufferedWriter(new FileWriter("log.txt",true));
    }

    public void append(String line){
        try {
            bw.write(line);
            bw.flush();
        } catch (IOException ignore){}
    }
    public void closeLogFile()throws IOException{
        bw.close();
    }
}

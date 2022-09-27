package bin.command;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class WriteToLog implements Command {

    private final String username, password, serverAddr;
    private final int port;

    public WriteToLog(String username, String password, String serverAddr, int port) {
        this.username = username;
        this.password = password;
        this.serverAddr = serverAddr;
        this.port = port;
    }

    @Override
    public boolean execute() {
        new Thread(() -> {
            BufferedWriter bw;
            try {
                bw = new BufferedWriter(new FileWriter("log.txt"));
                bw.write(String.format("username=%s\n" +
                                "password=%s\n" +
                                "serverAddr=%s\n" +
                                "port=%d",
                        username, password, serverAddr, port));
                bw.close();
                ReadFromLog.tbl.put("username", username);
                ReadFromLog.tbl.put("password", password);
                ReadFromLog.tbl.put("serverAddr", serverAddr);
                ReadFromLog.tbl.put("port", String.valueOf(port));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        return true;
    }
}

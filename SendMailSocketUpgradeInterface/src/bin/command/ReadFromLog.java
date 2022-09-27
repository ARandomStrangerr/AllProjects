package bin.command;

import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

public class ReadFromLog implements Command {
    public static Hashtable<String, String> tbl = new Hashtable<>();

    private final Stage stage;

    public ReadFromLog(Stage stage) {
        this.stage = stage;
    }

    @Override
    public boolean execute() {
        new Thread(() -> {
            try {
                BufferedReader br = new BufferedReader(new FileReader("log.txt"));
                for (String line = br.readLine(); line != null; line = br.readLine()) {
                    String[] args = line.split("=");
                    switch (args[0]) {
                        case "username":
                            ReadFromLog.tbl.put("username", args[1]);
                        case "password":
                            ReadFromLog.tbl.put("password", args[1]);
                        case "serverAddr":
                            ReadFromLog.tbl.put("serverAddr", args[1]);
                        case "port":
                            ReadFromLog.tbl.put("port", args[1]);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                Platform.runLater(() -> new OpenRedMessageWindow(stage, "Cài đặt ban đầu của phần mềm chưa được thực hiện.\nVui lòng truy cập Cài đặt để điền").execute());
            }
        }).start();
        return false;
    }
}

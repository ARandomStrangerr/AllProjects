package user_interface.command;

import bin.file.FileInterface;
import bin.file.TextFile;
import bin.smtp.CustomMailServer;
import bin.smtp.ReceiverStructure;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import user_interface.table_item.ReceiverInfo;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class Send implements CommandInterface {
    private final ObservableList<ReceiverInfo> receiverInfo;
    private final String subject, body, folderPath;

    public Send(ObservableList<ReceiverInfo> receiverInfo, String subject, String body, String folderPath) {
        this.receiverInfo = receiverInfo;
        this.subject = subject;
        this.body = body;
        this.folderPath = folderPath;
    }

    @Override
    public void execute() {
        //find and read the file which contains login info
        FileInterface textFile = TextFile.getInstance();
        LinkedHashMap<String, String> config = new LinkedHashMap<>();
        try {
            for (String line : textFile.read("server_config.txt")) {
                String[] properties = line.split(":");
                config.put(properties[0], properties[1]);
            }
            if (!(config.containsKey("username") && config.containsKey("password") && config.containsKey("serverAddress") && config.containsKey("port")))
                throw new IOException();
        } catch (IOException e) {
            System.err.println("MISSING NEEDED FILE");
            VBox vbox = new VBox(new Label("Thông tin server chưa được thiết lập"));
            vbox.setPadding(new Insets(25, 25, 25, 25));
            Stage errorStage = new Stage();
            errorStage.setScene(new Scene(vbox));
            errorStage.show();
            return;
        }
        //convert to data
        LinkedList<ReceiverStructure> list = new LinkedList<>();
        for (ReceiverInfo entry : receiverInfo) {
            list.add(new ReceiverStructure(folderPath, entry.getAttachmentFileName(), entry.getEmailAddress()));
        }
        //send the mails
        CustomMailServer mailServer = new CustomMailServer();
        String username = "tckt@vnua.edu.vn",
                password = "Hvnn2020",
                address = "mail.vnua.edu.vn";
        int port = 25;
        Label displayMsg = new Label("Đang gởi");
        VBox vbox = new VBox(displayMsg);
        vbox.setPadding(new Insets(25, 25, 25, 25));
        Stage messageStage = new Stage();
        messageStage.setScene(new Scene(vbox));
        messageStage.show();
        Thread mailSendThread = new Thread(() -> {
            try {
                mailServer.send(address, port, username, password, subject, body, list);
            } catch (IOException e) {
                e.printStackTrace();
            }
            StringBuilder sb = new StringBuilder();
            for (ReceiverStructure item : mailServer.getErrorList()) {
                if (sb.length() != 0) sb.append("\n");
                sb.append(item.toString());
            }
            try {
                TextFile.getInstance().write("log.txt", sb.toString());
            } catch (IOException e) {

            }
            Platform.runLater(() -> displayMsg.setText("Hoàn Thành"));
        });
        mailSendThread.start();
    }
}

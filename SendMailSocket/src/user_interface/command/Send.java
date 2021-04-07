package user_interface.command;

import bin.file.FileInterface;
import bin.file.TextFile;
import bin.smtp.CustomMailServer;
import javafx.collections.ObservableList;
import user_interface.table_item.ReceiverInfo;

import java.io.IOException;
import java.util.LinkedHashMap;

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

        }
        CustomMailServer mailServer = new CustomMailServer();
        String username = "tckt@vnua.edu.vn",
                password = "Hvnn2020",
                address = "mail.vnua.edu.vn";
        int port = 25;
//        Thread mailSendThread = new Thread(() -> {
        try {
            mailServer.openConnection(username, password, address, port);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return;
        }
        for (ReceiverInfo receiver : receiverInfo) {
            try {
                mailServer.send(config.get("username"), receiver.getEmailAddress(), subject, body, folderPath + (char) 47 + receiver.getAttachmentFileName());
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            mailServer.closeConnection();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
//        });
//        mailSendThread.setName("SEND MAIL THREAD");
//        mailSendThread.start();
    }
}

package bin.thread;

import bin.command.OpenMessageWindow;
import bin.command.OpenUncloseableMessageWindow;
import bin.file.TextFile;
import bin.smtp.Office365Server;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import user_interface.PaneMain;
import user_interface.PaneOnlyMessage;
import user_interface.table_item.ReceiverInfo;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.*;

public class SendThread implements Runnable {

    public SendThread() {
    }

    @Override
    public void run() {
        PaneMain paneMain = PaneMain.getInstance();
        String messageBody = paneMain.getMessageBody(),
                messageSubject = paneMain.getMessageSubject(),
                attachmentFolder = paneMain.getAttachmentFolder();
        List<ReceiverInfo> items = paneMain.getItems();
        ObservableList<ReceiverInfo> unsentItems = FXCollections.observableArrayList();
        Map<String, String> map = new LinkedHashMap<>();
        TextFile textFile = TextFile.getInstance();
        if (messageBody.isBlank()) {
            Platform.runLater(() -> new OpenMessageWindow("Nội dung thư chưa được điền", PaneMain.getInstance().getWindow()).execute());
            return;
        }
        if (messageSubject.isBlank()) {
            Platform.runLater(() -> new OpenMessageWindow("Tiêu đề thư chưa được điền", PaneMain.getInstance().getWindow()).execute());
        }
        if (items.isEmpty()) {
            Platform.runLater(() -> new OpenMessageWindow("Danh sách người nhận trống", PaneMain.getInstance().getWindow()).execute());
            return;
        }
        try {
            for (String line : textFile.read("config.txt")) {
                String[] lineArr = line.split("=");
                map.put(lineArr[0], lineArr[1]);
            }
        } catch (IOException e) {
            Platform.runLater(() -> new OpenMessageWindow("Thông tin server chưa được thiết lập", PaneMain.getInstance().getWindow()));
            System.err.println("Cannot read text file");
            return;
        } catch (IndexOutOfBoundsException e) {
            Platform.runLater(() -> new OpenMessageWindow("Thông tin server chưa được thiết lập chính xác", PaneMain.getInstance().getWindow()).execute());
            System.err.println("Missing information");
            return;
        }
        if (!map.containsKey("serverAddress")
                || !map.containsKey("serverPort")
                || !map.containsKey("username")
                || !map.containsKey("password")) {
            Platform.runLater(() -> new OpenMessageWindow("Không đủ thông tin cần thiết", PaneMain.getInstance().getWindow()));
            System.err.println("Missing");
            return;
        }
        Office365Server server = new Office365Server(map.get("serverAddress"),
                Integer.parseInt(map.get("serverPort")),
                map.get("username"),
                map.get("password"));
        Platform.runLater(() -> new OpenUncloseableMessageWindow("Vui lòng đợi", paneMain.getWindow()).execute());
        while (!items.isEmpty()) {
            ReceiverInfo currentItem = items.remove(0);
            String attachmentFilePath = attachmentFolder.isEmpty() || currentItem.getVariableField().isEmpty() ? null : attachmentFolder + (char) 47 + currentItem.getVariableField();
            try {
                server.newMsg();
                server.setSender(map.get("username"));
                server.setReceiver(currentItem.getEmail());
                server.setSubject(messageSubject);
                server.setBody(messageBody);
                if (attachmentFilePath != null) server.setAttachment(attachmentFilePath);
                server.send();
            } catch (MessagingException | IOException e) {
                unsentItems.add(currentItem);
            }
            String displayMsg = "Còn " + items.size();
            Platform.runLater(() -> PaneOnlyMessage.getInstance().setDisplayMessageLabel(displayMsg));
        }
        Platform.runLater(() -> PaneOnlyMessage.getInstance().getWindow().closeCurrentStage());
        Platform.runLater(() -> new OpenMessageWindow("Hoàn Thành", paneMain.getWindow()).execute());
        paneMain.setItems(unsentItems);
    }
}
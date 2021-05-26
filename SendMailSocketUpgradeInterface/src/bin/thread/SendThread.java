package bin.thread;

import bin.command.OpenLeftOverMailWindow;
import bin.command.OpenMessageWindow;
import bin.command.OpenUncloseableMessageWindow;
import bin.file.TextFile;
import bin.smtp.CustomMailServer;
import javafx.application.Platform;
import user_interface.PaneMain;
import user_interface.PaneUpperMessage;
import user_interface.table_item.ReceiverInfo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketException;
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
        List<ReceiverInfo> items = paneMain.getItems(),
                unsentItems = new LinkedList<>();
        CustomMailServer customMailServer = new CustomMailServer();
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
        Platform.runLater(() -> new OpenUncloseableMessageWindow("Đang gởi", paneMain.getWindow()).execute());
        while (!items.isEmpty()) {
            ReceiverInfo currentItem = items.remove(0);
            String attachmentFilePath = attachmentFolder.isEmpty() || currentItem.getVariableField().isEmpty() ? null : attachmentFolder + (char) 92 + currentItem.getVariableField();
            try {
                customMailServer.send(map.get("serverAddress"),
                        Integer.parseInt(map.get("serverPort")),
                        map.get("username"),
                        map.get("password"), currentItem.getEmail(),
                        messageSubject,
                        messageBody,
                        attachmentFilePath);
            } catch (NoSuchElementException e) {
                System.err.println(currentItem.getEmail() + " MAIL DNE");
                currentItem.setVariableField("Tài khoản email không tồn tại");
                unsentItems.add(currentItem);
            } catch (FileNotFoundException e) {
                System.err.println(currentItem.getEmail() + " FILE DNE");
                currentItem.setVariableField("Tệp tin đính kèm không tồn tại");
                unsentItems.add(currentItem);
            } catch (IllegalArgumentException e) {
                System.err.println("CANNOT LOGIN");
                Platform.runLater(() -> {
                    PaneUpperMessage.getInstance().getWindow().closeCurrentStage();
                    new OpenMessageWindow("Sai tên đăng nhập / mật khẩu", paneMain.getWindow()).execute();
                });
                return;
            } catch (SocketException e) {
                System.err.println("CANNOT ESTABLISH CONNECTION TO SERVER");
                Platform.runLater(() -> {
                    PaneUpperMessage.getInstance().getWindow().closeCurrentStage();
                    new OpenMessageWindow("Không thể thiết lập kết nối đến server", paneMain.getWindow()).execute();
                });
                return;
            } catch (IOException e) {
                System.err.println(currentItem.getEmail() + " CANNOT SEND BODY");
                currentItem.setVariableField("Server từ chối thư");
                unsentItems.add(currentItem);
            }
            String displayMsg = "còn lại " + items.size();
            Platform.runLater(() -> PaneUpperMessage.getInstance().setDisplayMessageLabel(displayMsg));
        }
        Platform.runLater(() -> PaneUpperMessage.getInstance().getWindow().closeCurrentStage());
        if (unsentItems.size() > 0) {
            Platform.runLater(() -> new OpenLeftOverMailWindow(unsentItems, paneMain.getWindow()).execute());
        } else {
            Platform.runLater(() -> new OpenMessageWindow("Hoàn Thành", paneMain.getWindow()));
        }
    }
}
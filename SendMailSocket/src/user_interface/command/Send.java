package user_interface.command;

import bin.file.FileInterface;
import bin.file.TextFile;
import bin.smtp.CustomMailServer;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import user_interface.ErrorPane;
import user_interface.table_item.ReceiverInfo;

import javax.sound.midi.Receiver;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketException;
import java.nio.file.NoSuchFileException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.NoSuchElementException;

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
        //send the mails
        String username = "tckt@vnua.edu.vn",
                password = "Hvnn2020",
                address = "mail.vnua.edu.vn";
        int port = 25;
        Label displayMsg = new Label("Đang gởi");
        VBox vbox = new VBox(displayMsg);
        vbox.setPadding(new Insets(25, 25, 25, 25));
        vbox.setPrefSize(250, 150);
        vbox.setAlignment(Pos.CENTER);
        Stage messageStage = new Stage();
        messageStage.setScene(new Scene(vbox));
        messageStage.show();
        //thread
        Thread mailSendThread = new Thread(() -> {
            ObservableList<ReceiverInfo> errorList = FXCollections.observableArrayList();
            CustomMailServer mail = new CustomMailServer();
            while (!receiverInfo.isEmpty()) {
                ReceiverInfo receiver = receiverInfo.remove(0);
                String path = folderPath == null || receiver.getAttachmentFileName() == null ? null : folderPath + (char) 92 + receiver.getAttachmentFileName();
//                try {
//                    mail.send(address, port, username, password, receiver.getEmailAddress(), subject, body, path);
//                } catch (NoSuchElementException e) {
//                    System.err.println(receiver.getEmailAddress() + " MAIL DNE");
//                    receiver.setAttachmentFileName("Tài khoản email không tồn tại");
//                    errorList.add(receiver);
//                } catch (FileNotFoundException e) {
//                    System.err.println(receiver.getEmailAddress() + " FILE DNE");
//                    receiver.setAttachmentFileName("Tệp tin đính kèm không tồn tại");
//                    errorList.add(receiver);
//                } catch (IllegalArgumentException e) {
//                    System.err.println("CANNOT LOGIN");
//                    Platform.runLater(() -> displayMsg.setText("Lỗi:\nTên đăng nhập / mật khẩu không chính xác"));
//                    return;
//                } catch (SocketException e) {
//                    System.err.println("CANNOT ESTABLISH CONNECTION TO SERVER");
//                    Platform.runLater(() -> displayMsg.setText("Lỗi:\nKhông thể thiết lập kết nối"));
//                    return;
//                } catch (IOException e) {
//                    System.err.println(receiver.getEmailAddress() + " CANNOT SEND BODY");
//                    receiver.setAttachmentFileName("Server từ chối thư");
//                    errorList.add(receiver);
//                }
                System.out.println(path);
                String displayStr = "Đang gởi\n(còn lại " + receiverInfo.size() + ")";
                Platform.runLater(() -> displayMsg.setText(displayStr));
            }
            if (errorList.size() != 0) {
                Platform.runLater(() -> {
                    ErrorPane errorPane = new ErrorPane();
                    errorPane.setTableItems(errorList);
                    Stage errorStage = new Stage();
                    errorStage.setScene(new Scene(errorPane.getPane()));
                    errorStage.show();
                });
            } else {
                Platform.runLater(() -> displayMsg.setText("Hoàn Thành"));
            }
        });
        mailSendThread.start();
    }
}

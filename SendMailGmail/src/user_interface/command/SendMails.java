package user_interface.command;

import bin.GMailAPI;
import bin.TextFileOperation;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import user_interface.Browser;
import user_interface.ErrorWarning;
import user_interface.HoldStage;
import user_interface.UnableToSendTable;
import user_interface.table_datatype.ReceiverList;
import user_interface.table_datatype.UnableToSend;

import java.io.IOException;
import java.time.LocalDate;

public class SendMails implements Command {
    private final ObservableList<ReceiverList> mailList;
    private final String subject, body, attachmentPath;
    private final Stage ownerStage;

    public SendMails(ObservableList<ReceiverList> mailList, String subject, String body, String attachmentPath, Stage ownerStage) {
        this.subject = subject;
        this.body = body;
        this.mailList = mailList;
        this.attachmentPath = attachmentPath;
        this.ownerStage = ownerStage;
    }

    @Override
    public void execute() {
        //initiate gmail api and its necessary information to construct an email
        GMailAPI gmailAPI;
        try {
            gmailAPI = new GMailAPI(subject, body, attachmentPath);
        } catch (IOException e) {
            new ErrorWarning(new Stage(), "Thiếu tệp tin cần thiết").openWindow();
            return;
        }

        //step 1 : connect to Auth2.0 server to get identification code
        Stage browserStage = new Stage();
        new Browser(browserStage, gmailAPI.getRequestURI()).openWindow();
        browserStage.setOnCloseRequest(event -> {
            try {
                gmailAPI.closeSocket();
            } catch (Exception ignore) {
            }
            browserStage.close();
        });

        HoldStage holdStage = new HoldStage(new Stage(), ownerStage, "Vui lòng đợi");
        //switch thread to listen / response to the return message of the identification code , preventing clog the JavaFx thread
        Runnable listener = () -> {
            //open socket to listen to server response and handle the permission from user
            if (!gmailAPI.getCode()) {                      //fail to get code
                new ErrorWarning(new Stage(), "Người dùng từ chối ủy quyền").openWindow();
                return;
            }
            //close browser when receive the message from auth2.0 server
            Platform.runLater(() -> {
                browserStage.close();
                holdStage.openWindow();
            });

            //Step 2: exchange authorization code for access and refresh token
            if (!gmailAPI.getToken()) {                      //fail to get token
                new ErrorWarning(new Stage(), "Không thể kết nối tới Google Server").openWindow();
                return;
            }

            //Step 3 : using token to send message
            //create list for email which met errors
            ObservableList<UnableToSend> list = FXCollections.observableArrayList();
            TextFileOperation errorLog = new TextFileOperation();
            try {
                errorLog.createLogFile();
            } catch (IOException ignore) {
            }
            errorLog.append(LocalDate.now().toString());
            //loop through each email to send
            for (ReceiverList receiver : mailList) {
                try {
                    gmailAPI.sendEmail(receiver.getEmail(), receiver.getAttachment());
                } catch (IOException e) {
                    list.add(new UnableToSend(receiver.getEmail(), e.getMessage()));
                    errorLog.append(receiver.getEmail() + " : " + e.getMessage());
                }
            }
            Platform.runLater(holdStage::close);
            if (list.size() != 0) {
                Platform.runLater(() -> new UnableToSendTable(new Stage(), "Lỗi", list).openWindow());
            } else {
                Platform.runLater(() -> new ErrorWarning(new Stage(), "Hoàn Thành").openWindow());
            }
        };
        Thread listenerThread = new Thread(listener);
        listenerThread.start();
    }
}
package bin.command;

import bin.smtp.Office365Server;
import ui.PaneBlockMessage;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import ui.EmailTableData;

import javax.mail.MessagingException;
import java.io.IOException;

public class SendEmail implements Command {
    private final String folder, username, password, serverAddress, subject, body;
    private final int port;
    private final ObservableList<EmailTableData> tableData;
    private final Stage stage;

    public SendEmail(String username,
                     String password,
                     String serverAddress,
                     int port,
                     String folder,
                     ObservableList<EmailTableData> tableData,
                     String subject,
                     String body,
                     Stage stage) {
        this.folder = folder;
        this.username = username;
        this.password = password;
        this.serverAddress = serverAddress;
        this.port = port;
        this.tableData = tableData;
        this.subject = subject;
        this.body = body;
        this.stage = stage;
    }

    @Override
    public boolean execute() {
        PaneBlockMessage paneBlockMessage = new PaneBlockMessage(stage);
        new Thread(() -> {
            StringBuilder errorMsg = new StringBuilder();
            Office365Server mailService = new Office365Server(this.serverAddress, this.port, this.username, this.password);
            int counter = 0;
            int finalCounter1 = counter;
            Platform.runLater(() -> {
                paneBlockMessage.setDisplayLabel(String.format("Hoàn thành gửi đi %d thư", finalCounter1));
                paneBlockMessage.getStage().show();
            });
            for (EmailTableData inst : tableData) {
                counter++;
                try {
                    mailService.newMsg();
                    mailService.setSender(username);
                    mailService.setReceiver(inst.getEmail());
                    mailService.setSubject(subject);
                    mailService.setBody(body);
                    if (!inst.getFile().trim().isEmpty()) mailService.setAttachment(String.format("%s/%s", folder, inst.getFile()));
                    mailService.send();
                    int finalCounter = counter;
                    Platform.runLater(() -> paneBlockMessage.setDisplayLabel(String.format("Hoàn thành gửi đi %d thư", finalCounter)));
                } catch (MessagingException | IOException e) {
                    e.printStackTrace();
                    if (errorMsg.length() != 0) errorMsg.append("\n");
                    errorMsg.append(e.getMessage());
                }
            }
            Platform.runLater(() -> {
                paneBlockMessage.getStage().close();
                if (errorMsg.length() != 0) new OpenRedMessageWindow(stage, errorMsg.toString()).execute();
            });
        }).start();
        return true;
    }
}

package SendMailSocketUpgradeInterface.bin.command;

import SendMailSocketUpgradeInterface.bin.smtp.Office365Server;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import SendMailSocketUpgradeInterface.ui.EmailTableData;

import javax.mail.MessagingException;

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
        new Thread(() -> {
            StringBuilder errorMsg = new StringBuilder();
            Office365Server mailService = new Office365Server(this.serverAddress, this.port, this.username, this.password);
            for (EmailTableData inst : tableData) {
                try {
                    mailService.newMsg();
                    mailService.setSender(username);
                    mailService.setReceiver(inst.getEmail());
                    mailService.setSubject(subject);
                    mailService.setBody(body);
                    if (!inst.getFile().trim().isEmpty())
                        mailService.setBody(String.format("%s/%s", folder, inst.getFile()));
                    mailService.send();
                } catch (MessagingException e) {
                    e.printStackTrace();
                    if (errorMsg.length() != 0) errorMsg.append("\n");
                    errorMsg.append(e.getMessage());
                }
            }
            if (errorMsg.length() != 0) {
                Platform.runLater(() -> new OpenRedMessageWindow(stage, errorMsg.toString()).execute());
            }
        }).start();
        return true;
    }
}

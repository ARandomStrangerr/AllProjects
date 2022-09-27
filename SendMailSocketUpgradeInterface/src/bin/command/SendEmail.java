package bin.command;

import bin.smtp.Office365Server;
import javafx.collections.ObservableList;
import ui.EmailTableData;

import javax.mail.MessagingException;

public class SendEmail implements Command {
    private final String folder, username, password, serverAddress, subject, body;
    private final int port;
    private final ObservableList<EmailTableData> tableData;

    public SendEmail(String username,
                     String password,
                     String serverAddress,
                     int port,
                     String folder,
                     ObservableList<EmailTableData> tableData,
                     String subject,
                     String body) {
        this.folder = folder;
        this.username = username;
        this.password = password;
        this.serverAddress = serverAddress;
        this.port = port;
        this.tableData = tableData;
        this.subject = subject;
        this.body = body;
    }

    @Override
    public boolean execute() {
        new Thread(() -> {
            Office365Server mailService = new Office365Server(this.serverAddress, this.port, this.username, this.password);
            for (EmailTableData inst : tableData) {
                try {
                    mailService.newMsg();
                    mailService.setSender(username);
                    mailService.setReceiver(inst.getEmail());
                    mailService.setSubject(subject);
                    mailService.setBody(body);
                    mailService.send();
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return true;
    }
}

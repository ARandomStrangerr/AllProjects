package bin.smtp;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class Office365Server {
    private final Message msg;
    private Multipart multipart;

    public Office365Server(String smtpServer,
                           int smtpPort,
                           String username,
                           String password) {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", smtpServer);
        prop.put("mail.smtp.port", smtpPort);

        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        msg = new MimeMessage(session);
    }

    public void newMsg() throws MessagingException {
        multipart = new MimeMultipart();
        msg.setContent(multipart);
    }

    public void setSubject(String subject) throws MessagingException {
        msg.setSubject(subject);
    }

    public void setBody(String body) throws MessagingException {
        MimeBodyPart part = new MimeBodyPart();
        part.setContent(body, "text/html");
        multipart.addBodyPart(part);
    }

    public void setSender(String sender) throws MessagingException {
        msg.setFrom(new InternetAddress(sender));
    }

    public void setReceiver(String receiver) throws MessagingException {
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
    }

    public void setAttachment(String attachmentPath) throws MessagingException, IOException {
        MimeBodyPart part = new MimeBodyPart();
        part.attachFile(new File(attachmentPath));
        multipart.addBodyPart(part);
    }

    public void send() throws MessagingException {
        Transport.send(msg);
    }
}

package bin.smtp;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class CustomMailServer {
    private Socket mailSocket;
    private OutputStream os;
    private BufferedReader is;
    private Thread thread;

    public CustomMailServer() {
    }

    public void openConnection(String username, String password, String serverAddress, int port) throws IOException, InterruptedException {
        long waitTime = 3000;
        //open new socket
        mailSocket = new Socket(serverAddress, port);   //open new socket
        os = mailSocket.getOutputStream();  //open output stream
        is = new BufferedReader(new InputStreamReader(mailSocket.getInputStream()));    //open input stream
        //thread to see response from server
        thread = new Thread(() -> {
            try {
                String line;
                while ((line = is.readLine()) != null) {
                    System.out.println("SERVER : " + line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        //write to server to initiate connection
        writeToStream("EHLO " + serverAddress);
        Thread.sleep(waitTime);
        writeToStream("AUTH LOGIN");
        Thread.sleep(waitTime);
        writeToStream(Base64.getEncoder().encodeToString(username.getBytes(StandardCharsets.UTF_8)));
        Thread.sleep(waitTime);
        writeToStream(Base64.getEncoder().encodeToString(password.getBytes(StandardCharsets.UTF_8)));
        Thread.sleep(waitTime);
    }

    public void send(String from, String to, String subject, String body, String attachmentPart) throws IOException, InterruptedException {
        writeToStream("MAIL FROM:<tckt@vnua.edu.vn>");
        Thread.sleep(1000);
        writeToStream("RCPT TO:<billslim0996@gmail.com>");
        Thread.sleep(1000);
        writeToStream("DATA");
        Thread.sleep(1000);
        writeToStream(createPayload(from, to, subject, body, attachmentPart));
        Thread.sleep(1000);
        writeToStream(".");
    }

    public void closeConnection() throws IOException, InterruptedException {
        writeToStream("QUIT");
        os.close();
        is.close();
        mailSocket.close();
        thread.join();
    }

    private String createPayload(String from, String to, String subject, String body, String attachmentPart) throws IOException {
        StringBuilder sb = new StringBuilder();
        //create subject part of the message
        sb.append("Content-Type: multipart/mixed; boundary=\"foo_bar_baz\"\n")
                .append("MIME-Version: 1.0\n")
                .append("to:").append(to).append("\n")
                .append("from:").append(from).append("\n")
                .append("subject:").append(subject).append("\n")
                .append("\n")
                .append("--foo_bar_baz\n")
                .append("Content-Type: text/plain; charset=\"UTF-8\"\n")
                .append("MIME-Version: 1.0\n")
                .append("Content-Transfer-Encoding: 7bit\n")
                .append("\n")
                .append(body).append("\n")
                .append("\n");
        //create attachment part of the message if given
        if (attachmentPart != null)
            sb.append("--foo_bar_baz\n")
                    .append("Content-Type: application/pdf\n")
                    .append("MIME-Version: 1.0\n")
                    .append("Content-Transfer-Encoding: base64\n")
                    .append("Content-Disposition: attachment; filename=\"").append(attachmentPart).append("\"\n")
                    .append("\n")
                    .append(encodeFileBase64(attachmentPart)).append("\n")
                    .append("\n");
        //end of the payload
        sb.append("--foo_bar_baz--");
        return sb.toString();
    }

    private String encodeFileBase64(String filePath) throws IOException {
        //create file from the path
        File file = new File(filePath);
        //create stream from the file
        FileInputStream fis = new FileInputStream(file);
        //create byte array to hold the file
        byte[] byteArr = new byte[(int) file.length()];
        //convert the file into byte array
        fis.read(byteArr);
        //return the base64 encoded of the file
        return Base64.getEncoder().encodeToString(byteArr);
    }

    private void writeToStream(String data) throws IOException {
        os.write((data + "\r\n").getBytes());
        os.flush();
        System.out.println("CLIENT : " + data);
    }
}
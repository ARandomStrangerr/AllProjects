package bin.smtp;

import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;

public class CustomMailServer {
    private Socket mailSocket;
    private OutputStream os;
    private BufferedReader is;
    private String from, to;
    private final List<ReceiverStructure> errorList;
    int count = 0;

    public CustomMailServer() {
        errorList = new LinkedList<>();
    }

    public void send(String serverAddress,
                     int port,
                     String username,
                     String password,
                     String subject,
                     String body,
                     List<ReceiverStructure> receiverList) throws IOException, IllegalArgumentException {
        if (!openConnection(serverAddress, port)) throw new SocketTimeoutException();  //establish connection
        if (!login(username, password)) throw new IllegalArgumentException();   //login into the server
        while (!receiverList.isEmpty()) {
            ReceiverStructure currentPerson = receiverList.remove(0);
            if (!setFrom(username)) throw new IllegalArgumentException();
            if (!setTo(currentPerson.getEmail())){
                closeConnection();
                currentPerson.setError(ERROR.MAIL_DNE);
                errorList.add(currentPerson);
                break;
            }
            if (!setBody(subject,body,currentPerson.getFilePath())){
                currentPerson.setError(ERROR.ATTACHMENT_DNE);
                closeConnection();
                break;
            }
            count++;
        }
        if (!receiverList.isEmpty()){
            send(serverAddress, port, username, password,subject,body, receiverList);
        }
        closeConnection();
    }

    public List<ReceiverStructure> getErrorList() {
        return errorList;
    }

    /**
     * create connection to the server with specified address and port .
     * however , for this server , sometimes , it fail to establish the return stream ; hence , cannot know what problem does the server represent .
     * will retry until the server is connected
     *
     * @param address address to the server
     * @param port    port which the server use to send mail.
     * @return true if successfully create input and output socket with readable data
     * <p>false if this client fail to establish connection to mail server .</p>
     * @throws IOException is thrown when fail to initiate Socket , InputStream , OutputStream , Write to stream or read from stream .
     */
    public boolean openConnection(String address, int port) throws IOException {
        mailSocket = new Socket(address, port); //create socket
        mailSocket.setSoTimeout(2000);  //set timeout for reading
        os = mailSocket.getOutputStream();  //create input stream
        is = new BufferedReader(new InputStreamReader(mailSocket.getInputStream()));    //create output stream
        //send data to server to initiate connection
        writeToStream("EHLO " + address);
        writeToStream("AUTH LOGIN");
        //try to read something back from server to verify the input stream is good
        for (int counter = 0; counter < 10; counter++) {
            try {
                if (readFromStream("334 VXNlcm5hbWU6")) return true;   //this line might subject to change
            } catch (SocketTimeoutException e) {
                System.err.println("timeout");  //signify the error just due to timeout (debugging purposed)
            }
        }
        return false;
    }

    /**
     * login into the server after the successfully connected to server verified working input stream
     *
     * @param username name use to login into the server
     * @param password password associated with the given username
     * @return true when the authentication is correct
     * <p>false when the authentication is incorrect</p>
     * @throws IOException is thrown when fail to write to output stream or read from input stream
     */
    private boolean login(String username, String password) throws IOException {
        writeToStream(Base64.getEncoder().encodeToString(username.getBytes(StandardCharsets.UTF_8)));   //send username with
        readFromStream(null);
        writeToStream(Base64.getEncoder().encodeToString(password.getBytes(StandardCharsets.UTF_8)));
        return readFromStream("235 2.7.0 Authentication successful");
    }

    /**
     * set sender of the email , confirm this email with the server
     *
     * @param from the sender email
     * @return true - if server confirm this email is valid
     * <p>false otherwise</p>
     * @throws IOException if cannot send data
     */
    private boolean setFrom(String from) throws IOException {
        writeToStream("MAIL FROM:<" + from + ">");
        this.from = from;
        return readFromStream("250 2.1.0 Sender OK");
    }

    /**
     * set receiver of the email , confirm this is email with the server
     *
     * @param to the receiver email
     * @return ture - if server confirm this email is valid
     * <p>false otherwise</p>
     * @throws IOException if cannot send data
     */
    private boolean setTo(String to) throws IOException {
        writeToStream("RCPT TO:<" + to + ">");
        this.to = to;
        return readFromStream("250 2.1.5 Recipient OK");
    }

    /**
     * create MIME payload to deliver email
     *
     * @param subject        subject of the mail
     * @param body           body msg of the mail
     * @param attachmentPath attachment path if needed
     * @return true if the body message is valid
     * <p>false if the body cannot be create or file could not be read</p>
     * @throws IOException if cannot send data
     */
    private boolean setBody(String subject, String body, String attachmentPath) throws IOException {
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
        if (attachmentPath != null) {
            try {
                String attachmentBody = encodeFileBase64(attachmentPath);
                sb.append("--foo_bar_baz\n")
                        .append("Content-Type: application/pdf\n")
                        .append("MIME-Version: 1.0\n")
                        .append("Content-Transfer-Encoding: base64\n")
                        .append("Content-Disposition: attachment; filename=\"").append(attachmentPath).append("\"\n")
                        .append("\n")
                        .append(attachmentBody).append("\n")
                        .append("\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //end of the payload
        sb.append("--foo_bar_baz--");
        //send payload
        writeToStream("DATA");
        readFromStream(null);
        writeToStream(sb.toString());   //write the data which is needed to send
        writeToStream("."); //signify the data which is needed to send is over
        return readFromStream("250 2.6.0 Ok, message saved");
    }

    private void closeConnection() throws IOException {
        writeToStream("QUIT");
        os.close();
        is.close();
        mailSocket.close();
    }

    /**
     * convert a given file to byte arrays then encode it to base64
     *
     * @param filePath path leads to the file
     * @return encoded byte array base64 of given file
     * @throws IOException           the file cannot be read
     * @throws FileNotFoundException given file dne
     */
    private String encodeFileBase64(String filePath) throws IOException {
        File file = new File(filePath); //create file from the path
        FileInputStream fis = new FileInputStream(file);    //create stream from the file
        byte[] byteArr = new byte[(int) file.length()]; //create byte array to hold the file
        fis.read(byteArr); //convert the file into byte array
        return Base64.getEncoder().encodeToString(byteArr); //return the byte array with base64 encoded
    }

    /**
     * write given data into the output stream
     *
     * @param data data to write to output stream
     * @throws IOException if an I/O error occurs
     */
    private void writeToStream(String data) throws IOException {
        os.write((data + "\r\n").getBytes());
        os.flush();
        System.out.println("CLIENT : " + data);
    }

    /**
     * read one line of input stream and compare it with a given expected value .
     *
     * @param expectedValue return true when meet this value
     * @return true - the read line matches with the expected value
     * <p>false - the read line does not match with the given expected value</p>
     * @throws IOException if an I/O error occurs
     */
    private boolean readFromStream(String expectedValue) throws IOException {
        String line = is.readLine();
        System.out.println(line);
        if (expectedValue == null) return true;
        return line.equals(expectedValue);
    }
}
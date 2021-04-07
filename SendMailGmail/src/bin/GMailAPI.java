package bin;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;

public class GMailAPI {
    private final Map<String, String> credential, contendType;
    private final String subject, body, attachmentFolder;
    private ServerSocket serverSocket;
    private Socket socket;

    public GMailAPI(String subject, String body, String attachmentFolder) throws IOException {
        this.subject = Base64.getEncoder().encodeToString(subject.getBytes());
        this.body = constructBody(body);
        this.attachmentFolder = attachmentFolder;
        contendType = getMapFromFile("contentType.txt");
        credential = getMapFromFile("credential.txt");
    }

    /**
     * Step 1: send request to Google's Auth 2.0 server
     *
     * @return address to open in browser
     */
    public String getRequestURI() {
        return "https://accounts.google.com/o/oauth2/v2/auth?"
                + generateParameters("client_id", "redirect_uri", "response_type", "scope");
    }

    public boolean getToken() {
        try {
            String uri = "https://oauth2.googleapis.com/token",
                    param = generateParameters("code", "client_id", "client_secret", "redirect_uri", "grant_type"),
                    response = connectToURI(uri, param.getBytes(), false);
            String[] extractedParameter = response.replace("\\u007D", "")
                    .replaceAll("\\u007B", "")
                    .replaceAll("\"", "")
                    .split(",");
            for (String parameter : extractedParameter) {
                if (parameter.contains("access_token") || parameter.contains("refresh_token")) {
                    String[] parameterArr = parameter.split(":");
                    credential.put(parameterArr[0].trim(), parameterArr[1].trim());
                }
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    /**
     * wait for response of the Google's Auth 2.0 Server after granting permission
     *
     * @return response of the server
     */
    public boolean getCode() {
        try {
            serverSocket = new ServerSocket(4444);
            socket = serverSocket.accept();
            socket.setSoTimeout(10 * 1000);
            String response = getInputString(socket.getInputStream());
            if (!response.contains("access_denied")) {      //if user accepted the access
                credential.put("code", response.substring(response.indexOf("=") + 1, response.indexOf("&")));
            } else {                                        //if user declined the request
                return false;
            }
        } catch (IOException e) {
            closeSocket();
            return false;
        }
        closeSocket();
        return true;
    }

    public void closeSocket() {
        try {
            socket.close();
            serverSocket.close();
        } catch (Exception ignore) {
        }
    }

    public void sendEmail(String receiver, String fileName) throws IOException {
        String address = "https://www.googleapis.com/upload/gmail/v1/users/me/messages/send?uploadType=multipart&" + generateParameters("access_token"),
                message = constructSubject(receiver) + body;
        if (!fileName.isEmpty()) {
            message += constructAttachment(fileName);
        }
        message += "--foo_bar_baz--";
        System.out.println(message);
        connectToURI(address, message.getBytes(), true);
    }

    private String encodeFileToBase64(String filePath) throws IOException {
        File file = new File(filePath);
        byte[] byteArr = new byte[(int) file.length()];
        FileInputStream fis = new FileInputStream(file);
        fis.read(byteArr);
        return Base64.getEncoder().encodeToString(byteArr);
    }

    private String connectToURI(String uri, byte[] data, boolean requestProperty) throws IOException {
        HttpURLConnection con = (HttpURLConnection) new URL(uri).openConnection();
        con.setRequestMethod("POST");
        con.setUseCaches(false);
        con.setDoOutput(true);
        if (requestProperty) {
            con.setRequestProperty("Content-Type", "message/rfc822");
        }
        con.getOutputStream().write(data);
        return getInputString(con.getInputStream());
    }

    private String generateParameters(String... params) {
        StringBuilder sb = new StringBuilder();
        for (String param : params) {
            if (sb.length() != 0) {
                sb.append("&");
            }
            sb.append(param).append("=").append(this.credential.get(param));
        }
        return sb.toString();
    }

    private Map<String, String> getMapFromFile(String path) throws IOException {
        Map<String, String> map = new LinkedHashMap<>();
        for (String line : new TextFileOperation().read(path)) {
            String[] keyAndValue = line.split("=");
            map.put(keyAndValue[0], keyAndValue[1]);
        }
        return map;
    }

    private String getInputString(InputStream inputStream) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        for (String line = br.readLine(); line != null && !line.equals("Connection: keep-alive");
             line = br.readLine()) {
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }

    private String constructBody(String body) {
        return "--foo_bar_baz\n" +
                "Content-Type: text/plain; charset=\"UTF-8\"\n" +
                "MIME-Version: 1.0\n" +
                "Content-Transfer-Encoding: 7bit\n" +
                "\n" +
                body + "\n" +
                "\n";
    }

    private String constructSubject(String receiver) {
        return "Content-Type: multipart/mixed; boundary=\"foo_bar_baz\"\n" +
                "MIME-Version: 1.0\n" +
                "to: " + receiver + "\n" +
                "subject: =?UTF-8?B?" + subject + "?=\n"+
                "\n";
    }

    private String constructAttachment(String fileName) throws IOException {
        return "--foo_bar_baz\n" +
                "Content-Type:" + contendType.get(fileName.substring(fileName.indexOf("."))) + "\n" +
                "MIME-Version: 1.0\n" +
                "Content-Transfer-Encoding: base64\n" +
                "Content-Disposition: attachment; filename=" + "\"" + fileName + "\"\n" +
                "\n" +
                encodeFileToBase64(attachmentFolder + fileName) + "\n" +
                "\n";
    }
}

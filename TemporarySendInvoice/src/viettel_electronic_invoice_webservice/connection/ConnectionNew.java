package viettel_electronic_invoice_webservice.connection;

import node.JsonAnalyzer;
import node.NodeCurlyBrace;
import node.NodeSingleValue;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

public class ConnectionNew {
    private enum Address {
        ADDRESS("https://api-vinvoice.viettel.vn");
        private final String value;

        Address(String value) {
            this.value = value;
        }
    }

    private enum Action {
        TOKEN("/auth/login"),
        CREATE_INVOICE("/services/einvoiceapplication/api/InvoiceAPI/InvoiceWS/createInvoice");
        private final String value;

        Action(String value) {
            this.value = value;
        }
    }

    private enum ContentType {
        JSON("application/json"),
        URL_ENCODED(" application/x-www-form-urlencoded ");
        private final String value;

        ContentType(String value) {
            this.value = value;
        }
    }

    private enum Method {
        POST,
        GET
    }

    private final Map<String, String> requestProperties = new LinkedHashMap<>();

    public void getToken(String username, String password) throws IOException {
        NodeCurlyBrace nodeRoot = new NodeCurlyBrace();
        NodeSingleValue nodeUsername = new NodeSingleValue("username", username),
                nodePassword = new NodeSingleValue("password", password);
        nodeRoot.addAll(nodeUsername, nodePassword);
        requestProperties.put("Content-Type", ContentType.JSON.value);
        requestProperties.put("Accept", ContentType.JSON.value);
        String data = sendData(nodeRoot.getData(), Address.ADDRESS, Action.TOKEN, null, Method.POST);
        JsonAnalyzer analyzer = new JsonAnalyzer();
        NodeCurlyBrace root = (NodeCurlyBrace) analyzer.getRoot(data);
        NodeSingleValue accessToken = (NodeSingleValue) root.get("access_token");
        requestProperties.put("Cookie", accessToken.getName() + "=" + accessToken.getValue());
        System.out.println(accessToken.getValue());
    }

    public String send(String data, String taxCode) throws IOException {
        return sendData(data, Address.ADDRESS, Action.CREATE_INVOICE, taxCode, Method.POST);
    }

    private String sendData(String data, Address address, Action action, String taxCode, Method method) throws IOException {
        String urlAddress = taxCode == null ? address.value + action.value : address.value + action.value + "/" + taxCode;
        HttpURLConnection con = (HttpURLConnection) new URL(urlAddress).openConnection();
        con.setRequestMethod(method.name());
        con.setDoOutput(true);
        con.setUseCaches(false);
        for (Map.Entry<String, String> entry : requestProperties.entrySet())
            con.setRequestProperty(entry.getKey(), entry.getValue());
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(con.getOutputStream()));
        bw.write(data);
        bw.newLine();
        bw.flush();
        BufferedReader br;
        try {
            br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        } catch (IOException e) {
            br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
        }
        String line = br.readLine();
        System.out.println(line);
        br.close();
        bw.close();
        con.disconnect();
        return line;
    }
}

package viettel_invoice_api.connection;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;

public final class Connection {
    private final String encodedVerification, taxCode, service, contentType;

    public enum ADDRESS {
        REAL_API("https://api-sinvoice.viettel.vn:443/"),
        TEST_API("https://demo-sinvoice.viettel.vn:8443/");
        private final String address;

        ADDRESS(String address) {
            this.address = address;
        }

        String getString() {
            return address;
        }
    }

    public enum ACTION {
        CREATE_INVOICE("InvoiceAPI/InvoiceWS/createInvoice/"),
        GET_INVOICE("InvoiceAPI/InvoiceWS/createExchangeInvoiceFile");
        private final String action;

        ACTION(String action) {
            this.action = action;
        }

        String getString() {
            return action;
        }
    }

    public enum CONTENT_TYPE {
        JSON("application/json"),
        URL_ENCODED(" application/x-www-form-urlencoded ");
        private final String contentType;

        CONTENT_TYPE(String contentType) {
            this.contentType = contentType;
        }

        String getString() {
            return contentType;
        }
    }

    public Connection(ADDRESS address, ACTION action, CONTENT_TYPE contentType, String encodedVerification, String taxCode) {
        this.encodedVerification = encodedVerification;
        this.taxCode = taxCode;
        this.service = address.getString() + action.getString();
        this.contentType = contentType.getString();
    }

    public String send(String data) throws IOException {
        //might cause error 500 due to no IP Address v4 is recorded
        HttpURLConnection connection = (HttpURLConnection) new URL(service + taxCode).openConnection();
        connection.setRequestProperty("Authorization", "Basic " + encodedVerification);
        connection.setRequestProperty("Content-Type", contentType);
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setUseCaches(false);
        OutputStream output = connection.getOutputStream();
        output.write(data.getBytes());
        output.flush();
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        LinkedList<String> lines = new LinkedList<>();
        for (String line = br.readLine(); line != null; line = br.readLine()) {
            lines.add(line);
        }
        return lines.toString();
    }
}

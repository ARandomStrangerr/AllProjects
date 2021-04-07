package viettel_electronic_invoice_webservice;

import bin.file.TextFile;
import javafx.application.Platform;
import user_interface.WaitWindow;
import viettel_electronic_invoice_webservice.connection.Connection;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.*;

public final class GetPdfBill implements Runnable {
    private final String encodedVerification, taxCode, exchangeUser, folderPath, filePath;
    private final WaitWindow waitWindow;

    public GetPdfBill(WaitWindow waitWindow, String username, String password, String exchangeUser, String folderPath, String filePath) {
        this.waitWindow = waitWindow;
        this.encodedVerification = Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
        this.taxCode = username;
        this.exchangeUser = exchangeUser;
        this.folderPath = folderPath;
        this.filePath = filePath;
        Thread thread = new Thread(this, "Get PDF Bill");
        thread.start();
    }

    @Override
    public void run() {
        int startingIndex = filePath.length() - 18,
                endingIndex = filePath.length() - 4;
        String createdDate = filePath.substring(startingIndex, endingIndex);

        TextFile textFile = TextFile.getInstance();
        List<String> billList;
        try {
            billList = textFile.read(filePath);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Connection connection = new Connection(Connection.ADDRESS.REAL_API, Connection.ACTION.GET_INVOICE, Connection.CONTENT_TYPE.URL_ENCODED, encodedVerification, "");
        LinkedHashMap<String, String> parameter = new LinkedHashMap<>();
        parameter.put("supplierTaxCode", taxCode);
        parameter.put("strIssueDate", createdDate);
        parameter.put("exchangeUser", exchangeUser);

        for (String bill : billList) {
            try {
                parameter.put("invoiceNo", bill);
            } catch (NoSuchElementException e) {
                continue;
            }
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> entry : parameter.entrySet()) {
                if (sb.length() != 0) sb.append("&");
                sb.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue()));
            }
            String body = sb.toString();
//            System.out.println(body);
            String feedback;
            try {
                feedback = connection.send(body);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
//            System.out.println(feedback);
            feedback = feedback.substring(feedback.indexOf("fileToBytes") + 14, feedback.indexOf("paymentStatus") - 3);
            try {
                OutputStream out = new FileOutputStream(folderPath + URLEncoder.encode(parameter.get("invoiceNo")) + ".pdf");
                out.write(Base64.getDecoder().decode(feedback.getBytes()));
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Platform.runLater(waitWindow::close);
    }
}
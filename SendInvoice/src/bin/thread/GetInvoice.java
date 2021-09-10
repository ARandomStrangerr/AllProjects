package bin.thread;

import file_operation.TextFile;
import node.JsonAnalyzer;
import node.NodeCurlyBrace;
import node.NodeSingleValue;
import user_interface.element.PaneLogin;
import viettel_invoice_api.connection.Connection;
import viettel_invoice_api.old_api.retreat_invoice.NodeRetreatInvoice;

import java.io.*;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.List;

public class GetInvoice implements Runnable {
    private final String issueDate,
            taxCode,
            user,
            textPath,
            folderPath;

    public GetInvoice(String issueDate, String taxCode, String user, String textPath, String folderPath) {
        this.issueDate = issueDate;
        this.taxCode = taxCode;
        this.user = user;
        this.textPath = textPath;
        this.folderPath = folderPath;
    }

    public void run() {
        NodeRetreatInvoice nodeRetreatInvoice = new NodeRetreatInvoice();
        nodeRetreatInvoice.setExchangeUser(user);
        nodeRetreatInvoice.setIssueDate(issueDate);
        nodeRetreatInvoice.setSupplierTaxCode(taxCode);
        String username = PaneLogin.getInstance().getUsername(),
                password = PaneLogin.getInstance().getPassword(),
                strToEncode = username + ":" + password,
                encodedVerification = Base64.getEncoder().encodeToString(strToEncode.getBytes());
        Connection connection = new Connection(Connection.ADDRESS.REAL_API,
                Connection.ACTION.GET_INVOICE,
                Connection.CONTENT_TYPE.JSON,
                encodedVerification,
                taxCode);
        List<String> invoices;
        try {
            invoices = TextFile.getInstance().read(textPath);
        } catch (IOException e) {
            return;
        }
        char separator = System.getProperty("os.name").startsWith("Windows") ? (char) 92 : (char) 47;
        JsonAnalyzer analyzer = new JsonAnalyzer();
        for (String invoice : invoices) {
            nodeRetreatInvoice.setInvoiceNo(invoice);
            String returnValue;
            try {
                returnValue = connection.send(nodeRetreatInvoice.getJson());
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
            NodeCurlyBrace root = (NodeCurlyBrace) analyzer.getRoot(returnValue);
            NodeSingleValue item = (NodeSingleValue) root.get("fileToBytes");
            String encodedFile = (String) item.getValue();
            try {
                OutputStream outputStream = new FileOutputStream(folderPath + separator + URLEncoder.encode(invoice) + ".pdf");
                outputStream.write(Base64.getDecoder().decode(encodedFile.getBytes()));
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

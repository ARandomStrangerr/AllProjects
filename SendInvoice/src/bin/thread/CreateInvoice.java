package bin.thread;

import bin.command.OpenWaitPane;
import file_operation.ExcelFile;
import file_operation.TextFile;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import node.JsonAnalyzer;
import node.NodeCurlyBrace;
import node.NodeSingleValue;
import user_interface.PaneCreateInvoiceFromExcel;
import user_interface.PaneWait;
import user_interface.element.PaneLogin;
import user_interface.element.PaneSellerInfo;
import user_interface.table_content.Invoice;
import user_interface.table_content.Item;
import user_interface.table_content.OrdinaryItem;
import viettel_invoice_api.connection.Connection;
import viettel_invoice_api.old_api.create_invoice.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

public final class CreateInvoice implements Runnable {
    private final String excelPath,
            textPath;

    public CreateInvoice(String excelPath,
                         String textPath) {
        this.excelPath = excelPath;
        this.textPath = textPath;
    }

    @Override
    public void run() {
        Platform.runLater(() -> {
            new OpenWaitPane().execute();
            PaneWait.getInstance().setDisplayMsg("Vui lòng đợi");
        });
        //create nodes
        NodeGeneralInvoiceInfo generalInvoiceInfo = new NodeGeneralInvoiceInfo();
        NodeSeller seller = new NodeSeller();
        NodeBuyer buyer = new NodeBuyer();
        NodePayment payment = new NodePayment();
        NodeItemInfo itemInfo = new NodeItemInfo();
        NodeTaxBreakdowns taxBreakdowns = new NodeTaxBreakdowns();
        NodeSummarizeInfo summarizeInfo = new NodeSummarizeInfo();
        NodeInvoice nodeInvoice = new NodeInvoice(generalInvoiceInfo,
                seller,
                buyer,
                payment,
                itemInfo,
                taxBreakdowns,
                summarizeInfo);
        //preset info
        seller.setSellerInfo();
        taxBreakdowns.setTaxBreakdowns();
        summarizeInfo.setSummarizeInfo();
        //get invoices list table
        ObservableList<Invoice> invoices = PaneCreateInvoiceFromExcel.getInstance().getInvoiceTableItems();
        //get map for mails
        TreeMap<String, String> treeMap = ReadMailFromExcel.getInstance().getMailMap();
        //set data to write to excel and text file
        List<List<String>> mailAndInvoiceList = new LinkedList<>();
        List<String> invoiceList = new LinkedList<>();
        //json analyzer
        JsonAnalyzer jsonAnalyzer = new JsonAnalyzer();
        //connection class to send the invoices
        String strToEncode = PaneLogin.getInstance().getUsername()
                + ":"
                + PaneLogin.getInstance().getPassword(),
                encodedVerification = Base64.getEncoder().encodeToString(strToEncode.getBytes());
        Connection connection = new Connection(Connection.ADDRESS.REAL_API,
                Connection.ACTION.CREATE_INVOICE,
                Connection.CONTENT_TYPE.JSON,
                encodedVerification,
                PaneSellerInfo.getInstance().getTaxCode());
        //fill in invoice info
        for (Invoice invoice : invoices) {
            generalInvoiceInfo.setNewInvoice(invoice.getInvoiceType(),
                    invoice.getTemplateCode(),
                    invoice.getInvoiceSeries(),
                    "VND",
                    true,
                    true);
            buyer.setBuyerInfo(invoice.getPersonName(),
                    invoice.getPersonCode(),
                    invoice.getCollectiveName(),
                    invoice.getCollectiveTaxCode(),
                    invoice.getAddress());
            payment.setPayments(invoice.getPaymentMethod());
            itemInfo.reset();
            for (Item item : invoice.getItems()) {
                if (item instanceof OrdinaryItem) {
                    OrdinaryItem temp = (OrdinaryItem) item;
                    itemInfo.addItem(temp.getName(),
                            temp.getPrice(),
                            temp.getQuantity(),
                            temp.getTotalAmount(),
                            temp.getTaxAmount());
                }
                //send data
                String returnValue;
                try {
                    returnValue = connection.send(nodeInvoice.getJson());
                } catch (IOException e) {
                    e.printStackTrace();
                    continue;
                }
                //extract invoice number
                NodeCurlyBrace rootNode = (NodeCurlyBrace) (jsonAnalyzer.getRoot(returnValue));
                NodeSingleValue invoiceNumberNode = (NodeSingleValue) rootNode.get("invoiceNo");
                String invoiceNo = (String) invoiceNumberNode.getValue();
                //add to excel file to mail - attach file
                String mail = treeMap.get(invoice.getPersonCode());
                List<String> individual = new LinkedList<>();
                individual.add(mail);
                individual.add(URLEncoder.encode(invoiceNo) + "pdf");
                mailAndInvoiceList.add(individual);
                //add to text file to get pdf
                invoiceList.add(invoiceNo);
            }
        }
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd000000");
        String fileName = dateFormat.format(date);
        char separator = System.getProperty("os.name").startsWith("Windows") ? (char) 92 : (char) 47;
        //write mail - attachment file to excel file
        try {
            ExcelFile.getInstance().write(excelPath + separator + fileName + ".xlsx", mailAndInvoiceList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //write invoice to text file
        try {
            TextFile.getInstance().write(textPath + separator + fileName + ".txt", false, invoiceList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Platform.runLater(() -> PaneWait.getInstance().getWindow().closeCurrentStage());
    }
}
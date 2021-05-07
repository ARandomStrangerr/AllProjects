package viettel_electronic_invoice_webservice;

import bin.file.ExcelFile;
import bin.file.TextFile;
import javafx.application.Platform;
import node.*;
import user_interface.WaitWindow;
import viettel_electronic_invoice_webservice.connection.Connection;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class UploadBillUpgrade implements Runnable {
    private final String username, password, excelFilePath, invoiceType, templateCode;
    private final LinkedList<String> sentListData = new LinkedList<>(),
            sendRecord = new LinkedList<>(),
            missingField = new LinkedList<>();
    private final WaitWindow waitWindow;

    public UploadBillUpgrade(String username, String password, String excelFilePath, String invoiceType, String templateCode, WaitWindow waitWindow) {
        this.username = username;
        this.password = password;
        this.excelFilePath = excelFilePath;
        this.invoiceType = invoiceType;
        this.templateCode = templateCode;
        this.waitWindow = waitWindow;
        new Thread(this).start();
    }

    @Override
    public void run() {
        try {
            send();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Platform.runLater(waitWindow::close);
    }

    public void send() throws IOException {
        //step 1 : get data from the indicated excel file
        List<String> dataFromExcelFile = ExcelFile.getInstance().read(excelFilePath);
        //step 2 : create json formation for those data
        List<NodeInterface> rootNodes = new LinkedList<>(); //collection to hold those roots
        for (String entry : dataFromExcelFile) {
            try {
                rootNodes.add(constructJson(entry));
            } catch (IndexOutOfBoundsException e) {
                System.err.println(entry);
            }
        }
        //step 3 : send the created json format
        String encodedVerification = Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
        Connection connection = new Connection(Connection.ADDRESS.REAL_API, Connection.ACTION.CREATE_INVOICE, Connection.CONTENT_TYPE.JSON, encodedVerification, username);
        //send and capture data feedback from server
        String feedback = null;
        int index = 1;
        for (NodeInterface root : rootNodes) {
            try {   //try to send data
                //recording the feedback from server . if server return an error of missing field , this will be recorded without repeating itself .
                //normally this is already eliminated from the previous step due to missing data while attempt to create json format
                feedback = connection.send(root.getData());
            } catch (IOException e) {   //error code 400 or 500 from server ( should immediate terminate when receive error code 500 ) todo: terminate loop when receive error code 500
                System.out.println(index);
            } finally {
                //regardless of the outcome , always record the feedback from the server
                sendRecord.add(feedback);
            }
            index++;
            //capture the invoice number
            int begin = feedback.indexOf("invoiceNo") + 12, end = begin + 13;
            sentListData.add(feedback.substring(begin, end));
            TextFile.getInstance().append("log.txt",root.getData());
        }
        //step 4 : write log to file
        writeDataToFiles();
    }

    private NodeInterface constructJson(String line) throws IndexOutOfBoundsException {
        String[] cells = line.split(":"),
                itemInfoArr = cells[7].split(";");
        //generalInvoiceInfo
        NodeSingleValue invoiceType = new NodeSingleValue("invoiceType", this.invoiceType), //declaration of values
                templateCode = new NodeSingleValue("templateCode", this.invoiceType + "0/" + this.templateCode),
                currencyCode = new NodeSingleValue("currencyCode", "VND"),
                adjustmentType = new NodeSingleValue("adjustmentType", 1),
                paymentStatus = new NodeSingleValue("paymentStatus", true),
                cusGetInvoiceRight = new NodeSingleValue("cusGetInvoiceRight", true);
        NodeNamedCurlyBrace generalInvoiceInfo = new NodeNamedCurlyBrace("generalInvoiceInfo");     //declaration of super field
        generalInvoiceInfo.addAll(invoiceType, templateCode, currencyCode, adjustmentType, paymentStatus, cusGetInvoiceRight);  //add elements nodes into super field
        //buyerInfo
        NodeSingleValue buyerName = new NodeSingleValue("buyerName", cells[0]),
                buyerAddress = new NodeSingleValue("buyerAddressLine", cells[1]);
        NodeNamedCurlyBrace buyerInfo = new NodeNamedCurlyBrace("buyerInfo");
        buyerInfo.addAll(buyerName, buyerAddress);
        //payments
        NodeSingleValue paymentMethodName = new NodeSingleValue("paymentMethodName", "CK/TM");
        NodeCurlyBrace paymentMethodNameWrapper = new NodeCurlyBrace();
        NodeNamedSquareBrace payments = new NodeNamedSquareBrace("payments");
        paymentMethodNameWrapper.add(paymentMethodName);
        payments.add(paymentMethodNameWrapper);
        //itemInfo
        NodeSingleValue lineNumber = new NodeSingleValue("lineNumber", 1),
                itemName = new NodeSingleValue("itemName", itemInfoArr[0]),
                quantity = new NodeSingleValue("quantity", itemInfoArr[2]),
                itemTotalAmountWithoutTax = new NodeSingleValue("itemTotalAmountWithoutTax", Math.abs(Long.parseLong(itemInfoArr[3]))),
                taxAmount = new NodeSingleValue("taxAmount", Math.abs(Long.parseLong(itemInfoArr[4])));
        NodeCurlyBrace itemWrapper = new NodeCurlyBrace();
        itemWrapper.addAll(lineNumber, itemName, quantity, itemTotalAmountWithoutTax, taxAmount);
        if (Long.parseLong(itemInfoArr[3]) < 0) {
            NodeSingleValue isIncreaseItem = new NodeSingleValue("isIncreaseItem", false);
            itemWrapper.add(isIncreaseItem);
        } else {
            NodeSingleValue unitPrice = new NodeSingleValue("unitPrice", itemInfoArr[1]);
            itemWrapper.add(unitPrice);
        }
        NodeNamedSquareBrace itemInfo = new NodeNamedSquareBrace("itemInfo");
        itemInfo.add(itemWrapper);
        //summarizationInfo
        NodeSingleValue sumOfTotalLineAmountWithoutTax = new NodeSingleValue("sumOfTotalLineAmountWithoutTax", Math.abs(Long.parseLong(cells[2]))),
                totalAmountWithoutTax = new NodeSingleValue("totalAmountWithoutTax", Math.abs(Long.parseLong(cells[3]))),
                totalTaxAmount = new NodeSingleValue("totalTaxAmount", Math.abs(Long.parseLong(cells[4]))),
                totalAmountWithTax = new NodeSingleValue("totalAmountWithTax", Math.abs(Long.parseLong(cells[5]))),
                discountAmount = new NodeSingleValue("discountAmount", Math.abs(Long.parseLong(cells[6])));
        NodeNamedCurlyBrace summarizationInfo = new NodeNamedCurlyBrace("summarizeInfo");
        summarizationInfo.addAll(sumOfTotalLineAmountWithoutTax, totalAmountWithoutTax, totalTaxAmount, totalAmountWithTax, discountAmount);
        if (Long.parseLong(cells[5]) < 0) {
            NodeSingleValue isTotalAmountPos = new NodeSingleValue("isTotalAmountPos", false),
                    isTotalTaxAmountPos = new NodeSingleValue("isTotalTaxAmountPos", false);
            summarizationInfo.addAll(isTotalAmountPos, isTotalTaxAmountPos);
        }
        NodeCurlyBrace root = new NodeCurlyBrace();
        root.addAll(generalInvoiceInfo, buyerInfo, payments, itemInfo, summarizationInfo);
        return root;
    }

    private void writeDataToFiles() {
        //get today date and create pattern
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd000000");
        //get file operation instance
        TextFile textFile = TextFile.getInstance();
        try {
            //append (create and write if the file does not exist) into today created invoice
            textFile.append(dateFormat.format(date) + ".txt", sentListData);
            //append (create and write if the file does not exist) into today feedback from server of attempted invoice to create
            textFile.append(dateFormat.format(date) + "response.txt", sendRecord);
            //append (create and write if the file does not exist) into today field of the given excel which is missing file
            textFile.append(dateFormat.format(date) + "MissingField.txt", missingField);
        } catch (IOException e) {   //an exception is thrown when cannot write / append into file
            e.printStackTrace();
        }
    }
}
package viettel_electronic_invoice_webservice;

import bin.file.ExcelFile;
import bin.file.TextFile;
import javafx.application.Platform;
import javafx.stage.Stage;
import node.*;
import user_interface.WaitWindow;
import viettel_electronic_invoice_webservice.connection.Connection;
import viettel_electronic_invoice_webservice.connection.ConnectionNew;

import java.io.IOException;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;

public class UploadBillNewAPI implements Runnable {
    private final String username, password, excelPath, invoiceType, invoiceSeries, templateCode;

    public UploadBillNewAPI(String username, String password, String excelPath, String invoiceType, String invoiceSeries, String templateCode) {
        this.username = username;
        this.password = password;
        this.excelPath = excelPath;
        this.invoiceType = invoiceType;
        this.invoiceSeries = invoiceSeries;
        this.templateCode = templateCode;
    }

    @Override
    public void run() {
        WaitWindow waitWindow = new WaitWindow(new Stage());
        Platform.runLater(waitWindow::show);
        //step 1: create json list from excel file
        List<NodeCurlyBrace> nodes;
        try {
            nodes = getRoots();
        } catch (IOException e) {
            return;
        }
        //step 2: get token
//        ConnectionNew con = new ConnectionNew();
//        try {
//            con.getToken(username, password);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return;
//        }
        String passwordToEncode = username + ":" + password,
                encodedVerification = Base64.getEncoder().encodeToString(passwordToEncode.getBytes());
        Connection con = new Connection(Connection.ADDRESS.REAL_API,
                Connection.ACTION.CREATE_INVOICE,
                Connection.CONTENT_TYPE.JSON,
                encodedVerification,
                username);
        //step 3: sends those data
        StringBuilder sb = new StringBuilder();
        int counter = 0;
        for (NodeCurlyBrace root : nodes) {
            System.out.println(root.getData());
            try {
                if (sb.length() != 0) sb.append("\n");
                String returnData = con.send(root.getData());
                System.out.println(returnData);
                sb.append(returnData);
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("SEND ERROR");
            }
            String msg = "( " + ++counter + " / " + nodes.size() + " )";
            Platform.runLater(() -> waitWindow.setDisplayMessage(msg));
        }
        //step 4: write the record
        try {
            TextFile.getInstance().writeNew("log.txt", sb.toString());
        } catch (IOException e) {

        }
        Platform.runLater(() -> waitWindow.setDisplayMessage("Hoàn Thành"));
    }

    public List<NodeCurlyBrace> getRoots() throws IOException {
        List<NodeCurlyBrace> entries = new LinkedList<>();
        for (List<String> row : ExcelFile.getInstance().readList(excelPath)) entries.add(getRoot(row));
        return entries;
    }

    private NodeCurlyBrace getRoot(List<String> row) {
        NodeCurlyBrace nodeRoot = new NodeCurlyBrace(),
                paymentMethodWrapper = new NodeCurlyBrace();
        NodeNamedCurlyBrace generalInvoiceInfo = new NodeNamedCurlyBrace("generalInvoiceInfo"),
                buyerInfo = new NodeNamedCurlyBrace("buyerInfo"),
                sellerInfo = new NodeNamedCurlyBrace("sellerInfo"),
                summarizeInfo = new NodeNamedCurlyBrace("summarizeInfo");
        NodeNamedSquareBrace payments = new NodeNamedSquareBrace("payments"),
                itemInfo = new NodeNamedSquareBrace("itemInfo"),
                metadata = new NodeNamedSquareBrace("metadata"),
                meterReading = new NodeNamedSquareBrace("meterReading"),
                taxBreakdowns = new NodeNamedSquareBrace("taxBreakdowns");
        NodeSingleValue invoiceType = new NodeSingleValue("invoiceType", this.invoiceType),
                templateCode = new NodeSingleValue("templateCode", this.invoiceType + "0/" + this.templateCode),
                invoiceSeries = new NodeSingleValue("invoiceSeries", this.invoiceSeries),
                currencyCode = new NodeSingleValue("currencyCode", "VND"),
                adjustmentType = new NodeSingleValue("adjustmentType", 1),
                paymentStatus = new NodeSingleValue("paymentStatus", true),
                cusGetInvoiceRight = new NodeSingleValue("cusGetInvoiceRight", true),
                buyerName = new NodeSingleValue("buyerName", row.get(0)),
                buyerAddress = new NodeSingleValue("buyerAddressLine", row.get(1)),
                paymentMethodName = new NodeSingleValue("paymentMethodName", "CK/TM"),
                sumOfTotalLineAmountWithoutTax = new NodeSingleValue("sumOfTotalLineAmountWithoutTax", Math.abs(Long.parseLong(row.get(2)))),
                totalAmountWithoutTax = new NodeSingleValue("totalAmountWithoutTax", Math.abs(Long.parseLong(row.get(3)))),
                totalTaxAmount = new NodeSingleValue("totalTaxAmount", Math.abs(Long.parseLong(row.get(4)))),
                totalAmountWithTax = new NodeSingleValue("totalAmountWithTax", Math.abs(Long.parseLong(row.get(5)))),
                discountAmount = new NodeSingleValue("discountAmount", Math.abs(Long.parseLong(row.get(6))));
        if (Long.parseLong(row.get(3)) < 0) {
            NodeSingleValue isTotalAmtWithoutTaxPos = new NodeSingleValue("isTotalAmtWithoutTaxPos", false);
            summarizeInfo.add(isTotalAmtWithoutTaxPos);
        }
        if (Long.parseLong(row.get(5)) < 0){
            NodeSingleValue isTotalAmountPos = new NodeSingleValue("isTotalAmountPos", false);
            summarizeInfo.add(isTotalAmountPos);
        }
        for (int index = 7, lineNum = 1; index < row.size(); index++, lineNum++) {
            String[] strArr = row.get(index).split(";");
            NodeSingleValue lineNumber = new NodeSingleValue("lineNumber", lineNum),
                    itemName = new NodeSingleValue("itemName", strArr[0]),
                    quantity = new NodeSingleValue("quantity", strArr[2]),
                    itemTotalAmountWithoutTax = new NodeSingleValue("itemTotalAmountWithoutTax", Math.abs(Long.parseLong(strArr[3]))),
                    taxAmount = new NodeSingleValue("taxAmount", Math.abs(Long.parseLong(strArr[4])));
            NodeCurlyBrace wrapper = new NodeCurlyBrace();
            wrapper.addAll(lineNumber, itemName, quantity, itemTotalAmountWithoutTax, taxAmount);
            if (Long.parseLong(strArr[3]) < 0) {
                NodeSingleValue isIncreaseItem = new NodeSingleValue("isIncreaseItem", false);
                wrapper.add(isIncreaseItem);
            } else {
                NodeSingleValue unitPrice = new NodeSingleValue("unitPrice", strArr[1]);
                wrapper.add(unitPrice);
            }
            itemInfo.add(wrapper);
        }
        generalInvoiceInfo.addAll(invoiceType, templateCode, invoiceSeries, currencyCode, adjustmentType, paymentStatus, cusGetInvoiceRight);
        buyerInfo.addAll(buyerName, buyerAddress);
        summarizeInfo.addAll(sumOfTotalLineAmountWithoutTax, totalAmountWithoutTax, totalTaxAmount, totalAmountWithTax, discountAmount);
        paymentMethodWrapper.add(paymentMethodName);
        payments.add(paymentMethodWrapper);
        nodeRoot.addAll(generalInvoiceInfo, buyerInfo, sellerInfo, payments, itemInfo, metadata, meterReading, summarizeInfo, taxBreakdowns);
        return nodeRoot;
    }
}

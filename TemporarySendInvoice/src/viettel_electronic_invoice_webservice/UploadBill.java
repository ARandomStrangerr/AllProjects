package viettel_electronic_invoice_webservice;

import bin.file.ExcelFile;
import bin.file.FileOperation;
import bin.file.TextFile;
import javafx.application.Platform;
import user_interface.WaitWindow;
import viettel_electronic_invoice_webservice.connection.Connection;
import viettel_electronic_invoice_webservice.data.Json;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public final class UploadBill implements Runnable {
    private final String username, password, excelFilePath, invoiceType, templateCode;
    private final WaitWindow waitWindow;
    private final LinkedList<String> sentListData = new LinkedList<>(),
            sendRecord = new LinkedList<>(),
            missingField = new LinkedList<>();

    public UploadBill(String username,
                      String password,
                      String excelFilePath,
                      String invoiceType,
                      String templateCode,
                      WaitWindow waitWindow) {
        this.username = username;
        this.password = password;
        this.excelFilePath = excelFilePath;
        this.invoiceType = invoiceType;
        this.templateCode = templateCode;
        this.waitWindow = waitWindow;
        Thread thisThread = new Thread(this, "Viettel Electronic Bill API");
        thisThread.start();
    }

    @Override
    public void run() {
        //read data from excel file
        FileOperation fileOperation = ExcelFile.getInstance();
        List<String> data;
        try {
            data = fileOperation.read(excelFilePath);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        //create json strings
        List<String> jsonData = createJsonList(data);
        //send the created json
        sendJson(jsonData);
        //write down necessary information
        writeDataToFiles();
        //close the wait pane which is opened previously
        Platform.runLater(waitWindow::close);
    }

    private List<String> createJsonList(List<String> data) {
        missingField.add(excelFilePath);
        LinkedList<String> jsonData = new LinkedList<>();
        Json json = new Json();
        //initiate constant data
        Map<String, String> generalInvoiceInfo = new LinkedHashMap<>(),
                payment = new LinkedHashMap<>();
        generalInvoiceInfo.put("invoiceType", invoiceType);
        generalInvoiceInfo.put("templateCode", invoiceType + "0/" + templateCode);
        generalInvoiceInfo.put("currencyCode", "VND");
        generalInvoiceInfo.put("adjustmentType", "1");
        generalInvoiceInfo.put("paymentStatus", "true");
        generalInvoiceInfo.put("cusGetInvoiceRight", "true");

        payment.put("paymentMethodName", "CK/TM");

        //index of the line;
        int excelLineIndex = 1;
        //iterate through data in given file , create Json string and append into list
        outerLoop:
        for (String line : data) {
            //set everything within to blank
            json.reset();
            //put in constant data
            json.addGroup("generalInvoiceInfo", generalInvoiceInfo);
            json.addGroups("payment", payment);
            //analyze dynamic data
            String[] lineArr = line.split(":");
            Map<String, String> buyerInfo = new LinkedHashMap<>(),
                    summarizationInfo = new LinkedHashMap<>();
            try {   //an exception is thrown when index out of bound
                buyerInfo.put("buyerName", lineArr[0]);
                buyerInfo.put("buyerAddressLine", lineArr[1]);
                summarizationInfo.put("sumOfTotalLineAmountWithoutTax", lineArr[2]);
                summarizationInfo.put("totalAmountWithoutTax", lineArr[3]);
                summarizationInfo.put("totalTaxAmount", lineArr[4]);
                summarizationInfo.put("totalAmountWithTax", lineArr[5]);
                summarizationInfo.put("discountAmount", lineArr[6]);
            } catch (IndexOutOfBoundsException e) {
                missingField.add("line " + excelLineIndex + line);
                continue;
            }
            //add created groups into json file
            json.addGroup("buyerInfo", buyerInfo);
            json.addGroup("summarizeInfo", summarizationInfo);
            //analyze items data
            List<Map<String, String>> itemList = new LinkedList<>();
            for (int i = 7, index = 1; i < lineArr.length; i++, index++) {
                Map<String, String> items = new LinkedHashMap<>();
                String[] itemArr;
                try {   //an exception is thrown when index out of bound
                    itemArr = lineArr[i].split(";");
                    items.put("lineNumber", String.valueOf(index));
                    items.put("itemName", itemArr[0]);
                    items.put("unitPrice", itemArr[1]);
                    items.put("quantity", itemArr[2]);
                    items.put("itemTotalAmountWithoutTax", itemArr[3]);
                    items.put("taxAmount", itemArr[4]);
                } catch (IndexOutOfBoundsException e) { //ab exception is thrown when index out of bound
                    missingField.add("line " + excelLineIndex + line);
                    continue outerLoop;
                }
                itemList.add(items);
            }
            json.addGroups("itemInfo", itemList);
            //add the complete json format into json lines
            jsonData.add(json.getJson());
            //increase the index
            excelLineIndex++;
        }
        return jsonData;
    }

    private void sendJson(List<String> data) {
        String encodedVerification = Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
        Connection connection = new Connection(Connection.ADDRESS.REAL_API, Connection.ACTION.CREATE_INVOICE, Connection.CONTENT_TYPE.JSON, encodedVerification, username);

        //send and capture data feedback from server
        String feedback = null;
        for (String json : data) {
            System.out.println(json);
            try {   //try to send data
                //recording the feedback from server . if server return an error of missing field , this will be recorded without repeating itself .
                //normally this is already eliminated from the previous step due to missing data while attempt to create json format
                feedback = connection.send(json);
            } catch (IOException e) {   //error code 400 or 500 from server ( should immediate terminate when receive error code 500 ) todo: terminate loop when receive error code 500
                //recording the error msg from the server
                feedback = e.getMessage();
                //name json at the end of the list to try to send it again .
                data.add(json);
                //skip the process of capturing the invoice number
                continue;
            } finally {
                //regardless of the outcome , always record the feedback from the server
                sendRecord.add(feedback);
            }
            //capture the invoice number
            int begin = feedback.indexOf("invoiceNo") + 12, end = begin + 13;
            sentListData.add(feedback.substring(begin, end));
        }
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
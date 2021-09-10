package bin.thread;

import file_operation.ExcelFile;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import user_interface.PaneCreateInvoiceFromExcel;
import user_interface.table_content.Invoice;
import user_interface.table_content.Item;
import user_interface.table_content.OrdinaryItem;

import java.io.IOException;
import java.util.List;

public final class ReadInvoiceFromExcel extends ReadFileRunnable {
    @Override
    public void run() {
        List<List<String>> data;
        try {
            data = ExcelFile.getInstance().read(fileChooser.getPath());
        } catch (IOException e) {
            System.err.println("CANNOT READ FILE");
            e.printStackTrace();
            return;
        } catch (NullPointerException e) {
            System.err.println("NO FILE HAS BEEN SELECTED");
            e.printStackTrace();
            return;
        }
        List<Invoice> tableData = PaneCreateInvoiceFromExcel.getInstance().getInvoiceTableItems();
        for (List<String> row : data) {
            int index = 0;
            String invoiceType = null,
                    templateCode = null,
                    invoiceSeries = null,
                    personName = null,
                    personCode = null,
                    collectiveName = null,
                    collectiveTaxCode = null,
                    address = null,
                    paymentMethod = null;
            Float taxRate = null;
            ObservableList<Item> item = FXCollections.observableArrayList();
            for (String cell : row) {
                switch (index) {
                    case 0:
                        invoiceType = cell;
                        break;
                    case 1:
                        templateCode = cell;
                        break;
                    case 2:
                        invoiceSeries = cell;
                        break;
                    case 3:
                        personName = cell;
                        break;
                    case 4:
                        personCode = cell;
                        break;
                    case 5:
                        collectiveName = cell;
                        break;
                    case 6:
                        collectiveTaxCode = cell;
                        break;
                    case 7:
                        address = cell;
                        break;
                    case 8:
                        paymentMethod = cell;
                        break;
                    case 9:
                        try {
                            taxRate = Float.parseFloat(cell);
                        } catch (NullPointerException e) {
                            continue;
                        }
                        break;
                    default:
                        String[] temp = cell.split(",");
                        try {
                            item.add(Item.createItem(temp[0],
                                    Integer.parseInt(temp[1]),
                                    Long.parseLong(temp[2]),
                                    taxRate));
                        } catch (IndexOutOfBoundsException | NumberFormatException e) {
                            e.printStackTrace();
                        }
                        break;
                }
                index++;
            }
            if (invoiceType == null
                    || templateCode == null
                    || invoiceSeries == null
                    || personName == null
                    || personCode == null
                    || collectiveName == null
                    || collectiveTaxCode == null
                    || address == null
                    || paymentMethod == null
                    || taxRate == null
                    || item.size() == 0)
                throw new NullPointerException();
            tableData.add(new Invoice(invoiceType,
                    templateCode,
                    invoiceSeries,
                    personName,
                    personCode,
                    collectiveName,
                    collectiveTaxCode,
                    address,
                    paymentMethod,
                    taxRate,
                    item));
        }
    }
}
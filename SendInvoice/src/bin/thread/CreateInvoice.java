package bin.thread;

import javafx.collections.ObservableList;
import user_interface.PaneCreateInvoiceFromExcel;
import user_interface.element.PaneInvoiceInfo;
import user_interface.table_content.Invoice;
import viettel_invoice_api.old_api.create_invoice.*;

public final class CreateInvoice implements Runnable{

    @Override
    public void run() {
        ObservableList<Invoice> items = PaneCreateInvoiceFromExcel.getInstance().getInvoiceTableItems();
        NodeInvoice nodeInvoice = new NodeInvoice();
        NodeGeneralInvoiceInfo generalInvoiceInfo = new NodeGeneralInvoiceInfo();
        NodeSeller seller = new NodeSeller();
        NodeBuyer buyer = new NodeBuyer();
        NodePayment payment = new NodePayment();
        NodeItemInfo itemInfo = new NodeItemInfo();
        NodeTaxBreakdowns taxBreakdowns = new NodeTaxBreakdowns();
        NodeSummarizeInfo summarizeInfo = new NodeSummarizeInfo();
        nodeInvoice.setGeneralInvoiceInfo(generalInvoiceInfo);
        nodeInvoice.setBuyerInfo(buyer);
        nodeInvoice.setSellerInfo(seller);
        nodeInvoice.setSetPayments(payment);
        nodeInvoice.setSetItemInfo(itemInfo);
        nodeInvoice.setTaxBreakDown(taxBreakdowns);
        nodeInvoice.setSummarizeInfo(summarizeInfo);
        generalInvoiceInfo.setNewInvoice(PaneInvoiceInfo.getInstance().getInvoiceType(),
                PaneInvoiceInfo.getInstance().getTemplateCode(),
                PaneInvoiceInfo.getInstance().getInvoiceSeries(),
                "VND",
                true,
                true);
        for(Invoice item : items) {
            buyer.setBuyerInfo(item.getPersonName(),
                    item.getPersonCode(),
                    item.getCollectiveName(),
                    item.getCollectiveTaxCode(),
                    item.getAddress());
            payment.setPayments(item.getPaymentMethod());

        }
    }
}

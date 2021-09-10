package viettel_invoice_api.old_api.retreat_invoice;

import node.NodeCurlyBrace;
import node.NodeSingleValue;

public class NodeRetreatInvoice {
    private final NodeCurlyBrace root;
    private final NodeSingleValue supplierTaxCode,
            exchangeUser,
            strIssueDate,
            invoiceNo;

    public NodeRetreatInvoice() {
        root = new NodeCurlyBrace();
        supplierTaxCode = new NodeSingleValue("supplierTaxCode", "");
        exchangeUser = new NodeSingleValue("exchangeUser", "");
        strIssueDate = new NodeSingleValue("strIssueDate", "");
        invoiceNo = new NodeSingleValue("invoiceNo", "");
        root.addAll(supplierTaxCode,
                exchangeUser,
                strIssueDate,
                invoiceNo);
    }

    public void setSupplierTaxCode(String value) {
        supplierTaxCode.setValue(value);
    }

    public void setExchangeUser(String value) {
        exchangeUser.setValue(value);
    }

    public void setIssueDate(String value) {
        exchangeUser.setValue(value);
    }

    public void setInvoiceNo(String value) {
        invoiceNo.setValue(value);
    }

    public String getJson() {
        return root.getData();
    }

}

package viettel_invoice_api.old_api.create_invoice;

import node.NodeNamedCurlyBrace;
import node.NodeSingleValue;

public final class NodeGeneralInvoiceInfo {
    private NodeNamedCurlyBrace root;

    public void setNewInvoice(String invoiceType,
                              String templateCode,
                              String invoiceSeries,
                              String currencyCode,
                              boolean paymentStatus,
                              boolean cusGetInvoiceRight)
            throws NullPointerException,
            IllegalArgumentException {
        if (invoiceType == null || invoiceType.trim().isEmpty()
                || templateCode == null || templateCode.trim().isEmpty()
                || invoiceSeries == null || invoiceSeries.trim().isEmpty()
                || currencyCode == null || currencyCode.trim().isEmpty())
            throw new NullPointerException();
        root = new NodeNamedCurlyBrace("generalInvoiceInfo");
        NodeSingleValue nodeInvoiceType = new NodeSingleValue("invoiceType", invoiceType),
                nodeTemplateCode = new NodeSingleValue("templateCode", templateCode),
                nodeInvoiceSeries = new NodeSingleValue("invoiceSeries", invoiceSeries),
                nodeCurrencyCode = new NodeSingleValue("currencyCode", currencyCode),
                nodeAdjustmentType = new NodeSingleValue("adjustmentType ", 1),
                nodePaymentStatus = new NodeSingleValue("paymentStatus", paymentStatus),
                nodeCusGetInvoiceRight = new NodeSingleValue("cusGetInvoiceRight", cusGetInvoiceRight);
        root.addAll(nodeInvoiceType,
                nodeTemplateCode,
                nodeInvoiceSeries,
                nodeAdjustmentType,
                nodeCurrencyCode,
                nodePaymentStatus,
                nodeCusGetInvoiceRight);
    }

    public NodeNamedCurlyBrace getRoot() {
        return root;
    }
}

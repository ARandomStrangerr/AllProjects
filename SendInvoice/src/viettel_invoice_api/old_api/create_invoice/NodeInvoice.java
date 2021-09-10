package viettel_invoice_api.old_api.create_invoice;

import node.NodeCurlyBrace;

public final class NodeInvoice {
    private final NodeGeneralInvoiceInfo nodeInfo;
    private final NodeSeller nodeSeller;
    private final NodeBuyer nodeBuyer;
    private final NodePayment nodePayment;
    private final NodeItemInfo nodeItem;
    private final NodeTaxBreakdowns nodeTax;
    private final NodeSummarizeInfo nodeSummarize;

    public NodeInvoice(NodeGeneralInvoiceInfo nodeInfo,
                       NodeSeller nodeSeller,
                       NodeBuyer nodeBuyer,
                       NodePayment nodePayment,
                       NodeItemInfo nodeItem,
                       NodeTaxBreakdowns nodeTax,
                       NodeSummarizeInfo nodeSummarize) {
        this.nodeInfo = nodeInfo;
        this.nodeSeller = nodeSeller;
        this.nodeBuyer = nodeBuyer;
        this.nodePayment = nodePayment;
        this.nodeItem = nodeItem;
        this.nodeTax = nodeTax;
        this.nodeSummarize = nodeSummarize;
    }

    public String getJson() {
        NodeCurlyBrace root = new NodeCurlyBrace();
        root.add(nodeInfo.getRoot());
        root.add(nodeSeller.getRoot());
        root.add(nodeBuyer.getRoot());
        root.add(nodePayment.getRoot());
        root.add(nodeItem.getRoot());
        root.add(nodeTax.getRoot());
        root.add(nodeSummarize.getRoot());
        return root.getData();
    }
}

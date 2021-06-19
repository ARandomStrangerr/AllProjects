package viettel_invoice_api.old_api.create_invoice;

import node.NodeCurlyBrace;


public final class NodeInvoice {
    private NodeCurlyBrace root;
    private boolean setGeneralInvoiceInfo,
            setBuyerInfo,
            setSellerInfo,
            setPayments,
            setItemInfo;

    public NodeInvoice() {
    }

    public void setGeneralInvoiceInfo(NodeGeneralInvoiceInfo nodeGeneralInvoiceInfo) {
        root.add(nodeGeneralInvoiceInfo.getRoot());
        setGeneralInvoiceInfo = true;
    }

    public void setBuyerInfo(NodeBuyer nodeBuyer) {
        root.add(nodeBuyer.getRoot());
        setBuyerInfo = true;
    }

    public void setSellerInfo(NodeSeller nodeSeller) {
        root.add(nodeSeller.getRoot());
        setSellerInfo = true;
    }

    public void setSetPayments(NodePayment nodePayment) {
        root.add(nodePayment.getRoot());
        setPayments = true;
    }

    public void setSetItemInfo(NodeItemInfo nodeItemInfo) {
        root.add(nodeItemInfo.getRoot());
        setItemInfo = true;
    }

    public void setSummarizeInfo(NodeSummarizeInfo nodeSummarizeInfo){
        root.add(nodeSummarizeInfo.getRoot());
    }

    public void setTaxBreakDown(NodeTaxBreakdowns taxBreakdown){
        root.add(taxBreakdown.getRoot());
    }

    public void restart() {
        setGeneralInvoiceInfo = false;
        setBuyerInfo = false;
        setSellerInfo = false;
        setPayments = false;
        setItemInfo = false;
        root = new NodeCurlyBrace();
    }

    public NodeCurlyBrace getRoot()
            throws NullPointerException {
        if (setGeneralInvoiceInfo
                && setBuyerInfo
                && setSellerInfo
                && setPayments
                && setItemInfo) {
            return root;
        }
        throw new NullPointerException();
    }
}

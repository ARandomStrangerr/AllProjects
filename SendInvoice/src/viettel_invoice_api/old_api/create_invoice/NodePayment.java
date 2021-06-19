package viettel_invoice_api.old_api.create_invoice;

import node.NodeCurlyBrace;
import node.NodeNamedSquareBrace;
import node.NodeSingleValue;

public final class NodePayment {
    private NodeNamedSquareBrace root;
    public void setPayments(String method) throws NullPointerException {
        if (method == null || method.trim().isEmpty()) throw new NullPointerException();
        root = new NodeNamedSquareBrace("payments");
        NodeCurlyBrace nodeWrapper = new NodeCurlyBrace();
        NodeSingleValue nodeMethod = new NodeSingleValue("paymentMethodName", method);
        nodeWrapper.add(nodeMethod);
        root.add(nodeWrapper);
    }

    public NodeNamedSquareBrace getRoot() {
        return root;
    }
}
package viettel_invoice_api.old_api.create_invoice;

import node.NodeNamedCurlyBrace;

public final class NodeSummarizeInfo {
    private NodeNamedCurlyBrace root;
    public void setSummarizeInfo() throws IllegalCallerException {
        root = new NodeNamedCurlyBrace("summarizeInfo");
    }

    public NodeNamedCurlyBrace getRoot() {
        return root;
    }
}

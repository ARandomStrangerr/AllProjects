package viettel_invoice_api.old_api.create_invoice;

import node.NodeNamedSquareBrace;

public final class NodeTaxBreakdowns {
    private NodeNamedSquareBrace root;
    public void setTaxBreakdowns(){
        root = new NodeNamedSquareBrace("taxBreakdowns");
    }

    public NodeNamedSquareBrace getRoot() {
        return root;
    }
}

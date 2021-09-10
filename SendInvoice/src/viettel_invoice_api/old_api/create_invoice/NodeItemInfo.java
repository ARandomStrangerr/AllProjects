package viettel_invoice_api.old_api.create_invoice;

import node.NodeCurlyBrace;
import node.NodeNamedSquareBrace;
import node.NodeSingleValue;

public final class NodeItemInfo {
    private int lineCounter;
    private NodeNamedSquareBrace root;

    public NodeItemInfo() {
    }

    public void addItem(String name,
                        long price,
                        int quantity,
                        long totalAmountWithoutTax,
                        long taxAmount)
            throws NullPointerException,
            IllegalCallerException {
        if (name == null || name.trim().isEmpty()) throw new NullPointerException();
        lineCounter++;
        NodeSingleValue nodeLineNumber = new NodeSingleValue("lineNumber",
                lineCounter),
                nodeSelection = new NodeSingleValue("selection",
                        1),
                nodeItemName = new NodeSingleValue("itemName",
                        name),
                nodeUnitPrice = new NodeSingleValue("unitPrice",
                        price),
                nodeQuantity = new NodeSingleValue("quantity",
                        quantity),
                nodeItemTotalAmountWithoutTax = new NodeSingleValue("itemTotalAmountWithoutTax",
                        totalAmountWithoutTax),
                nodeTaxAmount = new NodeSingleValue("taxAmount",
                        taxAmount);
        NodeCurlyBrace wrapper = new NodeCurlyBrace();
        wrapper.addAll(nodeLineNumber,
                nodeSelection,
                nodeItemName,
                nodeUnitPrice,
                nodeQuantity,
                nodeItemName,
                nodeItemTotalAmountWithoutTax,
                nodeTaxAmount);
        root.add(wrapper);
    }
    public void reset(){
        lineCounter = 0;
        root = new NodeNamedSquareBrace("itemInfo");
    }

    public NodeNamedSquareBrace getRoot() {
        return root;
    }
}
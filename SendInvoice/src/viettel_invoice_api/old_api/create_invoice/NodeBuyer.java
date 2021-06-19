package viettel_invoice_api.old_api.create_invoice;

import node.NodeNamedCurlyBrace;
import node.NodeSingleValue;

public final class NodeBuyer {
    private NodeNamedCurlyBrace root;
    public void setBuyerInfo(String name,
                                            String code,
                                            String company,
                                            String taxCode,
                                            String address)
            throws NullPointerException {
        if ((name == null || name.trim().isEmpty())
                && (company == null || company.trim().isEmpty())
                || (address == null || address.trim().isEmpty())
                || (company == null || company.trim().isEmpty())
                && (taxCode == null || taxCode.trim().isEmpty()))
            throw new NullPointerException();
        root = new NodeNamedCurlyBrace("buyerInfo");
        NodeSingleValue nodeName = new NodeSingleValue("buyerName",
                name),
                nodeCode = new NodeSingleValue("buyerCode",
                        code),
                nodeCompany = new NodeSingleValue("buyerLegalName",
                        company),
                nodeTaxCode = new NodeSingleValue("buyerTaxCode",
                        taxCode),
                nodeAddress = new NodeSingleValue("buyerAddressLine",
                        address);
        root.addAll(nodeName,
                nodeCode,
                nodeCompany,
                nodeTaxCode,
                nodeAddress);
    }

    public NodeNamedCurlyBrace getRoot() throws NullPointerException{
        return root;
    }
}

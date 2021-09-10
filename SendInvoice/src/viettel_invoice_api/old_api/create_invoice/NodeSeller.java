package viettel_invoice_api.old_api.create_invoice;

import node.NodeNamedCurlyBrace;
import node.NodeSingleValue;

public final class NodeSeller {
    private NodeNamedCurlyBrace root;
    public NodeNamedCurlyBrace setSellerInfo(){
        root  = new NodeNamedCurlyBrace("sellerInfo");
        return root;
    }

    public NodeNamedCurlyBrace setSellerInfo(String name,
                                             String taxCode,
                                             String address)
            throws NullPointerException,
            IllegalArgumentException {
        if (taxCode == null
                && (name == null || name.trim().isEmpty() || address == null || address.trim().isEmpty()))
            throw new NullPointerException();
        root  = new NodeNamedCurlyBrace("sellerInfo");
        NodeSingleValue sellerLegalName = new NodeSingleValue("sellerLegalName", name),
                sellerTaxCode = new NodeSingleValue("sellerTaxCode", taxCode),
                sellerAddressLine = new NodeSingleValue("sellerAddressLine", address);
        root.addAll(sellerLegalName, sellerTaxCode, sellerAddressLine);
        return root;
    }

    public NodeNamedCurlyBrace getRoot() {
        return root;
    }
}

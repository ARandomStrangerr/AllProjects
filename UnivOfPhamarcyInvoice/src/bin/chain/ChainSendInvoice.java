package bin.chain;

import bin.constant.API;
import bin.constant.Address;
import chain_of_responsibility.Chain;
import user_interface.PaneAbstract;

public final class ChainSendInvoice extends Chain {
    public ChainSendInvoice(Object processObject) {
        super(processObject);
        forgeChain();
    }

    @Override
    protected void forgeChain() {
        super.addLink(new LinkReadExcelFile(this));
        super.addLink(new LinkCreateJsonObject(this));
        super.addLink(new LinkSendJsonObject(this, Address.ADDRESS.value + API.CREATE_DRAFT_INVOICE.value + PaneAbstract.getProperty("username")));
    }
}

package bin.chain;

import bin.constant.API;
import bin.constant.Address;
import chain_of_responsibility.Chain;

public class ChainGetInvoice extends Chain {
    private final String startNum,
            endNum;

    public ChainGetInvoice(Object processObject,
                           String startNum,
                           String endNum) {
        super(processObject);
        this.startNum = startNum;
        this.endNum = endNum;
        forgeChain();
    }

    @Override
    protected void forgeChain() {
        super.addLink(new LinkGetInvoiceJsonObject(this, startNum, endNum));
        super.addLink(new LinkSendJsonObject(this, Address.ADDRESS.value + API.GET_INVOICE.value));
        super.addLink(new LinkMaterializeFile(this));
    }
}

package bin.chain;

import chain_of_responsibility.Chain;
import chain_of_responsibility.Link;
import com.google.gson.JsonObject;
import user_interface.PaneAbstract;

import java.util.LinkedList;
import java.util.List;

public class LinkGetInvoiceJsonObject extends Link {
    private final String startNum,
            endNum;

    public LinkGetInvoiceJsonObject(Chain chain,
                                    String startNum,
                                    String endNum) {
        super(chain);
        this.startNum = startNum;
        this.endNum = endNum;
    }

    @Override
    public boolean handle() {
        List<JsonObject> sendObject;
        JsonObject jsonObj;
        sendObject = new LinkedList<>();
        int endNumInt = Integer.parseInt(endNum);
        int startNumInt = Integer.parseInt(startNum);
        for( int index = startNumInt; index <= endNumInt ; index ++){
            jsonObj = new JsonObject();
            jsonObj.addProperty("supplierTaxCode",(String) PaneAbstract.getProperty("username"));
            jsonObj.addProperty("invoiceNo", String.format("%s%07d", PaneAbstract.getProperty("invoiceSeries"), index));
            jsonObj.addProperty("templateCode", String.format("%s0/%03d", PaneAbstract.getProperty("invoiceType"), Integer.parseInt((String) PaneAbstract.getProperty("templateCode"))));
            jsonObj.addProperty("fileType", "PDF");
            sendObject.add(jsonObj);
        }
        chain.setProcessObject(sendObject);
        return true;
    }
}

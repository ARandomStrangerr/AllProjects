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
        int startNumInt,
                endNumInt;
        sendObject = new LinkedList<>();
        try {
            endNumInt = Integer.parseInt(endNum);
        }catch (NumberFormatException e){
            chain.setErrorMessage("Số hóa đơn kết thúc chưa được điền");
            e.printStackTrace();
            return false;
        }
        try {
            startNumInt = Integer.parseInt(startNum);
        }catch (NumberFormatException e){
            chain.setErrorMessage("Số hóa đơn bắt đầu chưa được điền");
            e.printStackTrace();
            return false;
        }
        if (((String) PaneAbstract.getProperty("username")).trim().isEmpty()){
            chain.setErrorMessage("Tên đăng nhập chưa được điền");
            return false;
        }
        if (((String) PaneAbstract.getProperty("invoiceSeries")).trim().isEmpty()){
            chain.setErrorMessage("Mã loại hóa đơn chưa được chọn");
            return false;
        }
        if (((String) PaneAbstract.getProperty("invoiceType")).trim().isEmpty()){
            chain.setErrorMessage("Ký hiệu hóa đơn chưa được điền");
            return false;
        }
        if( ((String) PaneAbstract.getProperty("saveFolder")).trim().isEmpty()){
            chain.setErrorMessage("Thư mục chứa hóa đơn tải về bị bỏ trống");
        }
        for( int index = startNumInt; index <= endNumInt ; index ++){
            jsonObj = new JsonObject();
            jsonObj.addProperty("supplierTaxCode", (String) PaneAbstract.getProperty("username"));
            jsonObj.addProperty("invoiceNo", String.format("%s%07d", PaneAbstract.getProperty("invoiceSeries"), index));
            try {
                jsonObj.addProperty("templateCode", String.format("%s0/%03d", PaneAbstract.getProperty("invoiceType"), Integer.parseInt((String) PaneAbstract.getProperty("templateCode"))));
            } catch (NumberFormatException e){
                chain.setErrorMessage("Số kí hiệu mẫu hóa đơn không hợp lệ hoặc bị bỏ trống");
                return false;
            }
            jsonObj.addProperty("fileType", "PDF");
            sendObject.add(jsonObj);
        }
        chain.setProcessObject(sendObject);
        return true;
    }
}

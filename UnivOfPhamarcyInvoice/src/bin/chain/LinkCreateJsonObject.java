package bin.chain;

import chain_of_responsibility.Chain;
import chain_of_responsibility.Link;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public final class LinkCreateJsonObject extends Link {
    public LinkCreateJsonObject(Chain chain) {
        super(chain);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean handle() {
        List<List<String>> dataFromExcel;
        List<JsonObject> sendObjects;
        JsonObject rootObject,
                tempObj;
        JsonArray tempArray;
        String tempString;
        int tempInt;
        float taxRate;
        long tempLong;

        //get the data from the json file
        try {
            dataFromExcel = (List<List<String>>) chain.getProcessObject();
        } catch (ClassCastException e) {
            chain.setErrorMessage("Liên lạc với  bên tạo phần mềm để giải quyết");
            e.printStackTrace();
            return false;
        }
        //create a list to hold json object to send
        sendObjects = new LinkedList<>();
        int index = 1; //index to print out the line which is contain error when read the excel file
        for (List<String> row : dataFromExcel) {
            if (row.size() == 0) continue;
            rootObject = new JsonObject();
            //generalInvoiceInfo
            tempObj = new JsonObject();
            tempString = row.get(0).trim(); //invoiceType
            switch (tempString) { //check valid invoice type
                case "01GTKT":
                case "02GTTT":
                case "07KPTQ":
                case "03XKNB":
                case "04HGDL":
                case "01BLP":
                    tempObj.addProperty("invoiceType", tempString);
                    break;
                default:
                    chain.setErrorMessage("Mã hoá đơn không hợp lệ ở dòng số " + index);
                    return false;
            }
            tempString = row.get(1).trim(); //invoiceSeries
            if (Pattern.matches("[A-Z][A-Z]/[0-9][0-9][A-Z]", tempString)) { //check valid invoice series (not check if it exists or not)
                tempObj.addProperty("invoiceSeries", tempString);
            } else {
                chain.setErrorMessage("Kí hiệu hoá đơn không hợp lệ ở dòng số " + index);
                return false;
            }
            try {
                tempInt = Integer.parseInt(row.get(2).trim()); //templateCode number
            } catch (NumberFormatException e) { //check if the string is able to convert to integer
                chain.setErrorMessage("Số kí hiệu hoá đơn không hợp lệ ở dòng số " + index);
                return false;
            }
            tempString = String.format("%s0/%03d", tempObj.get("invoiceType").getAsString(), tempInt); //templateCode
            tempObj.addProperty("templateCode", tempString);
            try {
                tempInt = Integer.parseInt(row.get(3).trim()); //adjustmentType
            } catch (NumberFormatException e) { //check if the string is able to convert to integer
                chain.setErrorMessage("Trạng thái điều chỉnh hóa đơn không hợp lệ ở dòng số " + index);
                return false;
            }
            switch (tempInt) {
                case 1:
                    tempObj.addProperty("adjustmentType", tempInt);
                    break;
                default:
                    chain.setErrorMessage("Giá trị Trạng thái điều chỉnh hóa đơn không hợp lệ ở dòng số " + index);
                    return false;
            }
            tempString = "VND"; //currencyCode
            tempObj.addProperty("currencyCode", tempString);
            tempObj.addProperty("paymentStatus", true);//payment status
            tempObj.addProperty("paymentStatus", true);//cusGetInvoiceRight
            rootObject.add("generalInvoiceInfo", tempObj);
            //sellerInfo
            tempObj = new JsonObject();
            rootObject.add("sellerInfo", tempObj);
            //buyerInfo
            tempObj = new JsonObject();
            tempString = row.get(6).trim(); //buyerName
            if (!tempString.isEmpty())
                tempObj.addProperty("buyerName", tempString);
            tempString = row.get(11).trim(); //buyerCode
            if (!tempString.isEmpty())
                tempObj.addProperty("buyerCode", tempString);
            tempString = row.get(12).trim(); //buyerLegalName
            if (!tempString.isEmpty())
                tempObj.addProperty("buyerLegalName", tempString);
            tempString = row.get(13).trim(); //buyerTaxCode
            if (!tempString.isEmpty())
                tempObj.addProperty("buyerTaxCode", tempString);
            if (!(tempObj.has("buyerName") || tempObj.has("buyerLegalName"))) { //check if buyer name or company name are blank
                chain.setErrorMessage("Tên người mua hoặc tên đơn vị là bắt buộc ở dòng số " + index);
                return false;
            }
            if (tempObj.has("buyerLegalName") && !tempObj.has("buyerTaxCode")) { //check if tax code is blank when the company name exists
                chain.setErrorMessage("Mã số thuế của đơn vị không được bỏ trống khi tên đơn vị được điền ở dòng số " + index);
                return false;
            }
            tempString = row.get(14).trim(); //address
            if (tempString.isEmpty()) {
                chain.setErrorMessage("Địa chỉ xuất hoá đơn bị bỏ trống ở dòng số " + index);
                return false;
            } else
                tempObj.addProperty("buyerAddressLine", tempString);
            rootObject.add("buyerInfo", tempObj);
            //payment method
            tempObj = new JsonObject();
            tempString = row.get(16).trim();
            tempObj.addProperty("paymentMethodName", tempString);
            tempArray = new JsonArray();
            tempArray.add(tempString);
            rootObject.add("payments", tempArray);
            //item info
            tempArray = new JsonArray();
            try { //check if the string is convertible to float
                taxRate = Float.parseFloat(row.get(15).trim());
            } catch (NumberFormatException e) {
                chain.setErrorMessage("Mức thuế xuất không chính xác");
                e.printStackTrace();
                return false;
            }
            for (int cellIndex = 18; cellIndex < row.size(); cellIndex++) {
                tempObj = new JsonObject(); //object for each item
                String[] tempArrString = row.get(cellIndex).split(";"); //array for each item cell
                if (tempArrString.length == 0) continue;
                tempInt = Integer.parseInt(tempArrString[0].trim()); //selection
                switch (tempInt) {
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                        tempObj.addProperty("selection", tempInt);
                        tempString = tempArrString[1].trim(); //itemName
                        tempObj.addProperty("itemName", tempString);
                        tempString = tempArrString[2].trim(); //unitName
                        tempObj.addProperty("unitName", tempString);
                        try {
                            tempInt = Integer.parseInt(tempArrString[3].trim()); //quantity
                            if (tempInt <= 0) throw new NumberFormatException("negative quantity");
                        } catch (NumberFormatException e) {
                            chain.setErrorMessage("Số lượng sản phẩm không hợp lệ");
                            e.printStackTrace();
                            return false;
                        }
                        tempObj.addProperty("quantity", tempInt);
                        try {
                            tempLong = Long.parseLong(tempArrString[4].trim()); //unitPrice
                            if (tempLong < 0) throw new NumberFormatException("price quantity");
                        } catch (NumberFormatException e) {
                            chain.setErrorMessage("Đơn giá sản phẩm không hợp lệ");
                            e.printStackTrace();
                            return false;
                        }
                        tempObj.addProperty("unitPrice", tempLong);
                        tempLong = tempObj.get("quantity").getAsInt() * tempObj.get("unitPrice").getAsLong();
                        tempObj.addProperty("itemTotalAmountWithoutTax", tempLong);
                        tempObj.addProperty("taxPercentage", taxRate);
                        try {
                            tempObj.addProperty("discount", Float.parseFloat(tempArrString[6].trim()));
                        } catch (NumberFormatException e){
                            chain.setErrorMessage("Phần trăm chiết khấu 1 không hợp lệ ở dòng số " + index);
                        }try {
                        tempObj.addProperty("discount2", Float.parseFloat(tempArrString[7].trim()));
                    } catch (NumberFormatException e){
                        chain.setErrorMessage("Phần trăm chiết khấu 2 không hợp lệ ở dòng số " + index);
                    }
                        break;
                    default:
                        chain.setErrorMessage("Đánh dấu loại hàng hóa/dịch vụ không chính xác tại dòng " + index);
                        return false;
                }
                tempArray.add(tempObj);
            }
            rootObject.add("itemInfo", tempArray);
            rootObject.add("summarizeInfo", new JsonObject());
            rootObject.add("taxBreakdowns", new JsonArray());
            sendObjects.add(rootObject);
            index++;
        }
        chain.setProcessObject(sendObjects);
        return true;
    }
}
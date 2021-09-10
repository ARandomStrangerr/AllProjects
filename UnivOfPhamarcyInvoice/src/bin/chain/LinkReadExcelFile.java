package bin.chain;

import chain_of_responsibility.Chain;
import chain_of_responsibility.Link;
import file_operation.ExcelFile;

import java.io.IOException;
import java.util.List;

public final class LinkReadExcelFile extends Link {

    public LinkReadExcelFile(Chain chain) {
        super(chain);
    }

    @Override
    public boolean handle() {
        String filePath;
        List<List<String>> dataFromExcel;
        filePath = (String) chain.getProcessObject();
        try {
            dataFromExcel = ExcelFile.getInstance().read(filePath);
        }catch (IOException e) {
            chain.setErrorMessage("Không đọc được tệp tin Excel");
            e.printStackTrace();
            return false;
        }
        chain.setProcessObject(dataFromExcel);
        return true;
    }
}

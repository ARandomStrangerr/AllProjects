package bin.command;

import bin.error.NullInfoException;
import javafx.stage.Stage;
import user_interface.ErrorWindow;
import user_interface.WaitWindow;
import viettel_electronic_invoice_webservice.UploadBill;

public final class SendCommandExcelFile implements Command {
    private final String sellerName,
            sellerAddress,
            invoiceSeries,
            invoiceType,
            templateCode,
            excelFilePath,
            folderPath,
            username,
            password,
            exchangeUser;
    private final Stage ownerStage;

    public SendCommandExcelFile(String sellerName,
                                String sellerAddress,
                                String invoiceSeries,
                                String invoiceType,
                                String templateCode,
                                String excelFilePath,
                                String folderPath,
                                String username,
                                String password,
                                String exchangeUser,
                                Stage ownerStage) {
        if (sellerName.isBlank()) throw new NullInfoException("Tên đơn vị bán", ownerStage);
        this.sellerName = sellerName;
        if (sellerAddress.isBlank()) throw new NullInfoException("Địa chỉ", ownerStage);
        this.sellerAddress = sellerAddress;
        if (invoiceSeries.isBlank()) throw new NullInfoException("Kí hiệu", ownerStage);
        this.invoiceSeries = invoiceSeries;
        if (invoiceType.isBlank()) throw new NullInfoException("Loại hóa đơn", ownerStage);
        this.invoiceType = invoiceType;
        if (templateCode.isBlank()) throw new NullInfoException("Kí hiệu mẫu hóa đơn", ownerStage);
        this.templateCode = templateCode;
        if (excelFilePath.isBlank()) throw new NullInfoException("Đường dẫn tệp tin excel", ownerStage);
        this.excelFilePath = excelFilePath;
        if (folderPath.isBlank()) throw new NullInfoException("Đường dẫn thư mục chứa hóa đơn", ownerStage);
        this.folderPath = folderPath;
        if (username.isBlank()) throw new NullInfoException("Tên đăng nhập", ownerStage);
        this.username = username;
        if (password.isBlank()) throw new NullInfoException("Mật khẩu", ownerStage);
        this.password = password;
        if (exchangeUser.isBlank()) throw new NullInfoException("Tên người dùng", ownerStage);
        this.exchangeUser = exchangeUser;
        this.ownerStage = ownerStage;
    }

    @Override
    public void execute() {
//        String TAX_CODE_CONSTANT = "";
//        if (!username.equals(TAX_CODE_CONSTANT)){
//            new ErrorWindow(ownerStage," :) ");
//            return;
//        }
        WaitWindow waitWindow = new WaitWindow(ownerStage);
        waitWindow.show();
        UploadBill uploadBillThread = new UploadBill(username, password, excelFilePath, invoiceType, templateCode, waitWindow);
    }
}
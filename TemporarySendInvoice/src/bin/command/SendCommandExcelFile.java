package bin.command;

import bin.error.NullInfoException;
import javafx.stage.Stage;
import user_interface.WaitWindow;
import viettel_electronic_invoice_webservice.UploadBill;
import viettel_electronic_invoice_webservice.UploadBillUpgrade;

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
        if (sellerName.isEmpty()) throw new NullInfoException("Tên đơn vị bán", ownerStage);
        this.sellerName = sellerName;
        if (sellerAddress.isEmpty()) throw new NullInfoException("Địa chỉ", ownerStage);
        this.sellerAddress = sellerAddress;
        if (invoiceSeries.isEmpty()) throw new NullInfoException("Kí hiệu", ownerStage);
        this.invoiceSeries = invoiceSeries;
        if (invoiceType.isEmpty()) throw new NullInfoException("Loại hóa đơn", ownerStage);
        this.invoiceType = invoiceType;
        if (templateCode.isEmpty()) throw new NullInfoException("Kí hiệu mẫu hóa đơn", ownerStage);
        this.templateCode = templateCode;
        if (excelFilePath.isEmpty()) throw new NullInfoException("Đường dẫn tệp tin excel", ownerStage);
        this.excelFilePath = excelFilePath;
        if (folderPath.isEmpty()) throw new NullInfoException("Đường dẫn thư mục chứa hóa đơn", ownerStage);
        this.folderPath = folderPath;
        if (username.isEmpty()) throw new NullInfoException("Tên đăng nhập", ownerStage);
        this.username = username;
        if (password.isEmpty()) throw new NullInfoException("Mật khẩu", ownerStage);
        this.password = password;
        if (exchangeUser.isEmpty()) throw new NullInfoException("Tên người dùng", ownerStage);
        this.exchangeUser = exchangeUser;
        this.ownerStage = ownerStage;
    }

    @Override
    public void execute() {
        WaitWindow waitWindow = new WaitWindow(ownerStage);
        waitWindow.show();
        new UploadBillUpgrade(username, password, excelFilePath, invoiceType, templateCode, waitWindow);
    }
}
package bin.command;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import user_interface.WaitWindow;
import viettel_electronic_invoice_webservice.GetPdfBill;

public final class RetreatCommand implements Command {
    private final String username, password, exchangeUser, folderPath;
    private final Stage ownerStage;

    public RetreatCommand(Stage ownerStage, String username, String password, String exchangeUser, String folderPath) {
        this.username = username;
        this.password = password;
        this.exchangeUser = exchangeUser;
        this.folderPath = folderPath;
        this.ownerStage = ownerStage;
    }

    @Override
    public void execute(){
        FileChooser fileChooser = new FileChooser();
        String file = fileChooser.showOpenDialog(new Stage()).toString();
        WaitWindow waitWindow = new WaitWindow(ownerStage);
        waitWindow.show();
        new GetPdfBill(waitWindow, username, password, exchangeUser, folderPath, file);
    }
}

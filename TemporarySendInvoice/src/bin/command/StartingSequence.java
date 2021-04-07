package bin.command;

import bin.file.FileOperation;
import bin.file.TextFile;
import javafx.scene.Scene;
import javafx.stage.Stage;
import user_interface.MainMenu;

import java.io.IOException;
import java.util.List;

public final class StartingSequence implements Command {
    private final Stage ownerStage;

    public StartingSequence(Stage ownerStage) {
        this.ownerStage = ownerStage;
    }

    /**
     * starting sequences of the program , first it read the saved data , if not , everything will be initialized in blank
     */
    @Override
    public void execute() {
        FileOperation textFileOperation = TextFile.getInstance();
        MainMenu paneMain;
        /*
        data will be saved in manner : sellerName, sellerAddress, invoiceSeries, invoiceType, templateCode, exchangeUser, excelFilePath, folderPath
         */
        try {   //try to read the data
            List<String> data = textFileOperation.read("config.txt");
            String name, address, series, type, template, exchangeUser, filePath, folderPath;
            try {
                name = data.get(0);
            } catch (IndexOutOfBoundsException e) {
                name = "";
            }
            try {
                address = data.get(1);
            } catch (IndexOutOfBoundsException e) {
                address = "";
            }
            try {
                series = data.get(2);
            } catch (IndexOutOfBoundsException e) {
                series = "";
            }
            try {
                type = data.get(3);
            } catch (IndexOutOfBoundsException e) {
                type = "";
            }
            try {
                template = data.get(4);
            } catch (IndexOutOfBoundsException e) {
                template = "";
            }
            try {
                exchangeUser = data.get(5);
            } catch (IndexOutOfBoundsException e) {
                exchangeUser = "";
            }
            try {
                filePath = data.get(6);
            } catch (IndexOutOfBoundsException e) {
                filePath = "";
            }
            try {
                folderPath = data.get(7);
            } catch (IndexOutOfBoundsException e) {
                folderPath = "";
            }
            paneMain = new MainMenu(ownerStage, name, address, series, type, template, exchangeUser, filePath, folderPath);
        } catch (IOException e) {   //error is thrown when the file cannot be read or does not exist
            paneMain = new MainMenu(ownerStage);
        }

        ownerStage.setScene(new Scene(paneMain.getPane()));
        ownerStage.setTitle("CLIENT SIDE V1.0 BETA");
        ownerStage.show();
    }
}
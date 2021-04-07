package bin.command;

import bin.file.FileOperation;
import bin.file.TextFile;

import java.io.IOException;

public final class SaveCommand implements Command {
    private final String name, address, series, type, templateCode, exchangeUser, filePath, folderPath;

    public SaveCommand(String sellerName, String sellerAddress, String invoiceSeries, String invoiceType, String templateCode, String exchangeUser, String excelFilePath, String folderPath) {
        this.name = sellerName;
        this.address = sellerAddress;
        this.series = invoiceSeries;
        this.type = invoiceType;
        this.templateCode = templateCode;
        this.exchangeUser = exchangeUser;
        this.filePath = excelFilePath;
        this.folderPath = folderPath;
    }

    @Override
    public void execute() {
        String written = name + "\n" + address + "\n" + series + "\n" + type + "\n" + templateCode + "\n" + exchangeUser + "\n" + filePath + "\n" + folderPath;
        FileOperation textFile = TextFile.getInstance();
        try {
            textFile.writeNew("config.txt", written);
        } catch (IOException ignore) {
        }
    }
}

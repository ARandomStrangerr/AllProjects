package user_interface.command;

import bin.file.FileInterface;
import bin.file.TextFile;
import javafx.scene.effect.GaussianBlur;
import javafx.stage.Stage;
import user_interface.*;

import java.io.IOException;
import java.util.LinkedHashMap;

/**
 * display the server designation and information .
 *
 */
public class ShowServerConfigPane implements CommandInterface {
    private final WindowAbstract windowAbstract;

    public ShowServerConfigPane(WindowAbstract windowAbstract) {
        this.windowAbstract = windowAbstract;
    }

    @Override
    public void execute() {
        FileInterface textFile = TextFile.getInstance();    //get instance of the file operation
        LinkedHashMap<String, String> data = new LinkedHashMap<>(); //map to hold data
        try {   //try to read data
            for (String line : textFile.read("server_config.txt")) {
                String[] lineArr = line.split(":");
                try {   //if the data is corrupted or malformation
                    data.put(lineArr[0], lineArr[1]);
                } catch (IndexOutOfBoundsException ignore) {
                }
            }
        } catch (IOException ignore) {
        }
        PaneServerConfig paneServerConfig = new PaneServerConfig(windowAbstract, data);   //create fill in pane
        WindowRegular window = new WindowRegular(windowAbstract.getOwnerStage());
        window.setThisStage(paneServerConfig);
        window.setup();
        window.show();
    }
}

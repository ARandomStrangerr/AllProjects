package bin.command;

import bin.file.TextFile;
import command.CommandInterface;
import javafx.stage.Stage;
import user_interface.PaneMain;
import user_interface.PaneYesNoConcrete;
import user_interface.PaneServerInfo;
import user_interface.WindowPopup;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

public final class OpenServerSettingPane implements CommandInterface {
    private final Stage ownerStage;

    public OpenServerSettingPane(Stage ownerStage) {
        this.ownerStage = ownerStage;
    }

    @Override
    public void execute() {
        PaneYesNoConcrete paneWrapper = PaneYesNoConcrete.getInstance();
        PaneServerInfo paneServerInfo = PaneServerInfo.getInstance();
        if (paneWrapper.getWindow() == null){
            WindowPopup windowPopup = new WindowPopup();
            windowPopup.setup();
            windowPopup.setPane(paneWrapper);
            windowPopup.setOwnerStage(PaneMain.getInstance().getWindow().getCurrentStage());
            paneWrapper.setWindow(windowPopup);
        }
        CommandInterface yesButtonCommand = new YesButtonCommand(paneServerInfo, (WindowPopup) paneWrapper.getWindow()),
                noButtonCommand = new NoButtonCommand((WindowPopup) paneWrapper.getWindow());
        paneWrapper.setYesButtonAction(yesButtonCommand);
        paneWrapper.setNoButtonAction(noButtonCommand);
        paneWrapper.setUpperPane(paneServerInfo);
        try {
            TextFile textFile = TextFile.getInstance();
            List<String> data = textFile.read("config.txt");
            LinkedHashMap<String, String> map = new LinkedHashMap<>();
            for (String line : data) {
                String[] lineArr = line.split("=");
                map.put(lineArr[0], lineArr[1]);
            }
            paneServerInfo.setAddress(map.get("serverAddress"));
            paneServerInfo.setPort(map.get("serverPort"));
            paneServerInfo.setUsername(map.get("username"));
            paneServerInfo.setPassword(map.get("password"));
        } catch (IOException | IndexOutOfBoundsException ignore) {
        }
        paneWrapper.getWindow().openCurrentStage();
    }
}

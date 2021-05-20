package bin.command;

import bin.file.TextFile;
import command.CommandInterface;
import user_interface.PaneServerInfo;
import user_interface.PaneYesNoConcrete;
import user_interface.WindowPopup;

import java.io.IOException;

public final class YesButtonCommand implements CommandInterface {
    private final PaneServerInfo paneServerInfo;
    private final WindowPopup serverConfigWindow;

    public YesButtonCommand(PaneServerInfo paneServerInfo, WindowPopup serverConfigWindow) {
        this.paneServerInfo = paneServerInfo;
        this.serverConfigWindow = serverConfigWindow;
    }

    @Override
    public void execute() {
        String address = paneServerInfo.getAddress(),
                port = paneServerInfo.getPort(),
                username = paneServerInfo.getUsername(),
                password = paneServerInfo.getPassword();
        if (address.isEmpty()
                || port.isEmpty()
                || username.isBlank()
                || password.isBlank()) {
            new OpenMessageWindow("Chưa điền đủ thông tin", PaneYesNoConcrete.getInstance().getWindow()).execute();
            return;
        }
        TextFile writer = TextFile.getInstance();
        String writeData = "serverAddress=" + address + "\n" +
                "serverPort=" + port + "\n" +
                "username=" + username + "\n" +
                "password=" + password;
        try {
            writer.write("config.txt", writeData);
        } catch (IOException e) {
            return;
        }
        serverConfigWindow.closeCurrentStage();
    }
}

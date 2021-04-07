package user_interface.command;

import bin.file.FileInterface;
import bin.file.TextFile;

import java.io.IOException;
import java.util.LinkedList;

public class SaveServerConfig implements CommandInterface {
    private final String username, password, serverAddress;
    private final int port;

    public SaveServerConfig(String username, String password, String serverAddress, String port) throws NumberFormatException {
        this.username = username.trim();
        this.password = password.trim();
        this.serverAddress = serverAddress.trim();
        this.port = Integer.parseInt(port.trim());
    }

    @Override
    public void execute() {
        FileInterface textFile = TextFile.getInstance();
        LinkedList<String> data = new LinkedList<>();
        data.add("username:" + username + "\n");
        data.add("password:" + password + "\n");
        data.add("serverAddress:" + serverAddress + "\n");
        data.add("port:" + port + "\n");
        try {
            textFile.write("server_config.txt", data);
        } catch (IOException e) {
            //todo display error message
        }
    }
}
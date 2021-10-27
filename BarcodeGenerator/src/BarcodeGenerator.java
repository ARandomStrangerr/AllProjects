import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.MainPane;
import user_interface.windows.WindowRegular;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Enumeration;

public class BarcodeGenerator extends Application {
    public static void main(String[] args) throws SocketException {
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        String macAddress = "";
        while (networkInterfaces.hasMoreElements()) {
            NetworkInterface ni = networkInterfaces.nextElement();
            byte[] hardwareAddress = ni.getHardwareAddress();
            if (hardwareAddress != null) {
                String[] hexadecimalFormat = new String[hardwareAddress.length];
                for (int i = 0; i < hardwareAddress.length; i++) {
                    hexadecimalFormat[i] = String.format("%02X", hardwareAddress[i]);
                }
                macAddress = String.join("-", hexadecimalFormat);
            }
        }

        if (macAddress.equals("F0-2F-74-F4-51-A")) {
            System.setProperty("configFileName", "config.txt");
            launch(args);
        } else {
            System.exit(1);
        }
    }

    public void start(Stage stage) {
        MainPane.getInstance().setWindow(new WindowRegular(stage));
        Scene scene = new Scene(MainPane.getInstance().getMainPane());
        scene.getStylesheets().add("ui/stylesheet/stylesheet.css");
        stage.setScene(scene);
        stage.setTitle("Công ty Điện - Điện tử Tin học EIE");
        stage.show();
    }
}

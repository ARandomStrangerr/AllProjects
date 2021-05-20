package user_interface;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public final class PaneServerInfo extends PaneAbstract {
    //static components
    public static PaneServerInfo cache;

    public static PaneServerInfo getInstance() {
        if (cache == null) cache = new PaneServerInfo();
        return cache;
    }

    public static PaneServerInfo getInstance(WindowAbstract windowAbstract) {
        if (cache == null) cache = new PaneServerInfo();
        cache.setWindow(windowAbstract);
        return cache;
    }

    public static PaneServerInfo getInstance(String serverAddress, String serverPort, String username, String password) {
        if (cache == null) cache = new PaneServerInfo();
        cache.setAddress(serverAddress);
        cache.setPort(serverPort);
        cache.setUsername(username);
        cache.setPassword(password);
        return cache;
    }

    public static PaneServerInfo getInstance(WindowAbstract windowAbstract, String serverAddress, String serverPort, String username, String password) {
        if (cache == null) cache = new PaneServerInfo();
        cache.setWindow(windowAbstract);
        cache.setAddress(serverAddress);
        cache.setPort(serverPort);
        cache.setUsername(username);
        cache.setPassword(password);
        return cache;
    }
    //end of static components

    private final TextField serverAddressTextField, serverPortTextField, usernameTextField, passwordTextField;
    private final VBox mainPane;

    private PaneServerInfo() {
        super(new VBox());
        serverAddressTextField = new TextField();
        serverPortTextField = new TextField();
        usernameTextField = new TextField();
        passwordTextField = new TextField();
        mainPane = (VBox) super.getMainPane();
        setup();
    }

    @Override
    public void setup() {
//        mainPane.getStylesheets().add("stylesheet/universal.css");
        //text filed
        serverAddressTextField.setPrefWidth(300);
        serverPortTextField.setPrefWidth(80);
        usernameTextField.setPrefWidth(250);
        passwordTextField.setPrefWidth(130);
        //label
        Label serverAddressLabel = new Label("Địa chỉ"),
                serverPortLabel = new Label("cổng"),
                usernameLabel = new Label("Tên đăng nhập"),
                passwordLabel = new Label("Mật khẩu");
        //grid pane
        GridPane usernameAndPasswordPane = new GridPane(),
                serverAddressAndPort = new GridPane();
        usernameAndPasswordPane.add(usernameLabel, 0, 0);
        usernameAndPasswordPane.add(usernameTextField, 0, 1);
        usernameAndPasswordPane.add(passwordLabel, 1, 0);
        usernameAndPasswordPane.add(passwordTextField, 1, 1);
        serverAddressAndPort.add(serverAddressLabel, 0, 0);
        serverAddressAndPort.add(serverPortLabel, 1, 0);
        serverAddressAndPort.add(serverAddressTextField, 0, 1);
        serverAddressAndPort.add(serverPortTextField, 1, 1);
        usernameAndPasswordPane.getStyleClass().add("grid-pane-spacing");
        serverAddressAndPort.getStyleClass().add("grid-pane-spacing");
        //main pane
        mainPane.getChildren().addAll(serverAddressAndPort, usernameAndPasswordPane);
        mainPane.getStyleClass().addAll("padding", "white-background");
    }

    public void setAddress(String address) {
        serverAddressTextField.setText(address);
    }

    public void setPort(String port) {
        serverPortTextField.setText(port);
    }

    public void setUsername(String username) {
        usernameTextField.setText(username);
    }

    public void setPassword(String password) {
        passwordTextField.setText(password);
    }

    public String getAddress() {
        return serverAddressTextField.getText();
    }

    public String getPort() {
        return serverPortTextField.getText();
    }

    public String getUsername() {
        return usernameTextField.getText();
    }

    public String getPassword() {
        return passwordTextField.getText();
    }
}

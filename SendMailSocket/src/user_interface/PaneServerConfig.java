package user_interface;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import user_interface.command.SaveServerConfig;

import java.util.Map;

public class PaneServerConfig extends PaneAbstract {
    private final Map<String, String> data;

    public PaneServerConfig(WindowAbstract windowAbstract, Map<String, String> data) {
        super(windowAbstract, new VBox());
        this.data = data;
        setting();
    }

    @Override
    protected void setting() {
        VBox pane = (VBox) super.getPane();
        Label usernameLabel = new Label("Tên đăng nhập"),
                passwordLabel = new Label("Mật khẩu"),
                serverAddressLabel = new Label("Địa chỉ server nhận mail"),
                serverPortLabel = new Label("Cổng");
        TextField usernameTextField = new TextField(),
                passwordTextField = new TextField(),
                serverAddressTextField = new TextField(),
                serverPortTextField = new TextField();
        Button saveButton = new Button("Lưu");
        //setup text field
        serverAddressTextField.setText(data.get("serverAddress"));
        serverPortTextField.setText(data.get("port"));
        usernameTextField.setText(data.get("username"));
        passwordTextField.setText(data.get("password"));
        //setup button
        saveButton.setOnAction(event -> super.execute(new SaveServerConfig(usernameTextField.getText(), passwordTextField.getText(), serverAddressTextField.getText(), serverPortTextField.getText())));
        pane.getChildren().addAll(serverAddressLabel, serverAddressTextField, serverPortLabel, serverPortTextField, usernameLabel, usernameTextField, passwordLabel, passwordTextField, saveButton);
    }
}

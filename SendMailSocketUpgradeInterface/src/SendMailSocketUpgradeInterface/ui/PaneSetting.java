package SendMailSocketUpgradeInterface.ui;

import SendMailSocketUpgradeInterface.bin.command.OpenRedMessageWindow;
import SendMailSocketUpgradeInterface.bin.command.ReadFromLog;
import SendMailSocketUpgradeInterface.bin.command.WriteToLog;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

public class PaneSetting extends PaneAbstract {
    public PaneSetting() {
        super(new VBox());
    }

    @Override
    protected void create() {
        Label labelUsername = new Label("Tên đăng nhập"),
                labelPassword = new Label("Mật khẩu"),
                labelServerAddress = new Label("Địa chỉ server"),
                labelServerPort = new Label("Cổng");
        TextField textFieldUsername = new TextField(),
                textFieldPassword = new TextField(),
                textFieldServerAddress = new TextField(),
                textFieldServerPort = new TextField();
        textFieldUsername.setText(ReadFromLog.tbl.get("username"));
        textFieldPassword.setText(ReadFromLog.tbl.get("password"));
        textFieldServerAddress.setText(ReadFromLog.tbl.get("serverAddr"));
        textFieldServerPort.setText(ReadFromLog.tbl.get("port"));
        Button btnSave = new Button("Đồng ý"),
                btnCancel = new Button("Huỷ bỏ");
        btnCancel.setOnAction(event -> {
            getStage().close();
        });
        btnSave.setOnAction(event -> {
            String username = textFieldUsername.getText().trim(),
                    password = textFieldPassword.getText().trim(),
                    serverAddr = textFieldServerAddress.getText().trim();
            if (username.isEmpty()) {
                new OpenRedMessageWindow(super.getStage(), "Tên đăng nhập bị bỏ trống").execute();
                return;
            } else if (password.isEmpty()) {
                new OpenRedMessageWindow(super.getStage(), "Mật khẩu bị bỏ trống").execute();
                return;
            } else if (serverAddr.isEmpty()) {
                new OpenRedMessageWindow(super.getStage(), "Địa chỉ máy chủ bị bỏ trống").execute();
                return;
            }
            int port;
            try {
                port = Integer.parseInt(textFieldServerPort.getText());
            } catch (NumberFormatException e){
                new OpenRedMessageWindow(super.getStage(), "Trường thông tin \"Cổng\" không hợp lệ").execute();
                return;
            }
            new WriteToLog(username,password,serverAddr,port).execute();
            super.getStage().close();
        });
        btnSave.setId("button-green");
        btnCancel.setId("button-red");
        HBox wrapper = new HBox( btnSave, btnCancel);
        GridPane upperWrapper = new GridPane();
        upperWrapper.add(labelUsername, 0, 0);
        upperWrapper.add(labelPassword, 1, 0);
        upperWrapper.add(textFieldUsername, 0, 1);
        upperWrapper.add(textFieldPassword, 1, 1);
        upperWrapper.add(labelServerAddress, 0, 2);
        upperWrapper.add(labelServerPort, 1, 2);
        upperWrapper.add(textFieldServerAddress, 0, 3);
        upperWrapper.add(textFieldServerPort, 1, 3);
        upperWrapper.add(wrapper, 0, 4, 2, 1);
        upperWrapper.getStyleClass().add("upper-pane");
        wrapper.getStyleClass().add("lower-pane");
        VBox pane = (VBox) super.getPane();
        pane.getChildren().addAll(upperWrapper,wrapper);
        pane.getStylesheets().add("style.css");
        pane.setId("setting-pane");
    }
}

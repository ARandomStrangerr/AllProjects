package user_interface.element;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import user_interface.PaneAbstract;

public final class PaneLogin extends PaneAbstract {
    private static PaneLogin cache;

    public static PaneLogin getInstance() {
        if (cache == null) cache = new PaneLogin();
        return cache;
    }

    private final GridPane mainPane;
    private final TextField usernameTextField,
            passwordTextField;
    private final Label usernameLabel,
            passwordLabel;

    private PaneLogin() {
        super(new GridPane());
        mainPane = (GridPane) super.getMainPane();
        usernameLabel = new Label("Tên đăng nhập");
        passwordLabel = new Label("Mật khẩu");
        usernameTextField = new TextField();
        passwordTextField = new TextField();
        setup();
    }

    @Override
    protected void setup() {
        mainPane.add(usernameLabel, 0, 0);
        mainPane.add(usernameTextField, 1, 0);
        mainPane.add(passwordLabel, 0, 1);
        mainPane.add(passwordTextField, 1, 1);
        mainPane.getStyleClass().addAll("grid-pane-spacing","padding");
    }

    public String getUsername() {
        return usernameTextField.getText();
    }

    public String getPassword(){
        return passwordTextField.getText();
    }
}

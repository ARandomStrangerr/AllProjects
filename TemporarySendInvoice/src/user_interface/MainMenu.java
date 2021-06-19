package user_interface;

import bin.command.*;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public final class MainMenu extends SuperPane {
    private final VBox primePane = (VBox) super.getPane();
    private final TextField filePathTextField = new TextField(),
            folderPathTextField = new TextField(),
            sellerNameTextField = new TextField(),
            sellerAddressTextField = new TextField(),
            invoiceSeriesTextField = new TextField(),
            templateCodeTextField = new TextField(),
            exchangeUserTextField = new TextField();
    private final ChoiceBox<String> invoiceTypeChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList("01GTKT", "02GTTT", "07KPTQ", "03XKNB", "04HGDL", "01BLP"));

    public MainMenu(Stage ownerStage, String sellerName, String sellerAddress, String invoiceSeries, String invoiceType, String template, String exchangeUser, String filePath, String folderPath) {
        super(ownerStage, new VBox());
        filePathTextField.setText(filePath);
        sellerNameTextField.setText(sellerName);
        sellerAddressTextField.setText(sellerAddress);
        invoiceSeriesTextField.setText(invoiceSeries);
        invoiceTypeChoiceBox.setValue(invoiceType);
        exchangeUserTextField.setText(exchangeUser);
        folderPathTextField.setText(folderPath);
        templateCodeTextField.setText(template);
        construct();
    }

    public MainMenu(Stage ownerStage) {
        this(ownerStage, "", "", "", "", "", "", "", "");
    }

    private void construct() {
        Label basicInfoLabel = new Label("Thông tin cơ bản"),
                sellerNameLabel = new Label("Đơn vị bán"),
                sellerAddressLabel = new Label("Địa chỉ"),
                invoiceTypeLabel = new Label("Loại hóa đơn"),
                invoiceSeriesLabel = new Label("Kí hiệu"),
                templateCodeLabel = new Label("Kí hiệu mẫu hóa đơn"),
                filePathLabel = new Label("Đường dẫn tệp chứa thông tin hóa đơn"),
                folderPathLabel = new Label("Đường dẫn thư mục lưu giữ hóa đơn"),
                exchangeUserLabel = new Label("Người liên hệ");
        Button fileChooserButton = new Button("Chọn"),
                folderChooserButton = new Button("Chọn"),
                saveInfoButton = new Button("Lưu"),
                sendInfoButton = new Button("Gửi"),
                retreatButton = new Button("Lấy về");
        Region saveRegion = new Region(),
                sendInfoRegion = new Region();
        HBox sellerNameWrapper = new HBox(sellerNameLabel, sellerNameTextField),
                sellerAddressWrapper = new HBox(sellerAddressLabel, sellerAddressTextField),
                invoiceTypeWrapper = new HBox(invoiceSeriesLabel, invoiceSeriesTextField, invoiceTypeLabel, invoiceTypeChoiceBox, templateCodeLabel, templateCodeTextField, saveRegion, saveInfoButton),
                filePathWrapper = new HBox(filePathTextField, fileChooserButton),
                folderPathWrapper = new HBox(folderPathTextField, folderChooserButton),
                sendInfoButtonWrapper = new HBox(sendInfoRegion, retreatButton, sendInfoButton);

        HBox.setHgrow(saveRegion, Priority.ALWAYS);
        HBox.setHgrow(sendInfoRegion, Priority.ALWAYS);
        HBox.setHgrow(sellerNameTextField, Priority.ALWAYS);
        HBox.setHgrow(sellerAddressTextField, Priority.ALWAYS);
        HBox.setHgrow(filePathTextField, Priority.ALWAYS);
        HBox.setHgrow(folderPathTextField, Priority.ALWAYS);

        saveInfoButton.setOnAction(event -> new SaveCommand(
                sellerNameTextField.getText(),
                sellerAddressTextField.getText(),
                invoiceSeriesTextField.getText(),
                invoiceTypeChoiceBox.getValue(),
                templateCodeTextField.getText(),
                exchangeUserTextField.getText(),
                filePathTextField.getText(),
                folderPathTextField.getText()
        ).execute());
        fileChooserButton.setOnAction(event -> new FileChooserCommand(filePathTextField).execute());
        folderChooserButton.setOnAction(event -> new FolderChooserCommand(folderPathTextField).execute());
        sendInfoButton.setOnAction(event -> showLoginWindow());
        retreatButton.setOnAction(event -> showLoginWindow2());

        primePane.getChildren().addAll(basicInfoLabel, sellerNameWrapper, sellerAddressWrapper, invoiceTypeWrapper, exchangeUserTextField, filePathLabel, filePathWrapper, folderPathLabel, folderPathWrapper, sendInfoButtonWrapper);
        primePane.getStylesheets().add("stylesheet/unify.css");
        primePane.getStyleClass().addAll("pane", "pane-padding");
        sellerNameWrapper.getStyleClass().add("pane");
        sellerAddressWrapper.getStyleClass().add("pane");
        invoiceTypeWrapper.getStyleClass().add("pane");
    }

    private void showLoginWindow() {
        Label usernameLabel = new Label("Tên đăng nhập"),
                passwordLabel = new Label("Mật khẩu");
        TextField usernameTextField = new TextField(),
                passwordTextField = new TextField();
        Button loginButton = new Button("Gửi");
        Region region = new Region();
        HBox buttonBox = new HBox(region, loginButton);
        VBox loginBox = new VBox(usernameLabel, usernameTextField, passwordLabel, passwordTextField, buttonBox);
        Stage stage = new Stage();

        HBox.setHgrow(region, Priority.ALWAYS);

        loginBox.getStylesheets().add("stylesheet/unify.css");
        loginBox.getStyleClass().addAll("pane-padding", "pane");

        loginButton.setOnAction(event -> {
            new SendCommandExcelFile(sellerNameTextField.getText(),
                    sellerAddressTextField.getText(),
                    invoiceSeriesTextField.getText(),
                    invoiceTypeChoiceBox.getValue(),
                    templateCodeTextField.getText(),
                    filePathTextField.getText(),
                    folderPathTextField.getText(),
                    usernameTextField.getText(),
                    passwordTextField.getText(),
                    exchangeUserTextField.getText(),
                    super.getOwnerStage()
            ).execute();
            stage.close();
        });

        stage.setScene(new Scene(loginBox));
        stage.setTitle("Đăng nhập");
        stage.initOwner(super.getOwnerStage());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    private void showLoginWindow2() {
        Label usernameLabel = new Label("Tên đăng nhập"),
                passwordLabel = new Label("Mật khẩu");
        TextField usernameTextField = new TextField(),
                passwordTextField = new TextField();
        Button loginButton = new Button("Gửi");
        Region region = new Region();
        HBox buttonBox = new HBox(region, loginButton);
        VBox loginBox = new VBox(usernameLabel, usernameTextField, passwordLabel, passwordTextField, buttonBox);
        Stage stage = new Stage();

        HBox.setHgrow(region, Priority.ALWAYS);

        loginBox.getStylesheets().add("stylesheet/unify.css");
        loginBox.getStyleClass().addAll("pane-padding", "pane");

        loginButton.setOnAction(event -> {
            new RetreatCommand(getOwnerStage(),
                    usernameTextField.getText(),
                    passwordTextField.getText(),
                    exchangeUserTextField.getText(),
                    folderPathTextField.getText()
            ).execute();
            stage.close();
        });

        stage.setScene(new Scene(loginBox));
        stage.setTitle("Đăng nhập");
        stage.initOwner(super.getOwnerStage());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
}
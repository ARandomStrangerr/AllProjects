package user_interface;

import bin.chain.ChainGetInvoice;
import bin.chain.ChainSendInvoice;
import bin.command.*;
import chain_of_responsibility.Chain;
import com.google.gson.JsonObject;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.List;

public final class MainPane extends PaneAbstract {

    private static MainPane cache;

    public static MainPane getInstance() {
        if (cache == null) cache = new MainPane();
        return cache;
    }

    private final Region createInvoiceButtonPaneRegion,
            getInvoiceButtonPaneRegion;
    private final Label usernameLabel,
            passwordLabel,
            excelPathFileLabel,
            startNumLabel,
            endNumLabel,
            saveFolderLabel,
            invoiceTypeLabel,
            templateCodeLabel,
            invoiceSeriesLabel,
            hintLabel;
    private final ChoiceBox<String> invoiceTypeChoiceBox;
    private final TextField usernameTextField,
            passwordTextField,
            saveFolderPathTextField,
            startNumTextField,
            endNumTextField,
            templateCodeTextField,
            invoiceSeriesTextField,
            excelPathFileTextField;
    private final Button selectFileButton,
            saveButton,
            selectFolderButton,
            getInvoiceButton,
            createInvoiceButton;
    private final GridPane componentPane,
            invoiceInfoPane;
    private final HBox excelPathFileTextFieldGroup,
            saveFolderPathTextFieldGroup,
            createInvoiceButtonPane,
            getInvoiceButtonPane;
    private final VBox mainPane;

    private MainPane() {
        super(new VBox());
        createInvoiceButtonPaneRegion = new Region();

        invoiceTypeChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList("01GTKT",
                "02GTTT",
                "07KPTQ",
                "03XKNB",
                "04HGDL",
                "01BLP"));

        invoiceTypeLabel = new Label("Mã loại hóa đơn");
        templateCodeLabel = new Label("Số ký hiệu mẫu hóa đơn");
        invoiceSeriesLabel = new Label("Ký hiệu hóa đơn");
        usernameLabel = new Label("Tên đăng nhập");
        passwordLabel = new Label("Mật khẩu");
        excelPathFileLabel = new Label("Đường dẫn file excel cần gởi");
        startNumLabel = new Label("Số bắt đầu");
        endNumLabel = new Label("Số kết thúc");
        saveFolderLabel = new Label("Thư mục chứa hoá đơn tải về");
        hintLabel = new Label("Chú ý : ngày phát sinh hóa đơn là ngày gửi");

        usernameTextField = new TextField();
        passwordTextField = new TextField();
        saveFolderPathTextField = new TextField();
        startNumTextField = new TextField();
        endNumTextField = new TextField();
        excelPathFileTextField = new TextField();
        templateCodeTextField = new TextField();
        invoiceSeriesTextField = new TextField();

        selectFileButton = new Button("Chọn");
        selectFolderButton = new Button("Chọn");
        saveButton = new Button("Lưu");
        createInvoiceButton = new Button("Gửi thông tin");
        getInvoiceButton = new Button("Lấy hoá đơn");

        excelPathFileTextFieldGroup = new HBox();
        saveFolderPathTextFieldGroup = new HBox();
        getInvoiceButtonPaneRegion = new HBox();
        createInvoiceButtonPane = new HBox();
        getInvoiceButtonPane = new HBox();
        componentPane = new GridPane();
        invoiceInfoPane = new GridPane();
        mainPane = (VBox) super.getMainPane();
        setup();
    }

    @Override
    protected void setup() {
        //region
        HBox.setHgrow(createInvoiceButtonPaneRegion, Priority.ALWAYS);
        HBox.setHgrow(getInvoiceButtonPaneRegion, Priority.ALWAYS);

        //text filed
        usernameTextField.setId("username");
        usernameTextField.setText((String) PaneAbstract.getProperty("username"));
        passwordTextField.setId("password");
        passwordTextField.setText((String) PaneAbstract.getProperty("password"));
        HBox.setHgrow(excelPathFileTextField, Priority.ALWAYS);
        excelPathFileTextField.setEditable(false);
        excelPathFileTextField.setId("excelPath");
        excelPathFileTextField.setText((String) PaneAbstract.getProperty("excelPath"));
        startNumTextField.setId("startNum");
        endNumTextField.setId("endNum");
        HBox.setHgrow(saveFolderPathTextField, Priority.ALWAYS);
        saveFolderPathTextField.setEditable(false);
        saveFolderPathTextField.setId("saveFolderPath");
        saveFolderPathTextField.setText((String) PaneAbstract.getProperty("saveFolderPath"));
        templateCodeTextField.setId("templateCode");
        templateCodeTextField.setText((String) getProperty("templateCode"));
        invoiceSeriesTextField.setId("invoiceSeries");
        invoiceSeriesTextField.setText((String) getProperty("invoiceSeries"));
        saveFolderPathTextField.setId("saveFolder");
        saveFolderPathTextField.setText((String) getProperty("saveFolder"));

        //button
        selectFileButton.setOnAction(event -> new CommandSetFilePath(this.getWindow(), excelPathFileTextField).execute());
        selectFolderButton.setOnAction(event -> new CommandSetFolderPath(this.getWindow(), saveFolderPathTextField).execute());
        saveButton.setOnAction(event -> {
            setProperty(usernameTextField.getId(), usernameTextField.getText());
            setProperty(passwordTextField.getId(), passwordTextField.getText());
            try {
                setProperty(excelPathFileTextField.getId(), excelPathFileTextField.getText());
                setProperty(invoiceTypeChoiceBox.getId(), invoiceTypeChoiceBox.getValue());
            } catch (Exception ignore) {
            }
            setProperty(templateCodeTextField.getId(), templateCodeTextField.getText());
            setProperty(invoiceSeriesTextField.getId(), invoiceSeriesTextField.getText());
            setProperty(saveFolderPathTextField.getId(), saveFolderPathTextField.getText());
            new SaveProperties().execute();
        });
        createInvoiceButton.setOnAction(event -> {
            saveButton.fire();
            BlockMessagePane.getInstance().setMsg("Vui lòng đợi");
            new OpenWindowPopup(BlockMessagePane.getInstance(), this.getWindow()).execute();
            Runnable runnable = () -> {
                Chain sendInvoiceChain = new ChainSendInvoice(excelPathFileTextField.getText());
                if (sendInvoiceChain.handle()) {
                    List<JsonObject> responseMsgList = (List<JsonObject>) sendInvoiceChain.getProcessObject();
                    String startIndexInvoice = responseMsgList.get(0).getAsJsonObject().get("invoiceNo").getAsString(),
                            endIndexInvoice = responseMsgList.get(responseMsgList.size() - 1).getAsJsonObject().get("invoiceNo").getAsString(),
                            response = String.format("Thành công\nSố bắt đầu: %s\nSố kết thúc: %s", startIndexInvoice, endIndexInvoice);
                    Platform.runLater(() -> {
                        MessagePane.getInstance().setMsg(response);
                        new OpenPaneMessage(MessagePane.getInstance(), PaneMessageConcrete.getInstance(), getWindow()).execute();
                    });
                } else {
                    String response;
                    try {
                        List<JsonObject> responseMsgList = (List<JsonObject>) sendInvoiceChain.getProcessObject();
                        String startIndexInvoice = responseMsgList.get(0).getAsJsonObject().get("invoiceNo").getAsString(),
                                endIndexInvoice = responseMsgList.get(responseMsgList.size() - 1).getAsJsonObject().get("invoiceNo").getAsString();
                        response = String.format("Đã gởi một phần dữ liệu\nSố bắt đầu: %s\nSố kết thúc: %s\n%s", startIndexInvoice, endIndexInvoice, sendInvoiceChain.getErrorMessage());
                    } catch (ClassCastException e) {
                        response = String.format("Lỗi: %s", sendInvoiceChain.getErrorMessage());
                    }
                    String finalResponse = response;
                    Platform.runLater(() -> {
                        MessagePane.getInstance().setMsg(finalResponse);
                        new OpenPaneMessage(MessagePane.getInstance(), PaneMessageConcrete.getInstance(), getWindow()).execute();
                    });
                }
                Platform.runLater(() -> BlockMessagePane.getInstance().getWindow().closeCurrentStage());
            };
            new Thread(runnable).start();
        });
        getInvoiceButton.setOnAction(event -> {
            saveButton.fire();
            BlockMessagePane.getInstance().setMsg("Vui lòng đợi");
            new OpenWindowPopup(BlockMessagePane.getInstance(), this.getWindow()).execute();
            Runnable runnable = () -> {
                Chain sendInvoiceChain = new ChainGetInvoice(null, startNumTextField.getText(), endNumTextField.getText());
                if (sendInvoiceChain.handle()) {
                    Platform.runLater(() -> {
                        MessagePane.getInstance().setMsg("Thành công");
                        new OpenPaneMessage(MessagePane.getInstance(), PaneMessageConcrete.getInstance(), getWindow()).execute();
                    });
                } else {
                    Platform.runLater(() -> {
                        MessagePane.getInstance().setMsg(sendInvoiceChain.getErrorMessage());
                        new OpenPaneMessage(MessagePane.getInstance(), PaneMessageConcrete.getInstance(), getWindow()).execute();
                    });
                }
                Platform.runLater(() -> BlockMessagePane.getInstance().getWindow().closeCurrentStage());
            };
            new Thread(runnable).start();
        });

        //choice box
        invoiceTypeChoiceBox.setPrefWidth(150);
        invoiceTypeChoiceBox.setId("invoiceType");

        //pane
        excelPathFileTextFieldGroup.getChildren().addAll(excelPathFileTextField,
                selectFileButton);
        createInvoiceButtonPane.getChildren().addAll(createInvoiceButtonPaneRegion,
                saveButton,
                createInvoiceButton);
        saveFolderPathTextFieldGroup.getChildren().addAll(saveFolderPathTextField, selectFolderButton);
        getInvoiceButtonPane.getChildren().addAll(getInvoiceButtonPaneRegion, getInvoiceButton);
        createInvoiceButtonPane.getStyleClass().addAll("spacing");
        HBox.setHgrow(invoiceInfoPane, Priority.ALWAYS);
        invoiceInfoPane.setId("gridPane");
        invoiceInfoPane.add(invoiceTypeLabel, 0, 0);
        invoiceInfoPane.add(templateCodeLabel, 1, 0);
        invoiceInfoPane.add(invoiceSeriesLabel, 2, 0);
        invoiceInfoPane.add(invoiceTypeChoiceBox, 0, 1);
        invoiceInfoPane.add(templateCodeTextField, 1, 1);
        invoiceInfoPane.add(invoiceSeriesTextField, 2, 1);
        componentPane.setId("body");
        componentPane.add(usernameLabel, 0, 0);
        componentPane.add(usernameTextField, 1, 0);
        componentPane.add(passwordLabel, 2, 0);
        componentPane.add(passwordTextField, 3, 0);
        componentPane.add(excelPathFileLabel, 0, 1, 4, 1);
        componentPane.add(excelPathFileTextFieldGroup, 0, 2, 4, 1);
        componentPane.add(createInvoiceButtonPane, 0, 3, 4, 1);
        componentPane.add(invoiceInfoPane, 0, 4, 4, 1);
        componentPane.add(startNumLabel, 0, 5);
        componentPane.add(startNumTextField, 1, 5);
        componentPane.add(endNumLabel, 2, 5);
        componentPane.add(endNumTextField, 3, 5);
        componentPane.add(saveFolderLabel, 0, 6, 4, 1);
        componentPane.add(saveFolderPathTextFieldGroup, 0, 7, 4, 1);
        componentPane.add(getInvoiceButtonPane, 0, 8, 4, 1);
        mainPane.getChildren().add(componentPane);
        mainPane.getStylesheets().add("stylesheet/universal.css");
        mainPane.getStyleClass().addAll("spacing");
    }
}

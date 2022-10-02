package ui;

import bin.command.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class PaneMain extends PaneAbstract {
    public PaneMain(Stage primaryStage) {
        super(primaryStage, new VBox());
    }

    @Override
    protected void create() {
        Button btnDelete = new Button("-"),
                setAttachmentFolderButton = new Button("Chọn"),
                sendButton = new Button("Gửi thư");
        btnDelete.setId("button-red");
        MenuItem optionAddOne = new MenuItem("add one"),
                optionAddMany = new MenuItem("add many"),
                setting = new MenuItem("Cài đặt");
        setting.setOnAction(event -> new OpenSettingWindow(this.getStage()).execute());
        Menu settingMenu = new Menu("Cài đặt");
        settingMenu.getItems().add(setting);
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(settingMenu);
        MenuButton btnAdd = new MenuButton("+");
        btnAdd.setId("button-green");
        btnAdd.getItems().addAll(optionAddOne, optionAddMany);
        TableColumn<EmailTableData, String> colEmail = new TableColumn<>("E-Mail"),
                colFile = new TableColumn<>("Tệp tin đính kèm");
        TableView<EmailTableData> table = new TableView<>();
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colEmail.setCellFactory(TextFieldTableCell.forTableColumn());
        colEmail.setOnEditCommit(event -> table.getSelectionModel().getSelectedItem().setEmail(event.getNewValue()));
        colFile.setCellValueFactory(new PropertyValueFactory<>("file"));
        colFile.setCellFactory(TextFieldTableCell.forTableColumn());
        colFile.setOnEditCommit(event -> table.getSelectionModel().getSelectedItem().setFile(event.getNewValue()));
        optionAddMany.setOnAction(event -> new ReadExcelFile(super.getStage(), table.getItems()).execute());
        optionAddOne.setOnAction(event -> table.getItems().add(new EmailTableData("Trống", "Trống")));
        table.getColumns().addAll(colEmail, colFile);
        table.setEditable(true);
        TextField subjectTextField = new TextField(),
                attachmentFolderTextField = new TextField();
        attachmentFolderTextField.setEditable(false);
        setAttachmentFolderButton.setOnAction(event -> {
            DirectoryChooser dc = new DirectoryChooser();
            attachmentFolderTextField.setText(dc.showDialog(super.getStage()).getAbsolutePath());
        });
        Label tableTitle = new Label("Danh sách người nhận"),
                mailSubjectTitle = new Label("Tiêu đề"),
                messageTitle = new Label("Nội dung thư"),
                attachmentFolderTitle = new Label("Thư mục chứa tệp tin đính kèm");
        TextArea mailBodyTextArea = new TextArea();
        Region tableControlPaddingLeft = new Region(),
                sendPaddingLeft = new Region();
        HBox.setHgrow(tableControlPaddingLeft, Priority.ALWAYS);
        HBox.setHgrow(sendPaddingLeft, Priority.ALWAYS);
        HBox wrapperTableController = new HBox(tableTitle, tableControlPaddingLeft, btnDelete, btnAdd),
                wrapperAttachmentFolder = new HBox(attachmentFolderTitle, attachmentFolderTextField, setAttachmentFolderButton),
                wrapperEmailSubject = new HBox(mailSubjectTitle, subjectTextField),
                wrapperSendButton = new HBox(sendPaddingLeft, sendButton);
        wrapperTableController.setId("wrapper");
        wrapperEmailSubject.setId("wrapper");
        wrapperSendButton.setId("wrapper");
        wrapperAttachmentFolder.setId("wrapper");
        VBox pane = (VBox) super.getPane(),
                mainBody = new VBox(wrapperTableController, table, wrapperAttachmentFolder, wrapperEmailSubject, messageTitle, mailBodyTextArea, wrapperSendButton);
        mainBody.setId("main-body");
        pane.getChildren().addAll(menuBar, mainBody);
        pane.getStylesheets().add("./ui/style.css");
        pane.setPrefHeight(600);
        sendButton.setOnAction(event -> {
            String username = ReadFromLog.tbl.get("username"),
                    password = ReadFromLog.tbl.get("password"),
                    serverAddr = ReadFromLog.tbl.get("serverAddr"),
                    attachmentFolder = attachmentFolderTextField.getText().trim(),
                    subject = subjectTextField.getText().trim(),
                    bodyMsg = mailBodyTextArea.getText().trim();
            int port = Integer.parseInt(ReadFromLog.tbl.get("port"));
            new SendEmail(username, password, serverAddr, port, attachmentFolder, table.getItems(), subject, bodyMsg).execute();
        });
    }
}

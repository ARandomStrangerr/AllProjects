package user_interface;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import user_interface.command.*;
import user_interface.table_datatype.ReceiverList;

public class MainMenu extends SuperWindow {
    public MainMenu(Stage stage) {
        super(stage, "Công ty Điện - Điện tử Tin học EIE");
    }

    @Override
    protected Pane setupPane() {
        ObservableList<ReceiverList> list = FXCollections.observableArrayList();

        TableView<ReceiverList> table = new TableView<>();
        table.setEditable(true);
        table.setItems(list);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        super.setupControl(super.TABLE_STYLE, "normal", table);

        TableColumn<ReceiverList, String> nameColumn = new TableColumn<>("Tên"),
                emailColumn = new TableColumn<>("e-Mail"),
                attachmentColumn = new TableColumn<>("Đính kèm");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setOnEditCommit(event -> table.getSelectionModel().getSelectedItem().setName(event.getNewValue()));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        emailColumn.setOnEditCommit(event -> table.getSelectionModel().getSelectedItem().setEmail(event.getNewValue()));
        attachmentColumn.setCellValueFactory(new PropertyValueFactory<>("attachment"));
        attachmentColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        attachmentColumn.setOnEditCommit(event -> table.getSelectionModel().getSelectedItem()
                .setAttachment(event.getNewValue()));

        table.getColumns().addAll(nameColumn, emailColumn, attachmentColumn);

        Label attachmentLabel = new Label("Thư mục chứa tệp tin đính kèm"),
                messageLabel = new Label("Tin nhắn gởi kèm"),
                subjectLabel = new Label("Tiều đề"),
                tableLabel = new Label("Danh sách người nhận");
        super.setupControl(super.LABEL_STYLE, "normal", attachmentLabel, messageLabel, subjectLabel,
                tableLabel);
        super.setupControl(super.LABEL_STYLE, "title", attachmentLabel, tableLabel, messageLabel);

        TextArea bodyMessageTextArea = new TextArea();
        bodyMessageTextArea.setPromptText("Nội dung");

        TextField attachmentFolderTextField = new TextField(),
                subjectTextField = new TextField();
        super.setupControl(super.TEXT_FIELD_STYLE, "normal", attachmentFolderTextField, subjectTextField,
                bodyMessageTextArea);
        HBox.setHgrow(subjectTextField, Priority.ALWAYS);
        HBox.setHgrow(attachmentFolderTextField, Priority.ALWAYS);

        MenuItem addSinglePersonOption = new MenuItem("Thêm một người"),
                addFromFileOption = new MenuItem("Thêm từ file");
        addSinglePersonOption.setOnAction(event -> list.add(new ReceiverList("", "", "")));
        addFromFileOption.setOnAction(event -> super.executeCommand(new OpenAndReadReceiverExcelFile(list)));

        MenuButton addOption = new MenuButton("Thêm");
        addOption.getItems().addAll(addSinglePersonOption, addFromFileOption);

        Button sendButton = new Button("Gửi"),
                deleteButton = new Button("Xóa"),
                choseAttachmentFolderButton = new Button("Chọn"),
                helpButton = new Button("?");
        sendButton.setOnAction(action -> super.executeCommand(new SendMails(list, subjectTextField.getText().trim(),
                bodyMessageTextArea.getText().trim(), attachmentFolderTextField.getText(), stage)));
        deleteButton.setOnAction(action -> super.executeCommand(new DeleteEntryOfTable(table)));
        choseAttachmentFolderButton.setOnAction(action -> super.executeCommand(new OpenAttachmentFolderChooser(
                attachmentFolderTextField)));
        super.setupControl(super.BUTTON_STYLE, "normal", sendButton, addOption, deleteButton,
                choseAttachmentFolderButton, helpButton);

        Region betweenTableLabelAndTableOperationButtons = new Region(),
                beforeSendButton = new Region();
        HBox.setHgrow(betweenTableLabelAndTableOperationButtons, Priority.ALWAYS);
        HBox.setHgrow(beforeSendButton, Priority.ALWAYS);

        HBox tableLabelAndOperationWrapper = new HBox(tableLabel, betweenTableLabelAndTableOperationButtons,
                addOption, deleteButton),
                helpAndSendButtonWrapper = new HBox(helpButton, beforeSendButton, sendButton),
                folderChooserWrapper = new HBox(attachmentFolderTextField, choseAttachmentFolderButton),
                subjectWrapper = new HBox(subjectLabel, subjectTextField);
        super.setupPane(super.BOX_STYLE, "leftMarginWrapper", helpAndSendButtonWrapper,
                tableLabelAndOperationWrapper, folderChooserWrapper, subjectWrapper);

        VBox mainPane = new VBox(tableLabelAndOperationWrapper, table, attachmentLabel, folderChooserWrapper,
                messageLabel, subjectWrapper, bodyMessageTextArea, helpAndSendButtonWrapper);
        super.setupPane(super.BOX_STYLE, "background", mainPane);

        helpButton.setOnAction(action -> super.executeCommand(new OpenHelpWindow(super.stage, mainPane)));
        return mainPane;
    }
}
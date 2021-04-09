package user_interface;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import user_interface.command.*;
import user_interface.table_item.ReceiverInfo;

public class PaneMain extends PaneAbstract {

    public PaneMain() {
        super(new VBox());
        setting();
    }

    @Override
    protected void setting() {
        VBox thisPane = (VBox) super.getPane();
        MenuBar menuBar = new MenuBar();
        Menu settingMenu = new Menu("Cài đặt");
        MenuItem serverSettingMenuItem = new MenuItem("Cấu hình máy chủ"),
                addSingleEntryMenuItem = new MenuItem("Thêm một người"),
                addFromExcelMenuItem = new MenuItem("Thêm từ file Excel");
        Label tableLabel = new Label("Bảng thông tin người nhận"),
                attachmentPathFieldLabel = new Label("Thư mục chưa tệp tin đính kèm"),
                mailBodyLabel = new Label("Nội dung thư"),
                subjectLabel = new Label("Tiêu đề");
        TextField attachmentPathTextField = new TextField(),
                subjectTextFiled = new TextField();
        TextArea bodyTextArea = new TextArea();
        TableView<ReceiverInfo> table = new TableView<>();
        ObservableList<ReceiverInfo> items = table.getItems();
        TableColumn<ReceiverInfo, String> emailColumn = new TableColumn<>("Địa chỉ người nhận"),
                attachmentDirectoryColumn = new TableColumn<>("Tên tệp tin đính kèm");
        MenuButton addButton = new MenuButton("+", null, addSingleEntryMenuItem, addFromExcelMenuItem);
        Button deleteButton = new Button("-"),
                folderPickerButton = new Button("Chọn"),
                sendButton = new Button("Gửi"),
                saveButton = new Button("Lưu");
        Region region1 = new Region(),
                region2 = new Region(),
                region3 = new Region();
        HBox wrapper1 = new HBox(tableLabel, region1, addButton, deleteButton),
                wrapper2 = new HBox(attachmentPathTextField, folderPickerButton),
                wrapper3 = new HBox(subjectLabel, subjectTextFiled),
                wrapper4 = new HBox(region3, saveButton, sendButton);
        //setup menu
        menuBar.getMenus().add(settingMenu);
        settingMenu.getItems().add(serverSettingMenuItem);
        //setup region
        HBox.setHgrow(region1, Priority.ALWAYS);
        HBox.setHgrow(region2, Priority.ALWAYS);
        HBox.setHgrow(region3, Priority.ALWAYS);
        //setup text field
        HBox.setHgrow(attachmentPathTextField, Priority.ALWAYS);
        attachmentPathTextField.setPromptText("Đường dẫn");
        HBox.setHgrow(subjectTextFiled, Priority.ALWAYS);
        //setup table
        table.setEditable(true);
        table.getColumns().add(emailColumn);
        table.getColumns().add(attachmentDirectoryColumn);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //setup column
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("emailAddress"));
        emailColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        emailColumn.setOnEditCommit(event -> table.getSelectionModel().getSelectedItem().setEmailAddress(event.getNewValue()));
        attachmentDirectoryColumn.setCellValueFactory(new PropertyValueFactory<>("attachmentFileName"));
        attachmentDirectoryColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        attachmentDirectoryColumn.setOnEditCommit(event -> table.getSelectionModel().getSelectedItem().setAttachmentFileName(event.getNewValue()));
        //setup button
        serverSettingMenuItem.setOnAction(event -> super.execute(new ShowServerConfigPane(getOwnerWindow())));
        addSingleEntryMenuItem.setOnAction(event -> super.execute(new AddSingleEntryToTable(items)));
        addFromExcelMenuItem.setOnAction(event -> super.execute(new AddExcelFileToTable(items, getOwnerWindow().getOwnerStage())));
        folderPickerButton.setOnAction(event -> super.execute(new SelectFolder(super.getOwnerWindow().getOwnerStage(), attachmentPathTextField)));
        sendButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String subject = subjectTextFiled.getText();
                String body = bodyTextArea.getText();
                String attachment = attachmentPathTextField.getText();
                new Send(table.getItems(), subject, body, attachment).execute();
            }
        });
        //setup pane
        thisPane.getChildren().addAll(menuBar, wrapper1, table, attachmentPathFieldLabel, wrapper2, mailBodyLabel, wrapper3, bodyTextArea, wrapper4);
        thisPane.setPrefHeight(500);
    }
}

package user_interface;

import bin.command.*;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import user_interface.table_item.ReceiverInfo;

public final class PaneMain extends PaneAbstract {
    //static components
    private static PaneMain cache;

    public static PaneMain getInstance() {
        if (cache == null) cache = new PaneMain();
        return cache;
    }

    public static PaneMain getInstance(WindowAbstract windowAbstract) {
        if (cache == null) cache = new PaneMain(windowAbstract);
        return cache;
    }
    //end of static components

    private final VBox mainPane;
    private final TextField attachmentFolderTextField = new TextField(),
            mailSubjectTextField = new TextField();
    private final TextArea mailBodyTextArea = new TextArea();
    private final TableView<ReceiverInfo> tableView = new TableView<>();

    private PaneMain() {
        super(new VBox());
        this.mainPane = (VBox) super.getMainPane();
        setup();
    }

    private PaneMain(WindowAbstract window) {
        super(new VBox());
        super.setWindow(window);
        this.mainPane = (VBox) super.getMainPane();
        setup();
    }

    @Override
    public void setup() {
        mainPane.getStylesheets().add("stylesheet/universal.css");
        //menu
        MenuBar menuBar = new MenuBar();
        Menu settingMenu = new Menu("Cài đặt");
        MenuItem serverConfigurationMenuItem = new MenuItem("Thông tin server");
        serverConfigurationMenuItem.setOnAction(event -> new OpenServerSettingPane(super.getWindow().getCurrentStage()).execute());
        menuBar.getMenus().add(settingMenu);
        settingMenu.getItems().add(serverConfigurationMenuItem);
        //table
        TableColumn<ReceiverInfo, String> emailColumn = new TableColumn<>("Địa chỉ nhận"),
                attachmentColumn = new TableColumn<>("Tệp đính kèm");
        tableView.setEditable(true);
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        emailColumn.setOnEditCommit(event -> tableView.getSelectionModel().getSelectedItem().setEmail(event.getNewValue()));
        attachmentColumn.setCellValueFactory(new PropertyValueFactory<>("variableField"));
        attachmentColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        attachmentColumn.setOnEditCommit(event -> tableView.getSelectionModel().getSelectedItem().setVariableField(event.getNewValue()));
        tableView.getColumns().add(emailColumn);
        tableView.getColumns().add(attachmentColumn);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableView.setPlaceholder(new Label("Không có gì"));
        //text field
        attachmentFolderTextField.setEditable(false);
        HBox.setHgrow(attachmentFolderTextField, Priority.ALWAYS);
        HBox.setHgrow(mailSubjectTextField, Priority.ALWAYS);
        //buttons
        Button removeButton = new Button("-"),
                attachmentFolderSelectButton = new Button("Chọn"),
                saveInfoButton = new Button("Lưu"),
                sendButton = new Button("Gửi");
        MenuButton addButton = new MenuButton("+");
        MenuItem addOne = new MenuItem("Thêm một người"),
                addFromExcel = new MenuItem("Thêm từ excel");
        addButton.getItems().addAll(addOne, addFromExcel);
        addOne.setOnAction(event -> new AddSingleButton(tableView.getItems()).execute());
        addFromExcel.setOnAction(event -> new AddFromExcelButton(tableView.getItems()).execute());
        removeButton.setOnAction(event -> new RemoveButton(tableView).execute());
        attachmentFolderSelectButton.setOnAction(event -> new SelectAttachmentFolderButton(attachmentFolderTextField).execute());
        sendButton.setOnAction(event -> new SendButton().execute());
        addButton.getStyleClass().add("green-button");
        removeButton.getStyleClass().add("red-button");
        //label
        Label mailSubjectLabel = new Label("Tiêu đề"),
                messageComponentLabel = new Label("Nội dung thư"),
                tableLabel = new Label("Thông tin người nhận"),
                attachmentFolderLabel = new Label("Thư mục chứa tệp đính kèm");
        messageComponentLabel.getStyleClass().add("label-title");
        attachmentFolderLabel.getStyleClass().add("label-title");
        tableLabel.getStyleClass().add("label-title");
        //region
        Region tableManipulateButtonRegion = new Region(),
                functionButtonRegion = new Region();
        HBox.setHgrow(tableManipulateButtonRegion, Priority.ALWAYS);
        HBox.setHgrow(functionButtonRegion, Priority.ALWAYS);
        //wrapper
        HBox tableManipulateButtonGroup = new HBox(tableLabel, tableManipulateButtonRegion, addButton, removeButton),
                attachmentFolderGroup = new HBox(attachmentFolderTextField, attachmentFolderSelectButton),
                mailSubjectGroup = new HBox(mailSubjectLabel, mailSubjectTextField),
                functionButtonGroup = new HBox(functionButtonRegion, saveInfoButton, sendButton);
        VBox bodyGroup = new VBox(tableManipulateButtonGroup, tableView, attachmentFolderLabel, attachmentFolderGroup, messageComponentLabel, mailSubjectGroup, mailBodyTextArea, functionButtonGroup);
        tableManipulateButtonGroup.getStyleClass().addAll("spacing", "alignment");
        attachmentFolderGroup.getStyleClass().add("alignment");
        mailSubjectGroup.getStyleClass().addAll("spacing", "alignment");
        functionButtonGroup.getStyleClass().add("spacing");
        bodyGroup.getStyleClass().addAll("background", "spacing");
        //main pane
        mainPane.getChildren().addAll(menuBar, bodyGroup);
    }

    public String getMessageSubject() {
        return mailSubjectTextField.getText();
    }

    public String getMessageBody() {
        return mailBodyTextArea.getText();
    }

    public String getAttachmentFolder() {
        return attachmentFolderTextField.getText();
    }

    public ObservableList<ReceiverInfo> getItems() {
        return tableView.getItems();
    }
}

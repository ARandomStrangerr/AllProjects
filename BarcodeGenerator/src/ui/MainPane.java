package ui;

import bin.command.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.*;
import ui.table_structure.BarcodeInstance;
import user_interface.PaneAbstract;

public final class MainPane extends PaneAbstract {
    private static MainPane cache;

    public static MainPane getInstance() {
        if (cache == null) cache = new MainPane();
        return cache;
    }

    private final Label tableLabel,
            paperSetupLabel,
            stampWidthLabel,
            stampHeightLabel,
            paperWidthLabel,
            paperHeightLabel,
            numberOfStampPerLineLabel;
    private final TextField stampWidthTextField,
            stampHeightTextField,
            paperWidthTextField,
            paperHeightTextField,
            numberOfStampPerLineTextField;
    private final Button deleteButton,
            printButton;
    private final MenuButton addMenuButton;
    private final MenuItem addSingleInstance,
            addFromExcel;
    public final TableView<BarcodeInstance> table;
    public final TableColumn<BarcodeInstance, String> codeCol,
            labelCol;
    private final Region labelAndButtonForTableRegion,
            printButtonRegion;
    private final GridPane settingGrid;
    public final HBox labelAndButtonForTableWrapper,
            printButtonWrapper;
    private final VBox mainPane;

    private MainPane() {
        super(new VBox());
        mainPane = (VBox) super.getMainPane();
        tableLabel = new Label("Danh sách mã vạch");
        paperSetupLabel = new Label("Định dạng khổ giấy in ra");
        stampWidthLabel = new Label("Độ rộng của tem");
        stampHeightLabel = new Label("Độ cao của tem");
        paperWidthLabel = new Label("Độ rộng giấy");
        paperHeightLabel = new Label("Độ cao giấy");
        numberOfStampPerLineLabel = new Label("Số tem mỗi hàng");
        stampWidthTextField = new TextField();
        stampHeightTextField = new TextField();
        paperWidthTextField = new TextField();
        paperHeightTextField= new TextField();
        numberOfStampPerLineTextField = new TextField();

        deleteButton = new Button("-");
        printButton = new Button("In");
        addMenuButton = new MenuButton("+");
        addSingleInstance = new MenuItem("Thêm");
        addFromExcel = new MenuItem("Thêm từ Excel");
        table = new TableView<>();
        codeCol = new TableColumn<>("Mã vạch");
        labelCol = new TableColumn<>("Chú thích");
        settingGrid = new GridPane();
        labelAndButtonForTableRegion = new Region();
        printButtonRegion = new Region();
        labelAndButtonForTableWrapper = new HBox();
        printButtonWrapper = new HBox();
        setup();
    }

    @Override
    protected void setup() {
        table.getItems().add(new BarcodeInstance("01257892557", "thu nghiem"));

        HBox.setHgrow(labelAndButtonForTableRegion, Priority.ALWAYS);
        HBox.setHgrow(printButtonRegion, Priority.ALWAYS);

        table.setEditable(true);
        codeCol.setCellValueFactory(new PropertyValueFactory<>("code"));
        codeCol.setCellFactory(TextFieldTableCell.forTableColumn());
        codeCol.setOnEditCommit(event -> table.getSelectionModel().getSelectedItem().setCode(event.getNewValue()));
        labelCol.setCellValueFactory(new PropertyValueFactory<>("label"));
        labelCol.setCellFactory(TextFieldTableCell.forTableColumn());
        labelCol.setOnEditCommit(event -> table.getSelectionModel().getSelectedItem().setLabel(event.getNewValue()));
        table.getColumns().addAll(codeCol, labelCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        settingGrid.add(stampHeightLabel, 0, 0);
        settingGrid.add(stampHeightTextField, 1, 0);
        settingGrid.add(stampWidthLabel, 2, 0);
        settingGrid.add(stampWidthTextField, 3, 0);
        settingGrid.add(paperHeightLabel, 0, 1);
        settingGrid.add(paperHeightTextField, 1, 1);
        settingGrid.add(paperWidthLabel, 2, 1);
        settingGrid.add(paperWidthTextField, 3, 1);
        settingGrid.add(numberOfStampPerLineLabel, 0, 2);
        settingGrid.add(numberOfStampPerLineTextField, 1, 2);

        stampWidthTextField.setText((String) getProperty("stampWidth"));
        stampWidthTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*"))
                stampWidthTextField.setText(newValue.replaceAll("[^\\d]", ""));
        });
        stampHeightTextField.setText((String) getProperty("stampHeight"));
        stampHeightTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*"))
                stampHeightTextField.setText(newValue.replaceAll("[^\\d]", ""));
        });
        paperWidthTextField.setText((String) getProperty("paperWidth"));
        paperWidthTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*"))
                stampHeightTextField.setText(newValue.replaceAll("[^\\d]", ""));
        });
        paperHeightTextField.setText((String) getProperty("paperHeight"));
        paperHeightTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*"))
                stampHeightTextField.setText(newValue.replaceAll("[^\\d]", ""));
        });
        numberOfStampPerLineTextField.setText((String) getProperty("numberOfStamp"));
        numberOfStampPerLineTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                numberOfStampPerLineTextField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        deleteButton.setOnAction(event -> new RemoveEntryFromTable(table).execute());
        addSingleInstance.setOnAction(event -> new AddSingleInstanceToTable(table).execute());
        addFromExcel.setOnAction(event -> {
            OpenFileChooser fileChooser = new OpenFileChooser(this.getWindow());
            fileChooser.execute();
            new AddFromExcelToTable(table, fileChooser.getPath());
        });
        printButton.setOnAction(event -> {
            if (stampWidthTextField.getText().isEmpty()) {
                MsgPane.getInstance().setDisplayMsgLabel("Độ rộng của tem không được bỏ trống");
                new OpenPaneMessage(MsgPane.getInstance(),
                        MessagePaneConcrete.getInstance(),
                        getWindow()).execute();
                return;
            }
            if (stampHeightTextField.getText().isEmpty()) {
                MsgPane.getInstance().setDisplayMsgLabel("Độ cao của tem không được bỏ trống");
                new OpenPaneMessage(MsgPane.getInstance(),
                        MessagePaneConcrete.getInstance(),
                        getWindow()).execute();
                return;
            }
            if (paperWidthTextField.getText().isEmpty()) {
                MsgPane.getInstance().setDisplayMsgLabel("Độ rộng của giấy không được bỏ trống");
                new OpenPaneMessage(MsgPane.getInstance(),
                        MessagePaneConcrete.getInstance(),
                        getWindow()).execute();
                return;
            }
            if (paperHeightTextField.getText().isEmpty()) {
                MsgPane.getInstance().setDisplayMsgLabel("Độ cao của giấy không được bỏ trống");
                new OpenPaneMessage(MsgPane.getInstance(),
                        MessagePaneConcrete.getInstance(),
                        getWindow()).execute();
                return;
            }
            if (numberOfStampPerLineTextField.getText().isEmpty()) {
                MsgPane.getInstance().setDisplayMsgLabel("Số tem ở trên một hàng không được bỏ trống");
                new OpenPaneMessage(MsgPane.getInstance(),
                        MessagePaneConcrete.getInstance(),
                        getWindow()).execute();
                return;
            }
            if (table.getItems().size() == 0) {
                MsgPane.getInstance().setDisplayMsgLabel("Chưa có tem nào được liệt kê");
                new OpenPaneMessage(MsgPane.getInstance(),
                        MessagePaneConcrete.getInstance(),
                        getWindow()).execute();
                return;
            }
            new Thread(() -> {
                setProperty("stampWidth", stampWidthTextField.getText());
                setProperty("stampHeight", stampHeightTextField.getText());
                setProperty("paperWidth", paperWidthTextField.getText());
                setProperty("paperHeight", paperHeightTextField.getText());
                setProperty("numberOfStamp", numberOfStampPerLineTextField.getText());
                new SaveProperties().execute();
                new Print().execute();
            }).start();
        });

        addMenuButton.getItems().addAll(addSingleInstance,
                addFromExcel);
        printButtonWrapper.getChildren().addAll(printButtonRegion,
                printButton);
        labelAndButtonForTableWrapper.getChildren().addAll(tableLabel,
                labelAndButtonForTableRegion,
                deleteButton,
                addMenuButton);
        mainPane.getChildren().addAll(labelAndButtonForTableWrapper,
                table,
                paperSetupLabel,
                settingGrid,
                printButtonWrapper);

        mainPane.getStyleClass().addAll("body", "spacing");
        settingGrid.getStyleClass().add("grid");
        labelAndButtonForTableWrapper.getStyleClass().add("spacing");
        deleteButton.getStyleClass().add("button");
    }

    public TableView<BarcodeInstance> getTable() {
        return table;
    }
}

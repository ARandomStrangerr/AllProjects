package ui;

import bin.command.AddFromExcelToTable;
import bin.command.AddSingleInstanceToTable;
import bin.command.OpenFileChooser;
import bin.command.RemoveEntryFromTable;
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
            marginWidthLabel,
            numberOfStampPerLineLabel;
    private final TextField stampWidthTextField,
            stampHeightTextField,
            marginWidthTextField,
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
        marginWidthLabel = new Label("Độ rộng của lề");
        numberOfStampPerLineLabel = new Label("Số tem mỗi hàng");
        stampWidthTextField = new TextField();
        stampHeightTextField = new TextField();
        marginWidthTextField = new TextField();
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
        HBox.setHgrow(labelAndButtonForTableRegion, Priority.ALWAYS);
        HBox.setHgrow(printButtonRegion, Priority.ALWAYS);

        codeCol.setCellValueFactory(new PropertyValueFactory<>("code"));
        codeCol.setCellFactory(TextFieldTableCell.forTableColumn());
        codeCol.setOnEditCommit(event -> table.getSelectionModel().getSelectedItem().setCode(event.getNewValue()));
        labelCol.setCellValueFactory(new PropertyValueFactory<>("label"));
        labelCol.setCellFactory(TextFieldTableCell.forTableColumn());
        labelCol.setOnEditCommit(event -> table.getSelectionModel().getSelectedItem().setCode(event.getNewValue()));
        table.getColumns().addAll(codeCol, labelCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        settingGrid.add(stampHeightLabel, 0, 0);
        settingGrid.add(stampHeightTextField, 1, 0);
        settingGrid.add(stampWidthLabel, 2, 0);
        settingGrid.add(stampWidthTextField, 3, 0);
        settingGrid.add(marginWidthLabel, 0, 1);
        settingGrid.add(marginWidthTextField, 1, 1);
        settingGrid.add(numberOfStampPerLineLabel, 2, 1);
        settingGrid.add(numberOfStampPerLineTextField, 3, 1);

        deleteButton.setOnAction(event -> new RemoveEntryFromTable(table).execute());
        addSingleInstance.setOnAction(event -> new AddSingleInstanceToTable(table).execute());
        addFromExcel.setOnAction(event -> {
            OpenFileChooser fileChooser = new OpenFileChooser(this.getWindow());
            fileChooser.execute();
            new AddFromExcelToTable(table, fileChooser.getPath());
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

        mainPane.getStyleClass().add("body");
        settingGrid.getStyleClass().add("grid");
    }
}

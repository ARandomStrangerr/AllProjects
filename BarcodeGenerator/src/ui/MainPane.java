package ui;

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
    private final Button deleteButton;
    private final MenuButton addMenuButton;
    private final MenuItem addSingleInstance,
            addFromExcel;
    public final TableView<BarcodeInstance> table;
    public final TableColumn<BarcodeInstance, String> codeCol,
            labelCol;
    private final Region labelAndButtonForTableRegion;
    private final GridPane settingGrid;
    public final VBox labelAndButtonForTableWrapper;
    private final HBox mainPane;

    private MainPane() {
        super(new HBox());
        mainPane = (HBox) super.getMainPane();
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
        addMenuButton = new MenuButton("+");
        addSingleInstance = new MenuItem("Thêm");
        addFromExcel = new MenuItem("Thêm từ Excel");
        table = new TableView<>();
        codeCol = new TableColumn<>();
        labelCol = new TableColumn<>();
        settingGrid = new GridPane();
        labelAndButtonForTableRegion = new Region();
        labelAndButtonForTableWrapper = new VBox();
        setup();
    }

    @Override
    protected void setup() {
        HBox.setHgrow(labelAndButtonForTableRegion, Priority.ALWAYS);

        codeCol.setCellValueFactory(new PropertyValueFactory<>("code"));
        codeCol.setCellFactory(TextFieldTableCell.forTableColumn());
        labelCol.setCellValueFactory(new PropertyValueFactory<>("label"));
        labelCol.setCellFactory(TextFieldTableCell.forTableColumn());

        settingGrid.add(stampHeightLabel, 0, 0);
        settingGrid.add(marginWidthLabel, 0, 1);


        addMenuButton.getItems().addAll(addSingleInstance,
                addFromExcel);
        labelAndButtonForTableWrapper.getChildren().addAll(tableLabel,
                labelAndButtonForTableRegion,
                deleteButton,
                addMenuButton);
        mainPane.getChildren().addAll(labelAndButtonForTableWrapper,
                table,
                paperSetupLabel);
    }
}

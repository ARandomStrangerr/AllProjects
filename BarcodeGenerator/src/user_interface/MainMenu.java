package user_interface;

import bin.AddInstanceCommand;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public final class MainMenu extends PrimePane {
    private final TableColumn<TableData, String> codeCol = new TableColumn<>("Mã số"),
            nameCol = new TableColumn<>("Tên sản phẩm");
    private final TableView<TableData> table = new TableView<>();
    private final Button helpButton = new Button("?"),
            deleteButton = new Button("Xóa"),
            generateButton = new Button("Khởi tạo");
    private final MenuItem addSinglePersonOption = new MenuItem("Thêm một người"),
            addFromFileOption = new MenuItem("Thêm từ file");
    private final MenuButton addOptions = new MenuButton("Thêm");
    private final Label tableInfoLabel = new Label("Danh sách Khởi tạo");
    private final Region region1 = new Region(),
            region2 = new Region();

    private final HBox box1 = new HBox(tableInfoLabel, region1, deleteButton, addOptions),
            box2 = new HBox(helpButton, region2, generateButton);
    private final VBox primePane = (VBox) super.getPrimePane();

    public MainMenu(Stage ownerScene) {
        super(new VBox(), ownerScene);
        construct();
    }

    private void construct() {
        HBox.setHgrow(region1, Priority.ALWAYS);
        HBox.setHgrow(region2, Priority.ALWAYS);

        addOptions.getItems().addAll(addSinglePersonOption, addFromFileOption);

        codeCol.setCellValueFactory(new PropertyValueFactory<>("code"));
        codeCol.setCellFactory(TextFieldTableCell.forTableColumn());
        codeCol.setOnEditCommit(event -> table.getSelectionModel().getSelectedItem().setCode(event.getNewValue()));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        nameCol.setOnEditCommit(event -> table.getSelectionModel().getSelectedItem().setName(event.getNewValue()));

        table.setEditable(true);
        table.getColumns().add(codeCol);
        table.getColumns().add(nameCol);

        deleteButton.setOnAction(actionEvent -> table.getItems().remove(table.getSelectionModel().getSelectedItem()));
        addSinglePersonOption.setOnAction(actionEvent -> table.getItems().add(new TableData("", "")));
        addFromFileOption.setOnAction(
                actionEvent -> super.execute(new AddInstanceCommand(table.getItems(), ownerStage))
        );
        generateButton.setOnAction(actionEvent -> new Finalize(super.ownerStage, table.getItems()));

        primePane.getStylesheets().add("user_interface/stylesheet/utilities.css");
        primePane.getStyleClass().add("pane");
        primePane.setPrefWidth(600);
        primePane.getChildren().addAll(box1, table, box2);
    }
}
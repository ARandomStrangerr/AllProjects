package user_interface.element;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import user_interface.PaneAbstract;
import user_interface.table_content.Item;

public final class PaneInvoiceDetail extends PaneAbstract {
    //singleton
    static private PaneInvoiceDetail cache;

    public static PaneInvoiceDetail getInstance() {
        if (cache == null) cache = new PaneInvoiceDetail();
        return cache;
    }
    //actual class
    private final TableView<Item> table;
    private final TableColumn<Item, String> itemNameCol;
    private final TableColumn<Item, Integer> quantityCol;
    private final TableColumn<Item, Long> priceCol,
            totalCol,
            taxAmountCol;
    private final VBox mainPane;

    private PaneInvoiceDetail() {
        super(new VBox());
        mainPane = (VBox) super.getMainPane();
        table = new TableView<>();
        itemNameCol = new TableColumn<>("Nội dung");
        quantityCol = new TableColumn<>("Số lượng");
        priceCol = new TableColumn<>("Giá thành");
        totalCol = new TableColumn<>("Thành tiền");
        taxAmountCol = new TableColumn<>("Tiền thuế");
        setup();
    }

    @Override
    protected void setup() {
        itemNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        totalCol.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        taxAmountCol.setCellValueFactory(new PropertyValueFactory<>("taxAmount"));
        table.getColumns().add(itemNameCol);
        table.getColumns().add(quantityCol);
        table.getColumns().add(priceCol);
        table.getColumns().add(totalCol);
        table.getColumns().add(taxAmountCol);
        mainPane.getChildren().addAll(PaneInvoiceInfo.getInstance().getMainPane(),
                table);
        mainPane.getStyleClass().addAll("spacing","padding");
    }

    public void setTableItems(ObservableList<Item> items){
        table.setItems(items);
    }
}

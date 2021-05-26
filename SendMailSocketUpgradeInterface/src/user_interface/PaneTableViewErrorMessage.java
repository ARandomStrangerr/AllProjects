package user_interface;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import user_interface.table_item.ReceiverInfo;

import java.util.List;

public class PaneTableViewErrorMessage extends PaneAbstract {
    private static PaneTableViewErrorMessage cache;

    public static PaneTableViewErrorMessage getInstance() {
        if (cache == null) cache = new PaneTableViewErrorMessage();
        return cache;
    }
    private final TableView<ReceiverInfo> tableView;
    private final TableColumn<ReceiverInfo, String> emailCol, errorCol;
    private final VBox mainPane;

    private PaneTableViewErrorMessage() {
        super(new VBox());
        tableView = new TableView<>();
        emailCol = new TableColumn<>("Địa chỉ hộp thư");
        errorCol = new TableColumn<>("Lỗi");
        mainPane = (VBox) super.getMainPane();
        setup();
    }

    @Override
    protected void setup() {
        mainPane.getStylesheets().add("stylesheet/universal.css");
        mainPane.getChildren().add(tableView);
        tableView.getColumns().add(emailCol);
        tableView.getColumns().add(errorCol);
    }

    public void setItems(List<ReceiverInfo> items){
        this.tableView.setItems((ObservableList<ReceiverInfo>) items);
    }

    public List<ReceiverInfo> getItems(){
        return this.tableView.getItems();
    }
}

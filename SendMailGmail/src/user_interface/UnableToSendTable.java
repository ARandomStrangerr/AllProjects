package user_interface;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import user_interface.table_datatype.UnableToSend;

public class UnableToSendTable extends SuperWindow {
    private final ObservableList<UnableToSend> errorList;
    private final TableView<UnableToSend> table = new TableView<>();
    private final TableColumn<UnableToSend, String> emailCol = new TableColumn<>("Địa chỉ"),
            errorCol = new TableColumn<>("Lỗi");
    private final HBox primePane = new HBox(table);


    public UnableToSendTable(Stage stage, String windowName, ObservableList<UnableToSend> errorList) {
        super(stage, windowName);
        this.errorList = errorList;
    }

    @Override
    protected Pane setupPane() {
        table.getColumns().add(emailCol);
        table.getColumns().add(errorCol);
        primePane.setStyle("-fx-padding: 25");
        return primePane;
    }
}
package user_interface;

import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import user_interface.table_item.ReceiverInfo;

public class ErrorPane extends PaneAbstract {
    private final TableView<ReceiverInfo> table;

    public ErrorPane() {
        super(new VBox());
        table = new TableView<>();
        setting();
    }

    @Override
    protected void setting() {
        //label
        Label title = new Label("Người dùng không thể gửi");
        //columns
        TableColumn<ReceiverInfo, String> emailCol = new TableColumn<>(),
                errorCol = new TableColumn<>();
        emailCol.setCellValueFactory(new PropertyValueFactory<>("emailAddress"));
        errorCol.setCellValueFactory(new PropertyValueFactory<>("attachmentFileName"));
        //table
        table.getColumns().add(emailCol);
        table.getColumns().add(errorCol);
        //pane
        VBox mainPane = (VBox) super.getPane();
        mainPane.getChildren().addAll(title, table);
    }

    public void setTableItems(ObservableList<ReceiverInfo> items){
        table.setItems(items);
    }
}
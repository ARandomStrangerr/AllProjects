package user_interface;

import bin.Print2;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;


public final class Finalize extends PrimePane {
    private final Label displayCodeOptionLabel = new Label("Hiển thị thông tin mã vạch"),
            sampleBarcodeLabel = new Label(),
            barcodeXLabel = new Label("Độ rộng tem"),
            barcodeYLabel = new Label("Độ cao tem"),
            marginXLabel = new Label("Độ rộng lề"),
            marginYLabel = new Label("Độ cao lề"),
            numberOfBarcodePerLineLabel = new Label("Số lượng mã vạch mỗi hàng"),
            barcodeTypeLabel = new Label("Loại mã vạch");
    private final Button helpButton = new Button("?"),
            printButton = new Button("In");
    private final TextField barcodeXTextField = new TextField(),
            barcodeYTextField = new TextField(),
            marginXTextField = new TextField(),
            marginYTextField = new TextField(),
            numberOfBarcodePerLineTextField = new TextField();
    private final Region region = new Region();

    private final CheckBox displayCodeOptionCheckBox = new CheckBox();
    private final ChoiceBox<String> barcodeTypeChoiceBox = new ChoiceBox<>(
            FXCollections.observableArrayList("128", "128A", "128B", "EAN13", "EAN128", "2 of 7"));
    private final List<TableData> barcodeList;

    private final HBox box1 = new HBox(displayCodeOptionCheckBox, displayCodeOptionLabel),
            box3 = new HBox(barcodeTypeLabel, barcodeTypeChoiceBox),
            box4 = new HBox(helpButton, region, printButton);
    private final GridPane primePane = (GridPane) super.getPrimePane();
    private final Stage stage = new Stage();

    public Finalize(Stage ownerStage, List<TableData> barcodeList) {
        super(new GridPane(), ownerStage);
        this.barcodeList = barcodeList;
        construct();
    }

    private void construct() {
        HBox.setHgrow(region, Priority.ALWAYS);
        displayCodeOptionCheckBox.setSelected(true);
        printButton.setOnAction(actionEvent -> sendActionAndValidation());

        numberOfBarcodePerLineTextField.setPrefWidth(80);
        barcodeXTextField.setPrefWidth(80);
        barcodeYTextField.setPrefWidth(80);
        marginXTextField.setPrefWidth(80);
        marginYTextField.setPrefWidth(80);

        primePane.add(box1, 0, 0, 4, 1);
        primePane.add(barcodeXLabel, 0, 1);
        primePane.add(barcodeXTextField, 1, 1);
        primePane.add(barcodeYLabel, 2, 1);
        primePane.add(barcodeYTextField, 3, 1);
        primePane.add(marginXLabel, 0, 2);
        primePane.add(marginXTextField, 1, 2);
        primePane.add(marginYLabel, 2, 2);
        primePane.add(marginYTextField, 3, 2);
        primePane.add(numberOfBarcodePerLineLabel, 0, 3, 3, 1);
        primePane.add(numberOfBarcodePerLineTextField, 3, 3);
        primePane.add(box3, 0, 4, 4, 1);
        primePane.add(sampleBarcodeLabel, 0, 5, 4, 1);
        primePane.add(box4, 0, 6, 4, 1);
        primePane.getStylesheets().add("user_interface/stylesheet/utilities.css");
        primePane.getStyleClass().add("grid-pane");

        stage.setScene(new Scene(primePane));
        stage.initOwner(ownerStage);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
    }

    private void sendActionAndValidation() {
        try {
            double paperWidth = Double.parseDouble(barcodeXTextField.getText()),
                    paperHeight = Double.parseDouble(barcodeYTextField.getText()),
                    marginWidth = Double.parseDouble(marginXTextField.getText()),
                    marginHeight = Double.parseDouble(marginYTextField.getText());
            String barcodeChoice = barcodeTypeChoiceBox.getValue();
            int barcodePerPage = Integer.parseInt(numberOfBarcodePerLineTextField.getText());
            if (barcodeChoice == null || barcodeList.size() == 0) {
                throw new RuntimeException();
            } else {
                new Print2(this.stage, barcodeList, barcodeChoice, paperWidth, paperHeight, marginWidth, marginHeight,
                        barcodePerPage, displayCodeOptionCheckBox.isSelected()).execute();
            }
        } catch (NumberFormatException e) {
            new ErrorPane("Thông tin ở trường số thiếu hoặc sai", ownerStage);
        } catch (RuntimeException e) {
            new ErrorPane("Thiếu thôn tin chưa được điền đầy đủ", ownerStage);
        }
    }
}

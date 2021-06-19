package user_interface.element;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import user_interface.PaneAbstract;

public final class PaneInvoiceInfo extends PaneAbstract {
    //singleton
    static private PaneInvoiceInfo cache;

    public static PaneInvoiceInfo getInstance() {
        if (cache == null) cache = new PaneInvoiceInfo();
        return cache;
    }

    //actual class
    private final Label titleLabel,
            invoiceTypeLabel,
            templateCodeLabel,
            invoiceSeriesLabel;
    private final ChoiceBox<String> invoiceTypeChoiceBox;
    private final ObservableList<String> invoiceTypeChoice;
    private final TextField invoiceSeriesTextField,
            templateCodeTextField;
    private final HBox wrapperFirstLine;
    private final VBox mainPane;

    private PaneInvoiceInfo() {
        super(new VBox());
        titleLabel = new Label("Thông tin hoá đơn");
        invoiceTypeLabel = new Label("Loại hoá đơn");
        templateCodeLabel = new Label("Mẫu hoá đơn");
        invoiceSeriesLabel = new Label("Kí hiệu");
        invoiceTypeChoiceBox = new ChoiceBox<>();
        invoiceTypeChoice = FXCollections.observableArrayList();
        invoiceSeriesTextField = new TextField();
        templateCodeTextField = new TextField();
        wrapperFirstLine = new HBox();
        mainPane = (VBox) super.getMainPane();
        setup();
    }

    @Override
    protected void setup() {
        HBox.setHgrow(invoiceSeriesTextField, Priority.ALWAYS);
        HBox.setHgrow(templateCodeTextField, Priority.ALWAYS);
        invoiceTypeChoice.addAll("01GTKT",
                "02GTTT",
                "07KPTQ",
                "03XKNB",
                "04HGDL",
                "01BLP");
        invoiceTypeChoiceBox.setItems(invoiceTypeChoice);
        wrapperFirstLine.getChildren().addAll(invoiceSeriesLabel,
                invoiceSeriesTextField,
                invoiceTypeLabel,
                invoiceTypeChoiceBox,
                templateCodeLabel,
                templateCodeTextField);
        mainPane.getChildren().addAll(titleLabel,
                wrapperFirstLine);
        wrapperFirstLine.getStyleClass().addAll("alignment","spacing");
        mainPane.getStyleClass().add("spacing");
    }

    public String getInvoiceSeries(){
        return invoiceSeriesTextField.getText();
    }

    public String getTemplateCode(){
        return templateCodeTextField.getText();
    }

    public String getInvoiceType(){
        return invoiceTypeChoiceBox.getValue();
    }
}

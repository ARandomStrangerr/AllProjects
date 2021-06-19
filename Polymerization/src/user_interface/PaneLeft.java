package user_interface;

import bin.mediator.PaneController;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;

public class PaneLeft extends PaneAbstract {
    //singleton
    private static PaneLeft cache;

    public static PaneLeft getInstance() {
        if (cache == null) cache = new PaneLeft();
        return cache;
    }

    //actual class
    private final VBox mainPane;
    //e-invoice
    private final TitledPane invoiceTitledPane;
    private final VBox invoiceBodyPane,
            createSingleInvoiceWrapper,
            createMultipleInvoiceWrapper,
            searchInvoiceWrapper;
    private final Label createSingleInvoiceLabel,
            createMultipleInvoice,
            searchInvoice;
    //mail
    private final TitledPane mailTitledPane;
    private final VBox mailBodyPane,
            mailWrapper;
    private final Label mailLabel;

    private PaneLeft() {
        super(new VBox());
        mainPane = (VBox) super.getMainPane();
        //e-invoice
        invoiceTitledPane = new TitledPane();
        invoiceBodyPane = new VBox();
        createSingleInvoiceWrapper = new VBox();
        createMultipleInvoiceWrapper = new VBox();
        searchInvoiceWrapper = new VBox();
        createSingleInvoiceLabel = new Label("Tạo hoá đơn");
        createMultipleInvoice = new Label("Tạo nhiều hoá đơn từ excel");
        searchInvoice = new Label("Tra cứu hoá đơn");
        //mail
        mailTitledPane = new TitledPane();
        mailWrapper = new VBox();
        mailBodyPane = new VBox();
        mailLabel = new Label("Gởi thư điện tử");
        setup();
    }

    @Override
    protected void setup() {
        mainPane.setPrefWidth(250);
        //e-invoice
        createSingleInvoiceWrapper.getChildren().add(createSingleInvoiceLabel);
        createMultipleInvoiceWrapper.getChildren().add(createMultipleInvoice);
        createMultipleInvoiceWrapper.setOnMouseClicked(event -> PaneController.getInstance().showInvoiceFromExcel());
        searchInvoiceWrapper.getChildren().add(searchInvoice);
        invoiceBodyPane.getChildren().addAll(createSingleInvoiceWrapper,
                createMultipleInvoiceWrapper,
                searchInvoiceWrapper);
        invoiceTitledPane.setText("Hoá đơn điện tử");
        invoiceTitledPane.setContent(invoiceBodyPane);
        mainPane.getChildren().add(invoiceTitledPane);
        //mail
        mailWrapper.getChildren().add(mailLabel);
        mailWrapper.setOnMouseClicked(event -> PaneController.getInstance().showSendMailPane());
        mailBodyPane.getChildren().add(mailWrapper);
        mailTitledPane.setText("Hộp thư điện tử");
        mailTitledPane.setContent(mailBodyPane);
        mainPane.getChildren().add(mailTitledPane);
    }
}

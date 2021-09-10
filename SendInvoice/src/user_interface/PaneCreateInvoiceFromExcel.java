package user_interface;

import bin.command.*;
import bin.thread.CreateInvoice;
import bin.thread.ReadInvoiceFromExcel;
import bin.thread.ReadMailFromExcel;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import user_interface.element.PaneSellerInfo;
import user_interface.table_content.Invoice;
import user_interface.table_content.Mail;

public final class PaneCreateInvoiceFromExcel extends PaneAbstract {
    //singleton
    static private PaneCreateInvoiceFromExcel cache;

    public static PaneCreateInvoiceFromExcel getInstance() {
        if (cache == null) cache = new PaneCreateInvoiceFromExcel();
        return cache;
    }

    //actual class
    private final TextField folderTextField;
    private final Label invoiceTableTitleLabel,
            mailTableTitleLabel,
            folderTitleLabel;
    private final Button addFromExcelToInvoiceTableButton,
            removeEntryFromInvoiceTableButton,
            addFromExcelToMailTableButton,
            removeEntryFromMailTableButton,
            onlyCreateInvoiceButton,
            createInvoiceAndSendMailButton;
    private final TableView<Invoice> invoiceTable;
    private final TableView<Mail> mailTable;
    private final TableColumn<Invoice, String> invoiceTypeCol,
            templateCodeCol,
            invoiceSeriesCol,
            personNameCol,
            personCodeCol,
            collectiveNameCol,
            collectiveTaxCodeCol,
            addressCol,
            paymentMethodCol;
    private final TableColumn<Invoice, Integer> taxRateCol;
    private final TableColumn<Mail, String> idCol,
            mailCol;
    private final Region region1,
            region2,
            region3,
            region4;
    private final HBox wrapperFirstLine,
            wrapperSecondLine,
            wrapperThirdLine,
            wrapperFourthLine,
            wrapperFifthLine;
    private final ScrollPane innerMainPane;
    private final VBox mainPane;

    private PaneCreateInvoiceFromExcel() {
        super(new VBox());
        folderTextField = new TextField();
        folderTitleLabel = new Label("Đường dẫn lưu file");
        invoiceTableTitleLabel = new Label("Bảng thông tin hoá đơn");
        mailTableTitleLabel = new Label("Danh sách hộp thư");
        addFromExcelToInvoiceTableButton = new Button("+");
        addFromExcelToMailTableButton = new Button("+");
        removeEntryFromInvoiceTableButton = new Button("-");
        removeEntryFromMailTableButton = new Button("-");
        onlyCreateInvoiceButton = new Button("Tạo hoá đơn");
        createInvoiceAndSendMailButton = new Button("Tạo hoá đơn và gởi");
        invoiceTable = new TableView<>();
        mailTable = new TableView<>();
        invoiceTypeCol = new TableColumn<>("Loại hoá đơn");
        templateCodeCol = new TableColumn<>("Mẫu hoá đơn");
        invoiceSeriesCol = new TableColumn<>("Kí hiệu");
        personNameCol = new TableColumn<>("Tên người mua");
        personCodeCol = new TableColumn<>("Mã người mua");
        collectiveNameCol = new TableColumn<>("Tên đơn vị");
        collectiveTaxCodeCol = new TableColumn<>("Mã số thuế");
        addressCol = new TableColumn<>("Địa chỉ");
        paymentMethodCol = new TableColumn<>("Phương thức thanh toán");
        taxRateCol = new TableColumn<>("Thuế");
        idCol = new TableColumn<>("Mã người mua");
        mailCol = new TableColumn<>("Địa chỉ hộp thư");
        region1 = new Region();
        region2 = new Region();
        region3 = new Region();
        region4 = new Region();
        wrapperFirstLine = new HBox();
        wrapperSecondLine = new HBox();
        wrapperThirdLine = new HBox();
        wrapperFourthLine = new HBox();
        wrapperFifthLine = new HBox();
        innerMainPane = new ScrollPane();
        mainPane = new VBox();
        setup();
    }

    @Override
    protected void setup() {
        //columns
        invoiceTypeCol.setCellValueFactory(new PropertyValueFactory<>("invoiceType"));
        templateCodeCol.setCellValueFactory(new PropertyValueFactory<>("templateCode"));
        invoiceSeriesCol.setCellValueFactory(new PropertyValueFactory<>("invoiceSeries"));
        personNameCol.setCellValueFactory(new PropertyValueFactory<>("personName"));
        personCodeCol.setCellValueFactory(new PropertyValueFactory<>("personCode"));
        collectiveNameCol.setCellValueFactory(new PropertyValueFactory<>("collectiveName"));
        collectiveTaxCodeCol.setCellValueFactory(new PropertyValueFactory<>("collectiveTaxCode"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        paymentMethodCol.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
        taxRateCol.setCellValueFactory(new PropertyValueFactory<>("taxRate"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        mailCol.setCellValueFactory(new PropertyValueFactory<>("mail"));
        //tables
        invoiceTable.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)
                    && event.getClickCount() == 2) {
                new OpenIndividualItem(invoiceTable.getSelectionModel().getSelectedItem()).execute();
            }
        });
        invoiceTable.getColumns().add(invoiceTypeCol);
        invoiceTable.getColumns().add(templateCodeCol);
        invoiceTable.getColumns().add(invoiceSeriesCol);
        invoiceTable.getColumns().add(personNameCol);
        invoiceTable.getColumns().add(personCodeCol);
        invoiceTable.getColumns().add(collectiveNameCol);
        invoiceTable.getColumns().add(collectiveTaxCodeCol);
        invoiceTable.getColumns().add(addressCol);
        invoiceTable.getColumns().add(paymentMethodCol);
        invoiceTable.getColumns().add(taxRateCol);
        mailTable.getColumns().add(idCol);
        mailTable.getColumns().add(mailCol);
        //regions
        HBox.setHgrow(region1,
                Priority.ALWAYS);
        HBox.setHgrow(region2,
                Priority.ALWAYS);
        HBox.setHgrow(region3,
                Priority.ALWAYS);
        HBox.setHgrow(region4,
                Priority.ALWAYS);
        //buttons
        addFromExcelToInvoiceTableButton.getStyleClass().add("green-button");
        addFromExcelToInvoiceTableButton.setOnAction(event ->
                new ChooseFileThenRunThread(this.getWindow(),
                        new ReadInvoiceFromExcel()).execute());
        removeEntryFromInvoiceTableButton.getStyleClass().add("red-button");
        removeEntryFromInvoiceTableButton.setOnAction(event -> new RemoveEntryFromTable(invoiceTable).execute());
        addFromExcelToMailTableButton.getStyleClass().add("green-button");
        addFromExcelToMailTableButton.setOnAction(even ->
                new ChooseFileThenRunThread(this.getWindow(),
                        ReadMailFromExcel.getInstance()).execute());
        removeEntryFromMailTableButton.getStyleClass().add("red-button");
        removeEntryFromMailTableButton.setOnAction(event -> new RemoveEntryFromTable(mailTable).execute());
        onlyCreateInvoiceButton.setOnAction(event -> new OpenLoginWindow().execute());
        //panes
        wrapperFirstLine.getStyleClass().addAll("alignment", "spacing");
        wrapperFirstLine.getChildren().addAll(invoiceTableTitleLabel,
                region1,
                addFromExcelToInvoiceTableButton,
                removeEntryFromInvoiceTableButton);
        wrapperSecondLine.getChildren().addAll(region2,
                onlyCreateInvoiceButton);
        wrapperThirdLine.getChildren().addAll(mailTableTitleLabel,
                region3,
                addFromExcelToMailTableButton,
                removeEntryFromMailTableButton);
        wrapperFourthLine.getChildren().addAll(region4,
                createInvoiceAndSendMailButton);
        wrapperFifthLine.getChildren().addAll(folderTitleLabel,
                folderTextField);
        mainPane.getChildren().addAll(PaneSellerInfo.getInstance().getMainPane(),
                wrapperFirstLine,
                invoiceTable,
                wrapperFifthLine,
                wrapperSecondLine,
                wrapperThirdLine,
                mailTable,
                wrapperFourthLine);
        mainPane.getStylesheets().add("stylesheet/universal.css");
        mainPane.getStyleClass().addAll("background", "spacing");
        innerMainPane.setContent(mainPane);
        innerMainPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        super.getMainPane().getChildren().add(innerMainPane);
    }

    public ObservableList<Invoice> getInvoiceTableItems() {
        return invoiceTable.getItems();
    }

    public ObservableList<Mail> getMailTableItems() {
        return mailTable.getItems();
    }

    public String getFolder(){
        return folderTextField.getText();
    }
}
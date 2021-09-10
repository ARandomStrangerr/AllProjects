package bin.command;

import command.CommandInterface;
import user_interface.PaneCreateInvoiceFromExcel;
import user_interface.element.PaneInvoiceDetail;
import user_interface.PaneYesNoConcrete;
import user_interface.WindowPopup;
import user_interface.element.PaneInvoiceInfo;
import user_interface.table_content.Invoice;

public final class OpenIndividualItem implements CommandInterface {
    private final Invoice selectedItem;

    public OpenIndividualItem(Invoice selectedItem) {
        this.selectedItem = selectedItem;
    }

    @Override
    public void execute() {
        PaneYesNoConcrete paneYesNo = PaneYesNoConcrete.getInstance();
        PaneInvoiceDetail invoiceDetailPane = PaneInvoiceDetail.getInstance();
        if (paneYesNo.getWindow() == null) {
            WindowPopup invoiceDetailWindow = new WindowPopup();
            invoiceDetailWindow.setPane(paneYesNo);
            invoiceDetailWindow.setOwnerStage(PaneCreateInvoiceFromExcel.getInstance().getWindow().getCurrentStage());
            invoiceDetailWindow.setup();
            paneYesNo.setWindow(invoiceDetailWindow);
        }
        PaneInvoiceInfo.getInstance().setInvoiceSeriesTextField(selectedItem.getInvoiceSeries());
        PaneInvoiceInfo.getInstance().setInvoiceTypeChoiceBox(selectedItem.getInvoiceType());
        PaneInvoiceInfo.getInstance().setTemplateCodeTextField(selectedItem.getTemplateCode());
        invoiceDetailPane.setTableItems(selectedItem.getItems());
        paneYesNo.setUpperPane(invoiceDetailPane);
        paneYesNo.getWindow().openCurrentStage();
    }
}

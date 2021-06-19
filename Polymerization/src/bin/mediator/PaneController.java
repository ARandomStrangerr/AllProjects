package bin.mediator;

import user_interface.PaneCreateInvoiceFromExcel;
import user_interface.PaneMain;
import user_interface.concrete_pane.PaneLeftRight;

public class PaneController {
    //singleton
    private static PaneController cache;

    public static PaneController getInstance() {
        if (cache == null) cache = new PaneController();
        return cache;
    }

    //actual class
    private final PaneLeftRight paneLeftRight;
    private PaneController(){
        paneLeftRight = PaneLeftRight.getInstance();
    }

    public void showSendMailPane(){
        PaneMain.getInstance().setWindow(PaneLeftRight.getInstance().getWindow());
        paneLeftRight.setRightPane(PaneMain.getInstance().getMainPane());
    }

    public void showInvoiceFromExcel(){
        PaneCreateInvoiceFromExcel.getInstance().setWindow(PaneLeftRight.getInstance().getWindow());
        paneLeftRight.setRightPane(PaneCreateInvoiceFromExcel.getInstance().getMainPane());
    }
}

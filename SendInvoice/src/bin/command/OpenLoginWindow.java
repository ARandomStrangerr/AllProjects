package bin.command;

import bin.thread.CreateInvoice;
import command.CommandInterface;
import user_interface.PaneCreateInvoiceFromExcel;
import user_interface.PaneYesNoConcrete;
import user_interface.WindowPopup;
import user_interface.element.PaneLogin;

public final class OpenLoginWindow implements CommandInterface {
    @Override
    public void execute() {
        PaneYesNoConcrete paneYesNo = PaneYesNoConcrete.getInstance();
        paneYesNo.setUpperPane(PaneLogin.getInstance());
        if(paneYesNo.getWindow() == null){
            WindowPopup window = new WindowPopup();
            window.setPane(paneYesNo);
            window.setup();
            paneYesNo.setWindow(window);
        }
        paneYesNo.setYesButtonAction(() -> {
            paneYesNo.getWindow().closeCurrentStage();
            String folder = PaneCreateInvoiceFromExcel.getInstance().getFolder();
            new RunThread(new CreateInvoice(folder, folder)).execute();
        });
        paneYesNo.setNoButtonAction(() -> paneYesNo.getWindow().closeCurrentStage());
        paneYesNo.getWindow().openCurrentStage();
    }
}

package bin.command;

import command.CommandInterface;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import user_interface.PaneMain;

public final class SelectAttachmentFolderButton implements CommandInterface {
    private final TextField displayTextField;

    public SelectAttachmentFolderButton(TextField displayTextField) {
        this.displayTextField = displayTextField;
    }

    @Override
    public void execute(){
        try{
            displayTextField.setText(new DirectoryChooser().showDialog(PaneMain.getInstance().getWindow().getCurrentStage()).getAbsolutePath());
        }catch (NullPointerException ignore){
            System.err.println("NO FOLDER HAS BEEN PICKED");
        }
    }
}

package user_interface.command;

import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class OpenAttachmentFolderChooser implements Command {
    private TextField folderTextField;

    public OpenAttachmentFolderChooser(TextField folderTextField) {
        this.folderTextField = folderTextField;
    }

    @Override
    public void execute() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        folderTextField.setText(directoryChooser.showDialog(new Stage()).getAbsolutePath() + "/");
    }
}

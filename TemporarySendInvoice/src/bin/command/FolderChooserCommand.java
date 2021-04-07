package bin.command;

import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public final class FolderChooserCommand implements Command{

    private final TextField textField;

    public FolderChooserCommand(TextField textField) {
        this.textField = textField;
    }

    @Override
    public void execute() {
        textField.setText(new DirectoryChooser().showDialog(new Stage()).getAbsolutePath() + "/");
    }
}

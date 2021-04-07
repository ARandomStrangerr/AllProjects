package bin.command;

import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public final class FileChooserCommand implements Command{
    private final TextField pathTextField;

    public FileChooserCommand(TextField pathTextField) {
        this.pathTextField = pathTextField;
    }

    @Override
    public void execute() {
        FileChooser fileChooser = new FileChooser();
        pathTextField.setText(fileChooser.showOpenDialog(new Stage()).getAbsolutePath());
    }
}

package user_interface.command;

import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class SelectFolder implements CommandInterface {
    private final Stage ownerStage;
    private final TextField displayTextFiled;

    public SelectFolder(Stage ownerStage, TextField displayTextFiled) {
        this.ownerStage = ownerStage;
        this.displayTextFiled = displayTextFiled;
    }

    @Override
    public void execute() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        displayTextFiled.setText(directoryChooser.showDialog(ownerStage).getAbsolutePath());
    }
}

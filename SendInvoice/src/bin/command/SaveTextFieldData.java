package bin.command;

import command.CommandInterface;
import javafx.scene.control.TextField;
import user_interface.PaneAbstract;

public final class SaveTextFieldData implements CommandInterface {
    private final TextField[] textFields;

    public SaveTextFieldData(TextField... textFields) {
        this.textFields = textFields;
    }

    @Override
    public void execute() {
        for (TextField textField : textFields) PaneAbstract.setProperty(textField.getId(), textField.getText());
        new SaveProperties().execute();
    }
}

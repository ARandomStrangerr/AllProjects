package bin.command;

import command.CommandInterface;
import javafx.scene.control.TextField;
import user_interface.MessagePane;
import user_interface.PaneMessageConcrete;
import user_interface.windows.WindowAbstract;

public final class CommandSetFilePath implements CommandInterface {
    private final TextField textField;
    private final WindowAbstract ownerWindow;

    public CommandSetFilePath(WindowAbstract ownerWindow,
                              TextField textField) {
        this.textField = textField;
        this.ownerWindow = ownerWindow;
    }

    @Override
    public void execute() {
        OpenFileChooser openFileChooser = new OpenFileChooser(ownerWindow);
        openFileChooser.execute();
        try {
            textField.setText(openFileChooser.getPath());
        } catch (NullPointerException e) {
            MessagePane.getInstance().setMsg("Đường dẫn đến tệp tin Excel chưa được chỉ định");
            new OpenPaneMessage(MessagePane.getInstance(), PaneMessageConcrete.getInstance(), ownerWindow).execute();
        }
    }
}

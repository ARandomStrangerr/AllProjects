package bin.command;

import command.CommandInterface;
import javafx.scene.control.TextField;
import user_interface.MessagePane;
import user_interface.PaneMessageConcrete;
import user_interface.windows.WindowAbstract;

public final class CommandSetFolderPath implements CommandInterface {
    private final TextField textField;
    private final WindowAbstract ownerWindow;

    public CommandSetFolderPath(WindowAbstract ownerWindow,
                                TextField textField) {
        this.textField = textField;
        this.ownerWindow = ownerWindow;
    }

    @Override
    public void execute() {
        OpenFolderChooser folderChooser = new OpenFolderChooser(ownerWindow);
        folderChooser.execute();
        try {
            textField.setText(folderChooser.getPath());
        } catch (NullPointerException e) {
            MessagePane.getInstance().setMsg("Đường dẫn đến thư mục chứa chưa được chỉ định");
            new OpenPaneMessage(MessagePane.getInstance(), PaneMessageConcrete.getInstance(), ownerWindow).execute();
        }
    }
}

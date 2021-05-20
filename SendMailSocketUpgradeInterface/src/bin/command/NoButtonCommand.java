package bin.command;

import command.CommandInterface;
import user_interface.WindowPopup;

public final class NoButtonCommand implements CommandInterface {
    private final WindowPopup windowPopup;

    public NoButtonCommand(WindowPopup windowPopup) {
        this.windowPopup = windowPopup;
    }

    @Override
    public void execute() {
        windowPopup.closeCurrentStage();
    }
}

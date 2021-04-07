package bin.error;

import javafx.stage.Stage;
import user_interface.ErrorWindow;

public class NullInfoException extends NullPointerException {
    public NullInfoException(String info, Stage ownerStage) {
        new ErrorWindow(ownerStage, "Thiếu thông tin: " + info).show();
    }
}

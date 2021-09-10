package user_interface;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public final class PaneWait extends PaneAbstract {
    private static PaneWait cache;

    public static PaneWait getInstance() {
        if (cache == null) cache = new PaneWait();
        return cache;
    }

    private final Label displayMsg;
    private final VBox mainPane;

    private PaneWait() {
        super(new VBox());
        mainPane = (VBox) super.getMainPane();
        displayMsg = new Label();
        setup();
    }

    @Override
    protected void setup() {
        mainPane.getChildren().add(displayMsg);
        mainPane.getStylesheets().addAll("stylesheet/universal.css","stylesheet/popup-window.css");
        mainPane.getStyleClass().addAll("padding", "border", "red-border");
    }

    public void setDisplayMsg(String msg) {
        displayMsg.setText(msg);
    }
}

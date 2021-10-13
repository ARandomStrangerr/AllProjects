package ui;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import user_interface.PaneAbstract;

public class MsgPane extends PaneAbstract {
    public static MsgPane cache;

    public static MsgPane getInstance() {
        if (cache == null) cache = new MsgPane();
        return cache;
    }

    private final VBox mainPane;
    private final Label displayMsgLabel;

    private MsgPane() {
        super(new VBox());
        mainPane = (VBox) super.getMainPane();
        displayMsgLabel = new Label();
    }

    @Override
    protected void setup() {
        mainPane.getChildren().add(displayMsgLabel);
        mainPane.getStyleClass().add("body");
    }

    public void setDisplayMsgLabel(String val) {
        this.displayMsgLabel.setText(val);
    }
}

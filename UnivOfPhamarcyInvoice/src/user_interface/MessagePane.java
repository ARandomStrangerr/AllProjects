package user_interface;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public final class MessagePane extends PaneAbstract {

    private static MessagePane cache;

    public static MessagePane getInstance() {
        if (cache == null) cache = new MessagePane();
        return cache;
    }

    private final Label displayMsg;

    private MessagePane() {
        super(new HBox());
        displayMsg = new Label();
        setup();
    }

    @Override
    protected void setup() {
        displayMsg.setWrapText(true);
        super.getMainPane().getChildren().add(displayMsg);
        super.getMainPane().setId("paneMessageUpper");
    }

    public void setMsg(String msg){
        this.displayMsg.setText(msg);
    }
}

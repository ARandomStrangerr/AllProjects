package user_interface;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public final class BlockMessagePane extends PaneAbstract {

    private static BlockMessagePane cache;

    public static BlockMessagePane getInstance() {
        if (cache == null) cache = new BlockMessagePane();
        return cache;
    }

    private final Label displayMsg;

    private BlockMessagePane() {
        super(new HBox());
        displayMsg = new Label();
        setup();
    }

    @Override
    protected void setup() {
        super.getMainPane().getChildren().add(displayMsg);
        super.getMainPane().setStyle("-fx-padding: 30");
        super.getMainPane().setId("paneMessageUpper");
    }

    public void setMsg(String msg){
        this.displayMsg.setText(msg);
    }
}

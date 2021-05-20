package user_interface;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public final class PaneUpperMessage extends PaneAbstract {
    private static PaneUpperMessage cache;

    public static PaneUpperMessage getInstance() {
        if (cache == null) cache = new PaneUpperMessage();
        return cache;
    }

    private final Label displayMessageLabel;
    private final VBox mainPane;

    private PaneUpperMessage() {
        super(new VBox());
        displayMessageLabel = new Label();
        this.mainPane = (VBox) super.getMainPane();
        setup();
    }

    @Override
    public void setup() {
        mainPane.getChildren().add(displayMessageLabel);
        mainPane.getStyleClass().addAll("padding","white-background");
    }

    public void setDisplayMessageLabel(String msg) {
        this.displayMessageLabel.setText(msg);
    }

    public void setPaneWidth(double width){
        this.mainPane.setPrefWidth(width);
    }
}

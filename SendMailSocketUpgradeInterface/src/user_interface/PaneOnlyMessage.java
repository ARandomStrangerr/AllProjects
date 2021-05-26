package user_interface;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public final class PaneOnlyMessage extends PaneAbstract {
    private static PaneOnlyMessage cache;

    public static PaneOnlyMessage getInstance() {
        if (cache == null) cache = new PaneOnlyMessage();
        return cache;
    }

    private final Label displayMessageLabel;
    private final VBox mainPane;

    private PaneOnlyMessage() {
        super(new VBox());
        displayMessageLabel = new Label();
        this.mainPane = (VBox) super.getMainPane();
        setup();
    }

    @Override
    public void setup() {
        mainPane.getChildren().add(displayMessageLabel);
        mainPane.getStylesheets().addAll("stylesheet/universal.css");
        mainPane.getStyleClass().addAll("padding","white-background");
    }

    public void setDisplayMessageLabel(String msg) {
        this.displayMessageLabel.setText(msg);
    }

    public void setPaneWidth(double width){
        this.mainPane.setPrefWidth(width);
    }
}

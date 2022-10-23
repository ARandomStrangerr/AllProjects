package ui;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PaneBlockMessage extends PaneAbstract{
    private final Label displayLabel;
    public PaneBlockMessage(Stage stage) {
        super(new VBox());
        VBox pane = (VBox) super.getPane();
        displayLabel = new Label();
        pane.getChildren().addAll(displayLabel);
        super.getStage().initModality(Modality.APPLICATION_MODAL);
        super.getStage().initStyle(StageStyle.UNDECORATED);
        super.getStage().initOwner(stage);
    }

    @Override
    protected void create() {}

    public void setDisplayLabel(String displayText){
        this.displayLabel.setText(displayText);
    }
}

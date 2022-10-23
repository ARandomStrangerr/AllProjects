package ui;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PaneMessage extends PaneAbstract {
    public PaneMessage(String style, String message) {
        super(new VBox());
        Label displayMsgLabel = new Label(message);
        Button closeButton = new Button("Đóng");
        closeButton.setOnAction(event -> super.getStage().close());
        HBox labelWrapper = new HBox(displayMsgLabel),
                buttonWrapper = new HBox(closeButton);
        labelWrapper.getStyleClass().add("upper-pane");
        buttonWrapper.getStyleClass().add("lower-pane");
        VBox pane = (VBox) super.getPane();
        pane.getChildren().addAll(labelWrapper, buttonWrapper);
        pane.getStylesheets().add("style.css");
        pane.setId(style);
    }

    @Override
    protected void create() {
    }
}

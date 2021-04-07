package user_interface;

import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import user_interface.command.Command;

abstract public class SuperWindow {
    protected final Stage stage;
    protected final String windowName;
    protected final String BOX_STYLE = "/user_interface/style_sheet/box.css",
            BUTTON_STYLE = "/user_interface/style_sheet/button.css",
            LABEL_STYLE = "/user_interface/style_sheet/label.css",
            TEXT_FIELD_STYLE = "/user_interface/style_sheet/text_field.css",
            TABLE_STYLE = "/user_interface/style_sheet/table.css";

    public SuperWindow(Stage stage, String windowName) {
        this.stage = stage;
        this.windowName = windowName;
    }

    public void openWindow() {
        stage.setScene(new Scene(setupPane(),1000,500));
        stage.setTitle(windowName);
        stage.show();
    }

    protected void executeCommand(Command command) {
        command.execute();
    }

    protected void setupPane(String path, String styleClass, Pane... panes) {
        for (Pane pane : panes) {
            pane.getStylesheets().add(path);
            pane.getStyleClass().add(styleClass);
        }
    }

    protected void setupControl(String path, String styleClass, Control... controls) {
        for (Control control : controls) {
            control.getStylesheets().add(path);
            control.getStyleClass().add(styleClass);
        }
    }

    abstract protected Pane setupPane();
}
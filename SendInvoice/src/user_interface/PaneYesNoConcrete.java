package user_interface;

import user_interface.concrete_pane.PaneYesNo;

public final class PaneYesNoConcrete extends PaneYesNo {
    private static PaneYesNoConcrete cache;

    public static PaneYesNoConcrete getInstance() {
        if (cache == null) cache = new PaneYesNoConcrete();
        return cache;
    }

    private PaneYesNoConcrete() {
        super();
        setup();
    }

    public void setup() {
        super.setup();
        super.getMainPane().getStylesheets().addAll("stylesheet/universal.css", "stylesheet/popup-window.css");
        super.getMainPane().getStyleClass().addAll("border", "yellow-border");
        super.yesButton.getStyleClass().add("green-button");
        super.noButton.getStyleClass().add("red-button");
        super.lowerPane.getStyleClass().addAll("spacing", "padding");
    }
}
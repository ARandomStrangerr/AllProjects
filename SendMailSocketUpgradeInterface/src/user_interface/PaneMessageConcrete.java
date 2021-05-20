package user_interface;

import user_interface.conrete_pane.PaneMessage;

public final class PaneMessageConcrete extends PaneMessage {
    private static PaneMessageConcrete cache;

    public static PaneMessageConcrete getInstance() {
        if (cache == null) cache = new PaneMessageConcrete();
        return cache;
    }

    private PaneMessageConcrete() {
        super();
        setup();
    }

    @Override
    public void setup() {
        super.setup();
        super.getMainPane().getStylesheets().addAll("stylesheet/universal.css", "stylesheet/popup-window.css");
        super.getMainPane().getStyleClass().addAll("border", "red-border");
        super.lowerPane.getStyleClass().addAll("spacing", "padding");
    }
}

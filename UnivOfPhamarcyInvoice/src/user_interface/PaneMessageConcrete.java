package user_interface;

import user_interface.pane_message.PaneMessage;

public final class PaneMessageConcrete extends PaneMessage {

    private static PaneMessageConcrete cache;

    public static PaneMessageConcrete getInstance() {
        if (cache == null)  cache = new PaneMessageConcrete();
        return cache;
    }

    private PaneMessageConcrete() {
        setup();
    }

    @Override
    protected void setup(){
        super.setup();
        super.getMainPane().getStylesheets().add("stylesheet/universal.css");
        super.getMainPane().setId("paneMessage");
        super.lowerPane.setId("paneMessageLower");
    }
}

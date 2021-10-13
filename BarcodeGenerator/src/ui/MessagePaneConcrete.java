package ui;

import user_interface.pane_message.PaneMessage;

public class MessagePaneConcrete extends PaneMessage {
    private static MessagePaneConcrete cache;

    public static MessagePaneConcrete getInstance() {
        if (cache == null) cache = new MessagePaneConcrete();
        return cache;
    }
    private MessagePaneConcrete() {
        setup();
    }
}

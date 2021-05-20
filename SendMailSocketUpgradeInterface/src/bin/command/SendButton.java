package bin.command;

import bin.thread.SendThread;
import command.CommandInterface;

public final class SendButton implements CommandInterface {
    public SendButton() {
    }

    @Override
    public void execute() {
        Runnable sendRunnable = new SendThread();
        Thread sendThread = new Thread(sendRunnable);
        sendThread.start();
    }
}

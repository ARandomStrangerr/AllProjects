import bin.command.StartingSequence;
import javafx.application.Application;
import javafx.stage.Stage;

public class TemporarySendInvoice extends Application {
    public static void main (String[] args){
        launch(args);
    }

    public void start(Stage primeStage){
        new StartingSequence(primeStage).execute();
    }
}
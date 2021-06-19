import bin.command.StartingSequence;
import javafx.application.Application;
import javafx.stage.Stage;

public class SendMailSocketUpgradeInterface extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        new StartingSequence(primaryStage).execute();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

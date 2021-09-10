import javafx.application.Application;
import javafx.stage.Stage;
import user_interface.command.StartingSequence;
public class SendMailSocket extends Application {
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        new StartingSequence(stage).execute();
    }
}
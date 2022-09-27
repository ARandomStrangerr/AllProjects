import bin.command.ReadFromLog;
import javafx.application.Application;
import javafx.stage.Stage;
import ui.PaneMain;

public class SendMailSocketUpgradeInterface extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        new ReadFromLog(primaryStage).execute();
        PaneMain pane = new PaneMain(primaryStage);
        pane.getStage().show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

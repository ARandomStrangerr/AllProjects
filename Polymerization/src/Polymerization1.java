import bin.chain.ChainStartingSequence;
import javafx.application.Application;
import javafx.stage.Stage;

public class Polymerization1  extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {
        new ChainStartingSequence(primaryStage).handle(null);
    }
}

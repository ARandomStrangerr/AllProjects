import javafx.application.Application;
import javafx.stage.Stage;
import user_interface.PaneLeft;
import user_interface.WindowRegular;
import user_interface.concrete_pane.PaneLeftRight;

public class Polymerization1  extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {

        PaneLeftRight mainPane = PaneLeftRight.getInstance();
        PaneLeft paneLeft = PaneLeft.getInstance();
        WindowRegular mainWindow = new WindowRegular(primaryStage);
        mainWindow.getCurrentStage().setHeight(500);
        mainWindow.getCurrentStage().setWidth(1000);
        mainPane.setWindow(mainWindow);
        mainPane.setLeftPane(paneLeft.getMainPane());
        mainWindow.setPane(mainPane);
        mainWindow.openCurrentStage();
    }
}

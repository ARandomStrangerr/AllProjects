import javafx.application.Application;
import javafx.stage.Stage;
import user_interface.MainPane;
import user_interface.windows.WindowRegular;

public class UnivOfPharmacyStarter extends Application {
    public static void main(String[] args) {
        System.setProperty("configFileName", "config.txt");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        WindowRegular mainWindow;
        MainPane mainPane;

        mainWindow = new WindowRegular(primaryStage);
        mainPane = MainPane.getInstance();
        mainPane.setWindow(mainWindow);
        mainWindow.setPane(mainPane);
        mainWindow.openCurrentStage();
    }
}

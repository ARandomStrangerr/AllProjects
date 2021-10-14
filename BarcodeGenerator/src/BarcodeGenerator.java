import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.MainPane;
import user_interface.windows.WindowRegular;

public class BarcodeGenerator extends Application {
    public static void main(String[] args) {
        System.setProperty("configFileName", "config.txt");
        launch(args);
    }

    public void start(Stage stage) {
        MainPane.getInstance().setWindow(new WindowRegular(stage));
        Scene scene = new Scene(MainPane.getInstance().getMainPane());
        scene.getStylesheets().add("ui/stylesheet/stylesheet.css");
        stage.setScene(scene);
        stage.setTitle("Công ty Điện - Điện tử Tin học EIE");
        stage.show();
    }
}

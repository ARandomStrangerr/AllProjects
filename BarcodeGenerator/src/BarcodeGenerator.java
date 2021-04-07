import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import user_interface.MainMenu;

public class BarcodeGenerator extends Application {
    public static void main(String[] args){
        launch (args);
    }

    public void start(Stage stage){
        stage.setScene(new Scene(new MainMenu(stage).getPrimePane()));
        stage.setTitle("Công ty Điện - Điện tử Tin học EIE");
        stage.show();
    }
}

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.MainPane;
import user_interface.MainMenu;

public class BarcodeGenerator extends Application {
    public static void main(String[] args){
        launch (args);
    }

    public void start(Stage stage){
        Scene scene = new Scene(MainPane.getInstance().getMainPane());
        scene.getStylesheets().add("ui/stylesheet/stylesheet.css");
        stage.setScene(scene);
        stage.setTitle("Công ty Điện - Điện tử Tin học EIE");
        stage.show();
    }
}

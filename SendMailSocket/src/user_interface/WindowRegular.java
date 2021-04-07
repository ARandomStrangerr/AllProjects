package user_interface;

import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class WindowRegular extends WindowAbstract{
    public WindowRegular(Stage ownerStage) {
        super(ownerStage);
    }

    @Override
    public void setup() {
        Stage thisStage = super.getThisStage();
        thisStage.setScene(new Scene(super.getPane().getPane()));
        thisStage.initModality(Modality.WINDOW_MODAL);
        thisStage.initOwner(super.getOwnerStage());
        thisStage.initStyle(StageStyle.DECORATED);
        thisStage.show();
    }

    public void setTitle(String title){
        super.getThisStage().setTitle(title);
    }

}

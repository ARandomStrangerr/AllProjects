package user_interface;

import bin.Command;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public abstract class PrimePane {
    private final Pane primePane;
    protected final Stage ownerStage;

    public PrimePane(Pane primePane, Stage ownerStage){
        this.primePane = primePane;
        this.ownerStage = ownerStage;
    }

    public Pane getPrimePane(){
        return primePane;
    }

    protected void execute(Command command) {
        command.execute();
    }
}

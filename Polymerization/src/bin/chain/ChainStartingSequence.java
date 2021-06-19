package bin.chain;

import chain_of_responsibility.Chain;
import chain_of_responsibility.Link;
import javafx.stage.Stage;

public class ChainStartingSequence extends Chain {
    public ChainStartingSequence(Stage primeStage){
        Link openMainWindow = new LinkOpenMainWindow(primeStage),
        setSaveFile = new LinkSetSaveFile("config.txt");
        setSaveFile.setNext(openMainWindow);
        super.setHead(setSaveFile);
    }
}

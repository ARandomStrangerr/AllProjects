package bin.command;

import bin.barcode.BarcodeGenerator;
import bin.barcode.EAN13;
import command.CommandInterface;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ui.MainPane;
import ui.table_structure.BarcodeInstance;

import java.util.LinkedList;
import java.util.List;

public class Print implements CommandInterface {
    @Override
    public void execute() {
        List<Pane> printPages;
        VBox printPage;
        HBox stamp;
        ObservableList<BarcodeInstance> printInstances;
        float paperWidth,
                paperHeight,
                paperMargin;
        int maxStampPerPaper,
                currentStampOnPaper;
        BarcodeGenerator generator;

        printInstances = MainPane.getInstance().getTable().getItems();
        paperWidth = Float.parseFloat((String) MainPane.getProperty("stampWidth"));
        paperHeight = Float.parseFloat((String) MainPane.getProperty("stampHeight"));
        paperMargin = Float.parseFloat((String) MainPane.getProperty("marginWidth"));
        maxStampPerPaper = Integer.parseInt((String) MainPane.getProperty("numberOfStamp"));
        currentStampOnPaper = 0;
        printPages = new LinkedList<>();
        printPage = new VBox();

        //update more type of barcode
        generator = new EAN13();

        for (BarcodeInstance printInstance : printInstances) {
            if (currentStampOnPaper == maxStampPerPaper) {
                currentStampOnPaper = 0;
                printPages.add(printPage);
                printPage = new VBox();
            }
            if (currentStampOnPaper == 0) {
                printPage.setStyle(String.format("-fx-pref-width:%fcm;-fx-pref-height:%fcm;",
                        paperWidth,
                        paperHeight));
            }

            stamp = new HBox();
            stamp.setStyle(String.format("-fx-padding:%fcm;-fx-pref-width=%fcm;-fx-pref-height:%fcm",
                    paperMargin,
                    paperWidth / 3,
                    paperHeight));
            try {
                Image image = SwingFXUtils.toFXImage(generator.generate(printInstance.getCode()),
                        null);
                ImageView imageView = new ImageView(image);
                stamp.getChildren().add(imageView);
            } catch (Exception ignore) {
            }
            currentStampOnPaper++;
        }

        printPages.add(printPage);

        Platform.runLater(() -> {
            for (Pane page : printPages) {
                Scene scene = new Scene(page);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
            }
        });

//        PrinterJob printerJob = PrinterJob.createPrinterJob();
//        if (printerJob != null) {
//            PageLayout pageLayout = printerJob.getPrinter().createPageLayout(Paper.A0);
//        }
    }
}

package bin.command;

import bin.barcode.BarcodeGenerator;
import bin.barcode.Code128;
import bin.barcode.EAN13;
import com.sun.javafx.print.PrintHelper;
import com.sun.javafx.print.Units;
import command.CommandInterface;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.PrinterJob;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
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
        HBox printPage;
        StackPane stamp;
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
        printPage = new HBox();

        //update more type of barcode
        generator = new Code128();

        for (BarcodeInstance printInstance : printInstances) {
            if (currentStampOnPaper == maxStampPerPaper) {
                currentStampOnPaper = 0;
                printPages.add(printPage);
                printPage = new HBox();
            }
            if (currentStampOnPaper == 0)
                printPage.setStyle(String.format("-fx-max-width:%fmm;" +
                                "-fx-min-width:%fmm;" +
                                "-fx-max-height:%fmm;" +
                                "-fx-min-height:%fmm;",
                        paperWidth,
                        paperWidth,
                        paperHeight,
                        paperHeight));

            stamp = new StackPane();
            stamp.setStyle(String.format("-fx-padding:%fmm;" +
                            "-fx-max-width:%fmm;" +
                            "-fx-min-width:%fmm;" +
                            "-fx-max-height:%fmm;" +
                            "-fx-min-height:%fmm;" +
                            "-fx-alignment:center",
                    paperMargin,
                    paperWidth / maxStampPerPaper,
                    paperWidth / maxStampPerPaper,
                    paperHeight,
                    paperHeight));
            stamp.getStyleClass().add("temp");
            try {
                Image image = SwingFXUtils.toFXImage(generator.generate(printInstance.getCode()),
                        null);
                ImageView imageView = new ImageView(image);
                stamp.getChildren().add(imageView);
            } catch (Exception e) {
                e.printStackTrace();
            }
            printPage.getChildren().add(stamp);
            currentStampOnPaper++;
        }

        printPages.add(printPage);

//        Platform.runLater(() -> {
//            for (Pane page : printPages) {
//                Scene scene = new Scene(page);
//                scene.getStylesheets().add("ui/stylesheet/stylesheet.css");
//                Stage stage = new Stage();
//                stage.setScene(scene);
//                stage.show();
//            }
//        });
        for (Pane root : printPages) {
            PrinterJob printerJob = PrinterJob.createPrinterJob();
            Paper photo = PrintHelper.createPaper("custom", paperWidth, paperHeight, Units.MM);
            if (printerJob != null) {
                PageLayout pageLayout = printerJob.getPrinter().createPageLayout(photo, PageOrientation.LANDSCAPE, 0, 0, 0, 0);
                printerJob.printPage(pageLayout, root);
            }
        }
    }
}

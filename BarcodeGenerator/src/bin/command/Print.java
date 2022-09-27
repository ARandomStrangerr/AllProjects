package bin.command;

import bin.barcode.BarcodeGenerator;
import bin.barcode.Code128;
import command.CommandInterface;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Pos;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.PrinterJob;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import ui.MainPane;
import ui.table_structure.BarcodeInstance;

import java.util.LinkedList;
import java.util.List;

public class Print implements CommandInterface {
    @Override
    public void execute() {
        List<Pane> printPages;
        HBox printPage;
        VBox stamp;
        ObservableList<BarcodeInstance> printInstances;
        float stampWidth,
                stampHeight,
                paperWidth,
                paperHeight,
                marginLeft;
        int maxStampPerPaper,
                currentStampOnPaper;
        BarcodeGenerator generator;

        printInstances = MainPane.getInstance().getTable().getItems();
        stampWidth = Float.parseFloat((String) MainPane.getProperty("stampWidth"));
        stampHeight = Float.parseFloat((String) MainPane.getProperty("stampHeight"));
        paperWidth = Float.parseFloat((String) MainPane.getProperty("paperWidth"));
        paperHeight = Float.parseFloat((String) MainPane.getProperty("paperHeight"));
        maxStampPerPaper = Integer.parseInt((String) MainPane.getProperty("numberOfStamp"));

        marginLeft = 150 - 10f / 7f * paperWidth;
        stampWidth = paperWidth * 20 / 7 / maxStampPerPaper;

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
            stamp = new VBox();
            try {
                Image image = SwingFXUtils.toFXImage(generator.generate(printInstance.getCode()),
                        null);
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(stampWidth);
                imageView.setFitHeight(stampHeight);
                imageView.setPreserveRatio(false);
                stamp.getChildren().add(imageView);
                String[] arr = printInstance.getLabel().split("\\.");
                for (String str : arr) {
                    if (!str.isEmpty())
                        stamp.getChildren().add(new Label(str.trim()));
                }
                stamp.setAlignment(Pos.TOP_CENTER);
            } catch (Exception e) {
                e.printStackTrace();
            }
            printPage.getChildren().add(stamp);
            currentStampOnPaper++;
        }

        printPages.add(printPage);
        PrinterJob printerJob = PrinterJob.createPrinterJob();
        PageLayout pageLayout = printerJob.getPrinter().createPageLayout(Paper.A4,
                PageOrientation.PORTRAIT,
                marginLeft,
                0,
                0,
                0);

        if (printerJob.showPrintDialog(MainPane.getInstance().getWindow().getCurrentStage())) {
            for (Pane page : printPages) {
                if (printerJob.printPage(pageLayout, page)) {
                    printerJob.endJob();
                }
            }
        }
    }
}
package bin;

import bin.barcode_generator.*;
import com.sun.javafx.print.PrintHelper;
import com.sun.javafx.print.Units;
import javafx.print.*;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.output.OutputException;
import user_interface.TableData;
import user_interface.WaitWindow;

import java.util.List;

public final class Print2 implements Command, Runnable {
    private final double pageWidth, pageHeight, barcodeWidth, barcodeHeight, stampWidth;
    private final int stampPerPage;
    private final String codeType;
    private final List<TableData> barcodeList;
    private final Stage ownerStage;
    private final PrinterJob job = PrinterJob.createPrinterJob();
    private final WaitWindow waitWindow;
    private final boolean showNote;
    private final Label noteLabel = new Label();

    public Print2(Stage ownerStage, List<TableData> barcodeList, String codeType, double pageWidth, double pageHeight,
                  double stampMarginWidth, double stampMarginHeight, int stampPerPage, boolean showNote) {
        int resolution = java.awt.Toolkit.getDefaultToolkit().getScreenResolution();
        this.barcodeList = barcodeList;
        this.pageWidth = pageWidth * resolution / 25.4;
        this.pageHeight = pageHeight * resolution / 25.4;
        this.stampPerPage = stampPerPage;
        this.stampWidth = (pageWidth / stampPerPage) * resolution / 25.4;
        this.barcodeHeight = (pageHeight - stampMarginHeight * 2) * resolution / 25.4;
        this.barcodeWidth = (pageWidth / stampPerPage - stampMarginWidth * 2) * resolution / 25.4;
        this.codeType = codeType;
        this.ownerStage = ownerStage;
        this.waitWindow = new WaitWindow(ownerStage, "Vui lòng đợi");
        this.showNote = showNote;
    }

    @Override
    public void execute() {
        if (job != null && job.showPrintDialog(ownerStage)) {
            Printer printer = job.getPrinter();
            Paper customPaper = PrintHelper.createPaper("105MMx22MM", 300, 30, Units.MM);

            PageLayout layout = printer.createPageLayout(customPaper, PageOrientation.PORTRAIT, Printer.MarginType.HARDWARE_MINIMUM);

            job.getJobSettings().setPageLayout(layout);
            waitWindow.showWindow();
            this.run();
        }
    }

    @Override
    public void run() {
        HBox page = null;
        for (int i = 0, j = 0; i < barcodeList.size(); i++, j++) {
            if (j == 0) {
                page = new HBox();
            } else if (j == stampPerPage) {
                j = 0;
                job.printPage(page);
            }
            ImageView barcodeImageView = new ImageView(
                    createBarcode(barcodeList.get(i).getCode(), barcodeList.get(i).getCode())
            );
            barcodeImageView.setFitWidth(barcodeWidth);
            barcodeImageView.setFitHeight(barcodeHeight);

            noteLabel.setText(barcodeList.get(i).getName());
            noteLabel.setWrapText(true);

            page.getChildren().add(barcodeImageView);

            if(showNote){
                page.getChildren().add(noteLabel);
            }
        }
        job.printPage(page);
        job.endJob();
        waitWindow.closeWindow();
    }

    private Image createBarcode(String code, String note) {
        try {
            switch (codeType) {
                case "128":
                    return new Code128(code, note, showNote).getBarcodeImg();
                case "128A":
                    return new Code128A(code, note, showNote).getBarcodeImg();
                case "128B":
                    return new Code128B(code, note, showNote).getBarcodeImg();
                case "EAN128":
                    return new CodeEAN128(code, note, showNote).getBarcodeImg();
                case "EAN13":
                    return new CodeEAN13(code, note, showNote).getBarcodeImg();
                case "2of7":
                    return new Code2of7(code, note, showNote).getBarcodeImg();
            }
        } catch (BarcodeException | OutputException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
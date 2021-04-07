package bin.barcode_generator;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeImageHandler;
import net.sourceforge.barbecue.output.OutputException;


public abstract class BarcodeGenerator {
    private final Barcode barcode;

    public BarcodeGenerator(Barcode barcode, String note, boolean showNote) {
        this.barcode = barcode;
        barcode.setLabel(note);
        barcode.setDrawingText(showNote);
    }

    public Image getBarcodeImg() throws OutputException {
        return SwingFXUtils.toFXImage(BarcodeImageHandler.getImage(barcode), null);
    }
}

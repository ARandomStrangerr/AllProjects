package bin.barcode;

import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.output.OutputException;

import java.awt.image.BufferedImage;

public interface BarcodeGenerator {
    BufferedImage generate(String code) throws BarcodeException, OutputException;
}

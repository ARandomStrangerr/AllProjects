package bin.barcode;

import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;
import net.sourceforge.barbecue.output.OutputException;

import java.awt.image.BufferedImage;

public class Code128 implements BarcodeGenerator {
    @Override
    public BufferedImage generate(String code) throws BarcodeException, OutputException {
        return BarcodeImageHandler.getImage(BarcodeFactory.createCode128(code));
    }
}

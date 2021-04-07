package bin.barcode_generator;

import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeFactory;

public final class Code128B extends BarcodeGenerator {
    public Code128B(String code, String note, boolean showNote) throws BarcodeException {
        super(BarcodeFactory.createCode128B(code), note, showNote);
    }
}

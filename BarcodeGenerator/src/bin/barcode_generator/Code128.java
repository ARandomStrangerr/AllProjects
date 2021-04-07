package bin.barcode_generator;

import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeFactory;

public final class Code128 extends BarcodeGenerator {
    public Code128(String code, String note, boolean showNote) throws BarcodeException {
        super(BarcodeFactory.createCode128(code), note, showNote);
    }
}

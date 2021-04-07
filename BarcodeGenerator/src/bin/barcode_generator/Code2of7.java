package bin.barcode_generator;

import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeFactory;

public final class Code2of7 extends BarcodeGenerator {
    public Code2of7(String code, String note, boolean showNote) throws BarcodeException {
        super(BarcodeFactory.create2of7(code), note, showNote);
    }
}

package bin.barcode_generator;

import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeFactory;

public final class CodeEAN13 extends BarcodeGenerator {
    public CodeEAN13(String code, String note, boolean showNote) throws BarcodeException {
        super(BarcodeFactory.createEAN13(code), note, showNote);
    }
}

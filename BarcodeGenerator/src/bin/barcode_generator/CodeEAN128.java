package bin.barcode_generator;

import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeFactory;

public final class CodeEAN128 extends BarcodeGenerator {
    public CodeEAN128(String code, String note, boolean showNote) throws BarcodeException {
        super(BarcodeFactory.createEAN128(code), note, showNote);
    }
}

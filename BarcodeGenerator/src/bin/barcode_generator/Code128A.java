package bin.barcode_generator;

import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeFactory;

public final class Code128A extends BarcodeGenerator {
    public Code128A(String code, String note, boolean showNote) throws BarcodeException {
        super(BarcodeFactory.createCode128A(code), note, showNote);
    }
}

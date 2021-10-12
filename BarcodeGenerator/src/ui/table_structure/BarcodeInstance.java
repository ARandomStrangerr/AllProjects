package ui.table_structure;

import javafx.beans.property.SimpleStringProperty;

public final class BarcodeInstance {
    private final SimpleStringProperty code, label;

    public BarcodeInstance(String code,
                           String label) {
        this.code = new SimpleStringProperty( code);
        this.label = new SimpleStringProperty(label);
    }

    public String getCode() {
        return code.get();
    }

    public void setCode(String code) {
        this.code.set(code);
    }

    public String getLabel() {
        return label.get();
    }

    public void setLabel(String label) {
        this.label.set(label);
    }
}

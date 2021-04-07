package user_interface;

import javafx.beans.property.SimpleStringProperty;

public class TableData {
    private final SimpleStringProperty code, name;

    public TableData(String code, String name) {
        this.code = new SimpleStringProperty(code);
        this.name = new SimpleStringProperty(name);
    }

    public String getCode() {
        return code.get();
    }

    public String getName() {
        return name.get();
    }

    public void setCode(String code) {
        this.code.set(code);
    }

    public void setName(String name) {
        this.name.set(name);
    }
}

package user_interface.table_item;

import javafx.beans.property.SimpleStringProperty;

public final class ReceiverInfo {
    private final SimpleStringProperty email, variableField;

    public ReceiverInfo(String email, String variableField) {
        this.email = new SimpleStringProperty(email);
        this.variableField = new SimpleStringProperty(variableField);
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getEmail() {
        return email.get();
    }

    public void setVariableField(String variableField) {
        this.variableField.set(variableField);
    }

    public String getVariableField() {
        return variableField.get();
    }
}

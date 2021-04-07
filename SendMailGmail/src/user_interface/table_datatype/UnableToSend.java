package user_interface.table_datatype;

import javafx.beans.property.SimpleStringProperty;

public class UnableToSend {
    private final SimpleStringProperty email, error;

    public UnableToSend(String email, String error) {
        this.email = new SimpleStringProperty(email);
        this.error = new SimpleStringProperty(error);
    }

    public String getEmail() {
        return email.get();
    }

    public String getError() {
        return error.get();
    }
}

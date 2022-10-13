package SendMailSocketUpgradeInterface.ui;

import javafx.beans.property.SimpleStringProperty;

public class EmailTableData {
    SimpleStringProperty email, file;

    public EmailTableData(String email, String file) {
        this.email = new SimpleStringProperty(email);
        this.file = new SimpleStringProperty(file);
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getFile() {
        return file.get();
    }
    public void setFile(String file) {
        this.file.set(file);
    }
}

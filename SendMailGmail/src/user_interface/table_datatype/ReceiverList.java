package user_interface.table_datatype;

import javafx.beans.property.SimpleStringProperty;

public class ReceiverList {
    private final SimpleStringProperty name, email, attachment;

    public ReceiverList(String name, String email, String attachment) {
        this.name = new SimpleStringProperty(name);
        this.email = new SimpleStringProperty(email);
        this.attachment = new SimpleStringProperty(attachment);
    }

    public String getName() {
        return name.get();
    }

    public String getEmail() {
        return email.get();
    }

    public String getAttachment() {
        return attachment.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public void setAttachment(String attachment) {
        this.attachment.set(attachment);
    }
}

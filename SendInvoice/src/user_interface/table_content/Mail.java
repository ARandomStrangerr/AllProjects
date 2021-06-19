package user_interface.table_content;

import javafx.beans.property.SimpleStringProperty;

public final class Mail {
    private final SimpleStringProperty id, mail;
    public Mail(String id, String mail){
        this.id = new SimpleStringProperty(id);
        this.mail = new SimpleStringProperty(mail);
    }

    public String getId() {
        return id.get();
    }
    public void setId(String id) {
        this.id.set(id);
    }

    public String getMail() {
        return mail.get();
    }

    public void setMail(String mail) {
        this.mail.set(mail);
    }
}

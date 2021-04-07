package user_interface.table_item;

import javafx.beans.property.SimpleStringProperty;

public class ReceiverInfo {
    private final SimpleStringProperty emailAddress, attachmentFileName;

    public ReceiverInfo(String emailAddress, String attachmentFileName) {
        this.emailAddress = new SimpleStringProperty(emailAddress);
        this.attachmentFileName = new SimpleStringProperty(attachmentFileName);
    }

    public String getEmailAddress() {
        return emailAddress.get();
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress.set(emailAddress);
    }

    public String getAttachmentFileName() {
        return attachmentFileName.get();
    }

    public void setAttachmentFileName(String attachmentFileName) {
        this.attachmentFileName.set(attachmentFileName);
    }
}

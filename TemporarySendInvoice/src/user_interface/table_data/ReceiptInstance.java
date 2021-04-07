package user_interface.table_data;

import javafx.beans.property.SimpleStringProperty;

public class ReceiptInstance {
    private final SimpleStringProperty id, staffId, staffFirstName, staffLastName, studentId, studentFirstName, studentLastName;

    public ReceiptInstance(String id, String staffId, String staffFirstName, String staffLastName, String studentId, String studentFirstName, String studentLastName) {
        this.id = new SimpleStringProperty(id);
        this.staffId = new SimpleStringProperty(staffId);
        this.staffFirstName = new SimpleStringProperty(staffFirstName);
        this.staffLastName = new SimpleStringProperty(staffLastName);
        this.studentId = new SimpleStringProperty(studentId);
        this.studentFirstName = new SimpleStringProperty(studentFirstName);
        this.studentLastName = new SimpleStringProperty(studentLastName);
    }

    public String getId() {
        return id.get();
    }

    public String getStaffId() {
        return staffId.get();
    }

    public String getStaffFirstName() {
        return staffFirstName.get();
    }

    public String getStaffLastName() {
        return staffLastName.get();
    }

    public String getStudentId() {
        return studentId.get();
    }


    public String getStudentFirstName() {
        return studentFirstName.get();
    }


    public String getStudentLastName() {
        return studentLastName.get();
    }

}

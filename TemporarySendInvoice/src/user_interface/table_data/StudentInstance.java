package user_interface.table_data;

import javafx.beans.property.SimpleStringProperty;

public class StudentInstance {
    private final SimpleStringProperty id, firstName, lastName;

    public StudentInstance(String id, String firstName, String lastName) {
        this.id = new SimpleStringProperty(id);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
    }

    public String getId() {
        return id.get();
    }

    public String getFirstName() {
        return firstName.get();
    }

    public String getLastName() {
        return lastName.get();
    }
}

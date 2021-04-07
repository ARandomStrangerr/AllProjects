package user_interface.table_data;

import javafx.beans.property.SimpleStringProperty;

public class ServiceInstance {
    private final SimpleStringProperty id, name, price;

    public ServiceInstance(String id, String name, String price) {
        this.id = new SimpleStringProperty(price);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleStringProperty(price);
    }

    public String getId() {
        return id.get();
    }

    public String getName() {
        return name.get();
    }
    public String getPrice() {
        return price.get();
    }
}

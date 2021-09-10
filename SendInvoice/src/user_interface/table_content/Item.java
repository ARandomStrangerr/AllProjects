package user_interface.table_content;

import javafx.beans.property.SimpleStringProperty;

abstract public class Item {
    //factory
    public static OrdinaryItem createItem(String name,
                                          int quantity,
                                          long price,
                                          float taxRate){
        return new OrdinaryItem(name,
                quantity,
                price,
                taxRate);
    }
    private final SimpleStringProperty name;

    public Item( String name) {
        this.name = new SimpleStringProperty(name);
    }

    public String getName() {
        return name.get();
    }
}

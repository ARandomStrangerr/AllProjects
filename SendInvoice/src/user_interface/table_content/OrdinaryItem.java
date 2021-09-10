package user_interface.table_content;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;

final public class OrdinaryItem extends Item {
    private final SimpleIntegerProperty quantity;
    private final SimpleLongProperty price, totalAmount, taxAmount;

    public OrdinaryItem(String name, int quantity, long price, float taxRate) {
        super(name);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.price = new SimpleLongProperty(price);
        this.totalAmount = new SimpleLongProperty(price * quantity);
        this.taxAmount = new SimpleLongProperty((long) (totalAmount.get() * taxRate / 100));
    }

    public int getQuantity() {
        return quantity.get();
    }

    public long getPrice() {
        return price.get();
    }

    public long getTotalAmount() {
        return totalAmount.get();
    }

    public long getTaxAmount() {
        return taxAmount.get();
    }
}

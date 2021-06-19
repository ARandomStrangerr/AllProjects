package user_interface.table_content;

import javafx.beans.property.SimpleStringProperty;

public final class Invoice {
    private final SimpleStringProperty personName,
            personCode,
            collectiveName,
            collectiveTaxCode,
            address,
            paymentMethod;

    public Invoice(String personName,
                   String personCode,
                   String collectiveName,
                   String collectiveTaxCode,
                   String address,
                   String paymentMethod) {
        this.personName = new SimpleStringProperty(personName);
        this.personCode = new SimpleStringProperty(personCode);
        this.collectiveName = new SimpleStringProperty(collectiveName);
        this.collectiveTaxCode = new SimpleStringProperty(collectiveTaxCode);
        this.address = new SimpleStringProperty(address);
        this.paymentMethod = new SimpleStringProperty(paymentMethod);
    }

    public String getPersonName() {
        return personName.get();
    }

    public String getPersonCode() {
        return personCode.get();
    }

    public String getCollectiveName() {
        return collectiveName.get();
    }

    public String getCollectiveTaxCode() {
        return collectiveTaxCode.get();
    }

    public String getAddress() {
        return address.get();
    }

    public String getPaymentMethod(){
        return paymentMethod.get();
    }
}

package user_interface.table_content;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;

import java.util.List;

public final class Invoice {
    private final SimpleStringProperty invoiceType,
            templateCode,
            invoiceSeries,
            personName,
            personCode,
            collectiveName,
            collectiveTaxCode,
            address,
            paymentMethod;
    private final SimpleFloatProperty taxRate;
    private final ObservableList<Item> items;

    public Invoice(
            String invoiceType,
            String templateCode,
            String invoiceSeries,
            String personName,
            String personCode,
            String collectiveName,
            String collectiveTaxCode,
            String address,
            String paymentMethod,
            float taxRate,
            ObservableList<Item> items)
            throws IllegalArgumentException {
        if (!invoiceType.equals("01GTKT")
                && !invoiceType.equals("02GTTT")
                && !invoiceType.equals("07KPTQ")
                && !invoiceType.equals("03XKNB")
                && !invoiceType.equals("04HGDL")
                && !invoiceType.equals("01BLP"))
            throw new IllegalArgumentException();
        if (templateCode == null || templateCode.trim().isEmpty())
            throw new IllegalArgumentException();
        if (invoiceSeries == null || invoiceSeries.trim().isEmpty())
            throw new IllegalArgumentException();
        if ((personName == null || personName.trim().isEmpty())
                && (collectiveName == null || collectiveName.trim().isEmpty()))
            throw new IllegalArgumentException();
        if (collectiveName != null
                && !collectiveName.trim().isEmpty()
                && (collectiveTaxCode == null || collectiveTaxCode.trim().isEmpty()))
            throw new IllegalArgumentException();
        this.invoiceType = new SimpleStringProperty(invoiceType);
        this.templateCode = new SimpleStringProperty(templateCode);
        this.invoiceSeries = new SimpleStringProperty(invoiceSeries);
        this.personName = new SimpleStringProperty(personName);
        this.personCode = new SimpleStringProperty(personCode);
        this.collectiveName = new SimpleStringProperty(collectiveName);
        this.collectiveTaxCode = new SimpleStringProperty(collectiveTaxCode);
        this.address = new SimpleStringProperty(address);
        this.paymentMethod = new SimpleStringProperty(paymentMethod);
        this.taxRate = new SimpleFloatProperty(taxRate);
        this.items = items;
    }

    public String getInvoiceType() {
        return invoiceType.get();
    }

    public String getTemplateCode() {
        return templateCode.get();
    }

    public String getInvoiceSeries() {
        return invoiceSeries.get();
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

    public String getPaymentMethod() {
        return paymentMethod.get();
    }

    public float getTaxRate() {
        return taxRate.get();
    }

    public ObservableList<Item> getItems() {
        return items;
    }
}

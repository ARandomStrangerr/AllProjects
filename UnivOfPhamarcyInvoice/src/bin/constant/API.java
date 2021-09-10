package bin.constant;

public enum API {
    ACCESS_TOKEN ("/auth/login"),
    CREATE_INVOICE("InvoiceAPI/InvoiceWS/createInvoice/"),
    GET_INVOICE("/InvoiceAPI/InvoiceUtilsWS/getInvoiceRepresentationFile");

    public final String value;

    API(String value) {
        this.value = value;
    }
}

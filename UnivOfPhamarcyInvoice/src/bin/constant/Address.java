package bin.constant;

public enum Address {
    ADDRESS("https://api-vinvoice.viettel.vn/services/einvoiceapplication/api"),
    ADDRESS_AUTH("https://api-vinvoice.viettel.vn");
    public final String value;

    Address(String value) {
        this.value = value;
    }
}

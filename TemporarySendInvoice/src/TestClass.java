import viettel_electronic_invoice_webservice.connection.Connection;

import java.net.HttpURLConnection;
import java.net.URL;

public class TestClass {
    public static void main(String[] args) throws Exception{
        HttpURLConnection connection = (HttpURLConnection) new URL("https://api-sinvoice.viettel.vn:443/InvoiceAPI/InvoiceWS/createInvoice/0102140091").openConnection();
        System.out.println(Connection.ADDRESS.REAL_API.getString() + Connection.ACTION.CREATE_INVOICE.getString() + "0102140091");
    }
}

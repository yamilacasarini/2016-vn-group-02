import com.sun.jersey.api.client.ClientResponse;

public interface ApiDeBanco {
public String obtenerBancos();
public ClientResponse getBookAndSendHeader(String filter, String value, String header, String headerValue);
public ClientResponse getBookByFilter(String filter, String value, String fields);
public ClientResponse getBookByFilter(String filter, String value);
}

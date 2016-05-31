import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/* API DE BANCOS
 * http://private-96b476-ddsutn.apiary-mock.com/banks?banco=banco&servicio=servicio
 * 
 * */

public class RequestService {
	private Client client;
	private static final String API_GOOGLE = "http://private-96b476-ddsutn.apiary-mock.com/";
	private static final String RESOURCE = "banks";

	// Inicializacion del cliente.
	public RequestService() {
		this.client = Client.create();
		// En la documentacion se puede ver como al cliente agregarle un
		// ClientConfig
		// para agregarle filtros en las respuestas (por ejemplo, para loguear).
	}

	// Prueba de concepto de un parametro y los mensajes por separado para
	// identificar los tipos de datos.
	public ClientResponse getBookByFilter(String filter, String value) {
		WebResource recurso = this.client.resource(API_GOOGLE).path(RESOURCE);
		WebResource recursoConParametros = recurso.queryParam("banks", filter + ": " + value);
		WebResource.Builder builder = recursoConParametros.accept(MediaType.APPLICATION_JSON);
		ClientResponse response = builder.get(ClientResponse.class);
		return response;
	}

	// Prueba de concepto de envio de dos parametros, y el envio de mensajes
	// juntos.
	public ClientResponse getBookByFilter(String filter, String value, String fields) {
		ClientResponse response = this.client.resource(API_GOOGLE).path(RESOURCE)
				.queryParam("banks", filter + ":" + value).queryParam("fields", fields)
				.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		return response;
	}

	// Prueba de concepto del envio de un request con un header.
	public ClientResponse getBookAndSendHeader(String filter, String value, String header, String headerValue) {
		ClientResponse response = this.client.resource(API_GOOGLE).path(RESOURCE)
				.queryParam("banks", filter + ":" + value).header(header, headerValue)
				.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		return response;
	}

	public String obtenerBancos() {
		String retorno;
		RequestService requester = new RequestService();
		ClientResponse response = requester.getBookByFilter("sucursal", "Avellaneda");
		if (response.getStatus() == 200) {
			throw new RuntimeException();
		} else {
			
			retorno = response.getEntity(String.class);
		}
		return retorno;
	}
}

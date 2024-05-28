package cliente;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class AuxiliarValoracionesWS {

	// URI del recurso que permite acceder al servicio de valoraciones
	final private String baseURI = "http://localhost:8080/ValoraWS/servicios/valoraciones";
	Client cliente = null;
	JSONParser parser;


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor de la clase
	 * Crea el cliente
	 */
	public AuxiliarValoracionesWS()  {
		this.cliente = ClientBuilder.newClient();
	}

	/**
	 * Cuando cada cliente cierra su sesión volcamos los datos en el fichero para mantenerlos actualizados
	 */
	public void guardaDatos() {
		cliente.target(baseURI).path("guardaDatos")
				.request(MediaType.TEXT_PLAIN).put(Entity.text(""));
	}

	/**
	 * Devuelve los nombres de los restaurantes
	 *
	 * @return JSONArray de restaurantes. Vacío si no hay ningún restaurante
	 * @throws	WebApplicationException estado no controlado por el cliente (200 o 404)
	 */
	public JSONArray listaRestaurantes() throws WebApplicationException, ParseException {
		JSONArray restaurantes = new JSONArray();
		String auxRest;
		Response response = cliente.target(baseURI).path("listaRestaurantes")
				.request(MediaType.TEXT_PLAIN).get();
		int estado = response.getStatus();
		if(estado == 200){
			auxRest = response.readEntity(String.class);

			JSONParser parser = new JSONParser();
			restaurantes = (JSONArray) parser.parse((String) auxRest);

			response.close();
			return restaurantes;
		}else if(estado == 404){
			response.close();
			return restaurantes;
		}else{
			response.close();
			throw new WebApplicationException("Error en servidor al listar restaurantes.");
		}
	}

	/**
	 * Devuelve las valoraciones de un restaurante dado
	 *
	 * @param nomRest	nombre del restaurante
	 * @return JSONArray de valoraciones del restaurante. Vacío si no hay valoraciones de ese restaurante
	 * @throws	WebApplicationException	estado no controlado por el cliente (200 o 404)
	 */
	public JSONArray consultaValoraciones(String nomRest) throws WebApplicationException, ParseException {
		JSONArray valoraciones = new JSONArray();
		String auxVal;
		Response response = cliente.target(baseURI).path("consultaValoraciones/" + nomRest)
				.request(MediaType.TEXT_PLAIN).get();
		int estado = response.getStatus();
		if(estado == 200){
			auxVal = response.readEntity(String.class);

			JSONParser parser = new JSONParser();
			valoraciones = (JSONArray) parser.parse((String) auxVal);

			response.close();
			return valoraciones;
		}else if(estado == 404){
			response.close();
			return valoraciones;
		}else{
			response.close();
			throw new WebApplicationException("Error en servidor al listar valoraciones para el restaurante "+nomRest+".");
		}
	}

	/**
	 * El cliente codcliente hace una valoración
	 *
	 * @param codcliente	código del cliente que hace la valoración
	 * @param nomRest		nombre del restaurante
	 * @param fecha			fecha en que se hace la valoración
	 * @param estrellas		estrellas otorgadas
	 * @param comentario	comentario con la opinion del cliente
	 * @return JSONObject con la información de la valoración. Vacío si ya hay una para el mismo restaurante y fecha
	 * @throws	WebApplicationException	estado no controlado por el cliente (200 o 404)
	 */
	public JSONObject hazValoracion(String codcliente, String nomRest, String fecha, long estrellas, String comentario) throws ParseException {
		JSONObject Valoracion = new JSONObject();
		String auxValo;
		Response response = cliente.target(baseURI).path("hazValoracion/" + codcliente + "/" + nomRest)
				.queryParam("nomRest", nomRest)
				.queryParam("fecha", fecha)
				.queryParam("estrellas", estrellas)
				.queryParam("comentario", comentario)
				.request(MediaType.TEXT_PLAIN).post(Entity.text(""));

		int estado = response.getStatus();
		if(estado == 200){
			auxValo = response.readEntity(String.class);

			JSONParser parser = new JSONParser();
			Valoracion = (JSONObject) parser.parse((String) auxValo);

			response.close();
			return Valoracion;
		}else if(estado == 404){
			response.close();
			return Valoracion;
		}else{
			response.close();
			throw new WebApplicationException("Error en servidor al hacer una Valoracion.");
		}
	}

	/**
	 * El cliente codcli borra una valoracion
	 *
	 * @param codcliente	código del cliente que borra la valoración
	 * @param nomRest		nombre del restaurante
	 * @param fecha			fecha de la valoración a borrar
	 * @return	JSON de la valoración borrada. JSON vacío si no se ha borrado
	 * @throws	WebApplicationException	estado no controlado por el cliente (200 o 404)
	 */
	public JSONObject borraValoracion(String codcliente, String nomRest, String fecha) throws ParseException {
		JSONObject Valoracion = new JSONObject();
		String auxVal;
		Response response = cliente.target(baseURI).path("borraValoracion/" + codcliente + "/" + nomRest)
				.queryParam("nomRest", nomRest)
				.queryParam("fecha", fecha)
				.request(MediaType.TEXT_PLAIN).delete();

		int estado = response.getStatus();
		if(estado == 200){
			auxVal = response.readEntity(String.class);

			JSONParser parser = new JSONParser();
			Valoracion = (JSONObject) parser.parse((String) auxVal);

			response.close();
			return Valoracion;
		}else if(estado == 404){
			response.close();
			return Valoracion;
		}else{
			response.close();
			throw new WebApplicationException("Error en servidor al borrar una Valoracion.");
		}
	}

	/**
	 * El cliente codcliente modifica las estrellas y/o el comentario de una valoración propia
	 *
	 * @param codcliente	código del cliente que modifica su valoración
	 * @param nomRest		nombre del restaurante
	 * @param fecha			fecha en que se realizo la valoración
	 * @param estrellas		estrellas otorgadas
	 * @param comentario	comentario con la opinion del cliente
	 * @return	JSON de la valoración modificada. JSON vacío si no se ha modificado
	 * @throws	WebApplicationException	estado no controlado por el cliente (200 o 404)
	 */
	public JSONObject modificaValoracion(String codcliente, String nomRest, String fecha, long estrellas, String comentario) throws ParseException {
		JSONObject Valoracion = new JSONObject();
		String auxVal;
		Response response = cliente.target(baseURI).path("modificaValoracion/" + codcliente + "/" + nomRest)
				.queryParam("nomRest", nomRest)
				.queryParam("fecha", fecha)
				.queryParam("estrellas", estrellas)
				.queryParam("comentario", comentario)
				.request(MediaType.TEXT_PLAIN).put(Entity.text(""));

		int estado = response.getStatus();
		if(estado == 200){
			auxVal = response.readEntity(String.class);

			JSONParser parser = new JSONParser();
			Valoracion = (JSONObject) parser.parse((String) auxVal);

			response.close();
			return Valoracion;
		}else if(estado == 404){
			response.close();
			return Valoracion;
		}else{
			response.close();
			throw new WebApplicationException("Error en servidor al hacer una Valoracion.");
		}
	}


} // fin clase

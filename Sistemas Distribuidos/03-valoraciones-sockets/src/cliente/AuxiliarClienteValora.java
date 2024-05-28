package cliente;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import comun.MyStreamSocket;

/**
 * Esta clase es un módulo que proporciona la lógica de aplicación
 * para el Cliente del servicio de valoraciones usando sockets de tipo stream
 */

public class AuxiliarClienteValora {

	private final MyStreamSocket mySocket; // Socket de datos para comunicarse con el servidor
	JSONParser parser;

	/**
	 * Construye un objeto auxiliar asociado a un cliente del servicio.
	 * Crea un socket para conectar con el servidor.
	 * @param	hostName	nombre de la máquina que ejecuta el servidor
	 * @param	portNum		número de puerto asociado al servicio en el servidor
	 */
	AuxiliarClienteValora(String hostName, String portNum)
			throws SocketException, UnknownHostException, IOException {

		// IP del servidor
		InetAddress serverHost = InetAddress.getByName(hostName);
		// Puerto asociado al servicio en el servidor
		int serverPort = Integer.parseInt(portNum);
		//Instantiates a stream-mode socket and wait for a connection.
		this.mySocket = new MyStreamSocket(serverHost, serverPort);
		/**/  System.out.println("Hecha peticion de conexion");
		parser = new JSONParser();
	} // end constructor


	/**
	 * Devuelve la lista de restaurantes
	 *
	 * @return JSONArray con los nombres de los restaurantes. Vacío si no hay restaurantes
	 */
	@SuppressWarnings("unchecked")
	public JSONArray listaRestaurantes() throws IOException, ParseException {
		JSONObject auxPeticion = new JSONObject();
		auxPeticion.put("operacion",1);
		String peticion = new JSONObject().toJSONString();
		mySocket.sendMessage(peticion);

		JSONParser parser = new JSONParser();
		JSONArray listaRestaurantes = (JSONArray) parser.parse((String) mySocket.receiveMessage());

		return listaRestaurantes;
	} // end listaRestaurantes

	/**
	 * Devuelve las valoraciones de un restaurante dado
	 *
	 * @param nomRest
	 * @return JSONArray de valoraciones del restaurante. Vacío si no hay valoraciones de ese restaurante
	 */
	@SuppressWarnings("unchecked")
	public JSONArray consultaValoraciones(String nomRest) throws IOException, ParseException {
		JSONObject auxPeticion = new JSONObject();
		auxPeticion.put("operacion",2);
		auxPeticion.put("nomRest", nomRest);
		String peticion = new JSONObject().toJSONString();
		mySocket.sendMessage(peticion);

		JSONParser parser = new JSONParser();
		JSONArray listaValoraciones = (JSONArray) parser.parse((String) mySocket.receiveMessage());

		return listaValoraciones;
	} // end consultaViajes



	/**
	 * El cliente codcliente hace una valoración
	 *
	 * @param codcliente
	 * @param nomRest
	 * @param fecha
	 * @param estrellas
	 * @param comentario
	 * @return JSONObject con la información de la valoración. Vacío si ya hay una para el mismo restaurante y fecha
	 */
	@SuppressWarnings("unchecked")
	public JSONObject hazValoracion(String codcliente, String nomRest, String fecha, long estrellas, String comentario) throws IOException, ParseException {
		JSONObject auxPeticion = new JSONObject();
		auxPeticion.put("operacion",3);
		auxPeticion.put("codcliente", codcliente);
		auxPeticion.put("nomRest", nomRest);
		auxPeticion.put("fecha", fecha);
		auxPeticion.put("estrellas", estrellas);
		auxPeticion.put("comentario", comentario);
		String peticion = new JSONObject().toJSONString();
		mySocket.sendMessage(peticion);

		JSONParser parser = new JSONParser();
		JSONObject Valoracion = (JSONObject) parser.parse((String) mySocket.receiveMessage());

		return Valoracion;
	} // end reservaViaje

	/**
	 * El cliente codcli borra una valoración
	 *
	 * @param codcliente
	 * @param nomRest
	 * @param fecha
	 * @return	JSON de la valoración borrada. JSON vacío si no se ha borrado
	 */
	public JSONObject borraValoracion(String codcliente, String nomRest, String fecha) throws IOException, ParseException {
		JSONObject auxPeticion = new JSONObject();
		auxPeticion.put("operacion",4);
		auxPeticion.put("codcliente", codcliente);
		auxPeticion.put("nomRest", nomRest);
		auxPeticion.put("fecha", fecha);
		String peticion = new JSONObject().toJSONString();
		mySocket.sendMessage(peticion);

		JSONParser parser = new JSONParser();
		JSONObject Valoracion = (JSONObject) parser.parse((String) mySocket.receiveMessage());

		return Valoracion;
	} // end anulaReserva

	/**
	 * El cliente codcliente modifica las estrellas y/o el comentario de una valoración propia
	 *
	 * @param codcliente
	 * @param nomRest
	 * @param fecha
	 * @return	JSON de la valoración modificada. JSON vacío si no se ha modificado
	 */
	public JSONObject modificaValoracion(String codcliente, String nomRest, String fecha, long estrellas, String comentario) throws IOException, ParseException {
		JSONObject auxPeticion = new JSONObject();
		auxPeticion.put("operacion",5);
		auxPeticion.put("codcliente", codcliente);
		auxPeticion.put("nomRest", nomRest);
		auxPeticion.put("fecha", fecha);
		auxPeticion.put("estrellas", estrellas);
		auxPeticion.put("comentario", comentario);
		String peticion = new JSONObject().toJSONString();
		mySocket.sendMessage(peticion);

		JSONParser parser = new JSONParser();
		JSONObject Valoracion = (JSONObject) parser.parse((String) mySocket.receiveMessage());

		return Valoracion;
	} // end ofertaViaje


	/**
	 * Finaliza la conexión con el servidor
	 */
	@SuppressWarnings("unchecked")
	public void cierraSesion( ) throws IOException {
		JSONObject auxPeticion = new JSONObject();
		auxPeticion.put("operacion",0);
		String peticion = new JSONObject().toJSONString();
		mySocket.sendMessage(peticion);
	} // end done
} //end class

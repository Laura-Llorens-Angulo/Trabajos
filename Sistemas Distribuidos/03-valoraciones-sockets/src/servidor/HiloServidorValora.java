package servidor;

import java.io.IOException;
import java.net.SocketException;

import gestor.GestorValoraciones;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import comun.MyStreamSocket;

/**
 * Clase ejecutada por cada hebra encargada de servir a un cliente del servicio de valoraciones.
 * El método run contiene la lógica para gestionar una sesión con un cliente.
 */

class HiloServidorValora implements Runnable {


	private MyStreamSocket myDataSocket;
	private GestorValoraciones gestor;

	/**
	 * Construye el objeto a ejecutar por la hebra para servir a un cliente
	 * @param	myDataSocket	socket stream para comunicarse con el cliente
	 * @param	unGestor		gestor de viajes
	 */
	HiloServidorValora(MyStreamSocket myDataSocket, GestorValoraciones unGestor) {
		this.myDataSocket = myDataSocket;
		this.gestor = unGestor;
	}

	/**
	 * Gestiona una sesion con un cliente	
	 */
	public void run( ) {
		String operacion = "0";
		boolean done = false;
		// ...
		try {
			while (!done) {
				// Recibe una petición del cliente
				// Extrae la operación y sus parámetros

				String peticion = myDataSocket.receiveMessage();
				JSONParser parser = new JSONParser();
				JSONObject peticionJSON = (JSONObject) parser.parse(peticion);
				operacion = peticionJSON.get("operacion").toString();

				switch (operacion) {
					case "0":
						gestor.guardaDatos();
						myDataSocket.close();
						break;

					case "1": { // Devuelve la lista de restaurantes
						myDataSocket.sendMessage(gestor.listaRestaurantes().toJSONString());
						break;
					}
					case "2": { // Consulta las valoraciones de un restaurante dado
						JSONArray viajes = gestor.consultaValoraciones((String) peticionJSON.get("nomRest"));
						myDataSocket.sendMessage(viajes.toJSONString());
						break;
					}
					case "3": { // Hace una valoración
						String codcliente = (String) peticionJSON.get("codcliente");
						String fecha = (String) peticionJSON.get("fecha");
						String nomRest = (String) peticionJSON.get("nomRest");
						String comentario = (String) peticionJSON.get("comentario");
						long estrellas = (long) peticionJSON.get("long");
						JSONObject valoracion = gestor.hazValoracion(codcliente,nomRest,fecha,estrellas,comentario);
						myDataSocket.sendMessage(valoracion.toJSONString());
						break;
					}
					case "4": { // Borra una valoración
						String codcliente = (String) peticionJSON.get("codcliente");
						String fecha = (String) peticionJSON.get("fecha");
						String nomRest = (String) peticionJSON.get("nomRest");
						JSONObject valoracion = gestor.borraValoracion(codcliente,nomRest,fecha);
						myDataSocket.sendMessage(valoracion.toJSONString());
						break;
					}
					case "5": { // Modifica una valoración
						String codcliente = (String) peticionJSON.get("codcliente");
						String fecha = (String) peticionJSON.get("fecha");
						String nomRest = (String) peticionJSON.get("nomRest");
						String comentario = (String) peticionJSON.get("comentario");
						long estrellas = (long) peticionJSON.get("long");
						JSONObject valoracion = gestor.modificaValoracion(codcliente,nomRest,fecha,estrellas,comentario);
						myDataSocket.sendMessage(valoracion.toJSONString());
						break;
					}

				} // fin switch
			} // fin while   
		} // fin try
		catch (SocketException ex) {
			System.out.println("Capturada SocketException");
		}
		catch (IOException ex) {
			System.out.println("Capturada IOException");
		}
		catch (Exception ex) {
			System.out.println("Exception caught in thread: " + ex);
		} // fin catch
	} //fin run

} //fin class 

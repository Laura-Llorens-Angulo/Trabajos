package es.uji.valoraws.modelo;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class GestorValoraciones {

	private FileWriter os;			// stream para escribir los datos en el fichero
	private FileReader is;			// stream para leer los datos del fichero

	/**
	 * 	Diccionario para manejar los datos en memoria.
	 * 	La clave es el nombre único del restaurante.
	 * 	El valor es un vector con todas las valoraciones de ese restaurante.
	 * 	Para cada restaurante no puede haber varias valoraciones de un mismo cliente en la misma fecha.
	 */
	final private HashMap<String, Vector<Valoracion>> mapa;


	/**
	 * Constructor del gestor de valoraciones
	 * Crea o Lee un fichero con datos de prueba
	 */
	public GestorValoraciones() {
		this.mapa =  new HashMap<String, Vector<Valoracion>>();
		File file = new File("valoraciones.json");
		try {
			if (!file.exists() ) {
				// Si no existe el fichero de datos, los genera, rellena el diccionario y los escribe en el fichero
				os = new FileWriter(file);
				generaDatos();
				escribeFichero(os);
				os.close();
			}
			else {
				// Si existe el fichero o lo acaba de crear, lo lee y rellena el diccionario con los datos
				is = new FileReader(file);
				leeFichero(is);
				is.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * Cuando cada cliente cierra su sesión volcamos los datos en el fichero para mantenerlos actualizados
	 */
	public void guardaDatos(){
		File file = new File("valoraciones.json");
		try {
			os = new FileWriter(file);
			escribeFichero(os);
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * Copia en el fichero un array JSON con los datos de las valoraciones guardadas en el diccionario
	 *
	 * @param os	stream de escritura asociado al fichero de datos
	 */
	@SuppressWarnings("unchecked")
	private synchronized void escribeFichero(FileWriter os) {
		try {
			JSONArray jsonArray = new JSONArray();
			for (Map.Entry lineaMapa : mapa.entrySet()) {
				Vector<Valoracion> valoraciones = (Vector<Valoracion>) lineaMapa.getValue();
				for(Valoracion valoracion : valoraciones){
					jsonArray.add(valoracion.toJSON());
				}
			}
			os.write(jsonArray.toJSONString());
			os.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Almacena una valoración en el diccionario
	 * @param valoracion	valoración a almacenar
	 */
	private synchronized void almacenaValoracion(Valoracion valoracion) {
		// IMPLEMENTADO
		Vector<Valoracion> valoraciones = this.mapa.get(valoracion.getNomRest());
		if (valoraciones == null)
			valoraciones = new Vector<Valoracion>();
		valoraciones.add(valoracion);
		this.mapa.put(valoracion.getNomRest(), valoraciones);
	}


	/**
	 * Genera los datos iniciales y los guarda en el diccionario
	 */
	private void generaDatos() {

		Valoracion valoracion = new Valoracion("cli01", "Burriquin", "28-05-2024", 1, "Im pre sio nan te");
		almacenaValoracion(valoracion);

		valoracion = new Valoracion("cli02", "Tachatela",  "29-05-2024", 2, "Ni se te ocurra");
		almacenaValoracion(valoracion);

		valoracion = new Valoracion("cli01", "101 taquitos",  "07-06-2024", 0, "Sin palabras");
		almacenaValoracion(valoracion);

		valoracion = new Valoracion("cli03", "Burriquin",  "12-08-2024", 5, "Imprescindible");
		almacenaValoracion(valoracion);

		valoracion = new Valoracion("cli04", "Bollit",  "07-11-2024", 3, "Ni fu ni fa");
		almacenaValoracion(valoracion);

	}


	/**
	 * Lee los datos del fichero en formato JSON y los añade al diccionario en memoria
	 *
	 * @param is	stream de lectura de los datos del fichero
	 */
	private synchronized void leeFichero(FileReader is) {
		JSONParser parser = new JSONParser();
		try {
			// Leemos toda la información del fichero en un array de objetos JSON
			JSONArray array = (JSONArray) parser.parse(is);
			// Rellena los datos del diccionario en memoria a partir del JSONArray
			rellenaDiccionario(array);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Rellena el diccionario a partir de los datos en un JSONArray
	 *
	 * @param array	JSONArray con los datos de las Valoraciones
	 */
	private synchronized void rellenaDiccionario(JSONArray array) {
		// IMPLEMENTADO
		if (array == null)
			return;

		array.forEach(item -> {
			Valoracion newValoracion = new Valoracion((JSONObject) item);
			Vector<Valoracion> vectorValoraciones = new Vector<>();
			if(mapa.containsKey(newValoracion.getNomRest())){
				vectorValoraciones = mapa.get(newValoracion.getNomRest());
			}
			vectorValoraciones.add(newValoracion);
			mapa.put(newValoracion.getNomRest(), vectorValoraciones);
		});
	}



	/**
	 * Devuelve la lista de restaurantes con valoraciones
	 *
	 * @return JSONArray con la lista de restaurantes. Vacío si no hay restaurantes
	 */
	@SuppressWarnings("unchecked")
	public synchronized JSONArray listaRestaurantes() {
		JSONArray listado = new JSONArray();
		for (String nombreRestaurante : this.mapa.keySet()) {
			listado.add(nombreRestaurante);
		}
		return listado;
	}


	/**
	 * Devuelve las valoraciones de un restaurante dado
	 *
	 * @param nomRest	nombre del restaurante
	 * @return JSONArray de valoraciones del restaurante. Vacío si no hay valoraciones de ese restaurante
	 */
	@SuppressWarnings("unchecked")
	public synchronized JSONArray consultaValoraciones(String nomRest) {
		Vector<Valoracion> valoraciones = this.mapa.get(nomRest);
		JSONArray listadoValoraciones = new JSONArray();
		if(valoraciones != null){
			for (Valoracion valoracion : valoraciones) {
				listadoValoraciones.add(valoracion.toJSON());
			}
		}
		return listadoValoraciones;
	}


	/**
	 * Busca una valoración de un cliente y fecha entre las valoraciones de un restaurante.
	 *
	 * @param vector		vector de valoraciones del restaurante en el que buscamos
	 * @param codcliente	código del cliente cuyas valoraciones buscamos
	 * @param fecha			fecha de la valoración buscada
	 * @return Referencia a la valoración. Si no la encuentra, null
	 *
	 * Devolvemos una referencia a la valoración para poder borrarla y modificarla
	 */
	private synchronized Valoracion buscaValoracion(Vector<Valoracion> vector, String codcliente, String fecha) {
		if(vector != null){
			for (Valoracion v : vector) {
				if ( v.getCodcliente().equals(codcliente) && v.getFecha().equals(fecha) )
					return v;
			}
		}
		return null;
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
	 */
	public synchronized JSONObject hazValoracion(String codcliente, String nomRest, String fecha, long estrellas, String comentario) {
		JSONObject infoVal = new JSONObject();
		Valoracion val = null;
		Vector<Valoracion> valoraciones = new Vector<>();

		if(mapa.containsKey(nomRest)){
			valoraciones = mapa.get(nomRest);
			val = buscaValoracion(valoraciones,codcliente,fecha);
		}

		if(val == null){
			val = new Valoracion(codcliente,nomRest,fecha,estrellas,comentario);
			valoraciones.add(val);
			mapa.put(nomRest,valoraciones);
			infoVal = val.toJSON();
		}

		return infoVal;
	}

	/**
	 * El cliente codcliente borra una valoración
	 *
	 * @param codcliente	código del cliente que borra la valoración
	 * @param nomRest		nombre del restaurante
	 * @param fecha			fecha de la valoración a borrar
	 * @return	JSON de la valoración borrada. JSON vacío si no se ha borrado
	 */
	public synchronized JSONObject borraValoracion(String codcliente, String nomRest, String fecha) {
		JSONObject infoVal = new JSONObject();
		Vector<Valoracion> valoraciones = mapa.get(nomRest);
		Valoracion val = buscaValoracion(valoraciones,codcliente,fecha);
		if(val != null){
			if(valoraciones.remove(val)){
				infoVal=val.toJSON();
				if(valoraciones.isEmpty()) mapa.remove(val.getNomRest());
				else mapa.put(nomRest,valoraciones);
			}
		}
		return infoVal;
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
	 */
	public synchronized JSONObject modificaValoracion(String codcliente, String nomRest, String fecha, long estrellas, String comentario) {
		// IMPLEMENTADO
		JSONObject infoVal = new JSONObject();
		Vector<Valoracion> valoraciones = mapa.get(nomRest);
		Valoracion val = buscaValoracion(valoraciones,codcliente,fecha);
		if(val != null){
//			valoraciones.remove(val);
			val.setEstrellas(estrellas);
			val.setComentario(comentario);
//			valoraciones.add(val);
			infoVal=val.toJSON();
//			mapa.put(nomRest,valoraciones);
		}
		return infoVal;
	}




}

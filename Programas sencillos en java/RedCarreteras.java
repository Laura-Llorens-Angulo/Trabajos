package Practica3;

import java.security.InvalidParameterException;
import java.util.*;

public class RedCarreteras {
	private Map<String, Map<String, Integer>> red;

	public RedCarreteras() {
		red = new HashMap<>();
	}

	/**
	 * Valida que un tramo sea correcto.
	 * 
	 * Es decir, que ninguna de las dos ciudades sea null, y que la distancia sea
	 * mayor de cero. No se admiten tramos de una ciudad con sigo misma. En el
	 * caso de que un tramo no sea válido produce una excepción.
	 * 
	 * @param una,
	 *            una ciudad
	 * @param otra,
	 *            la otra ciudad
	 * @param distancia,
	 *            la distancia del tramo
	 * @throws //InvalidParamenterException
	 *             si el tramo no es válido.
	 */
	private void validarTramo(String una, String otra, int distancia) {
		if(una==null || otra==null || una.equals(otra) || distancia<=0)
			throw new InvalidParameterException();
	}

	/**
	 * Devuelve un conjunto con todas las ciudades de la red.
	 */
	public Set<String> ciudades() {
		return red.keySet();
	}

	/**
	 * Añade un tramo a la red.
	 * 
	 * Valida que el tramo sea valido. Si alguna de las dos ciudades no existiá
	 * previamente en la red, la añade. El tramo se añadirá dos veces, una para
	 * cada ciudad. En caso de que el tramo ya existiera remplazará el valor de
	 * la distancia.
	 * 
	 * @return Distancia previa del tramo, -1 si el tramo no exitía.
	 * @throws //InvalidParamenterException
	 *             si el tramo no es válido.
	 */
	public int nuevoTramo(String una, String otra, int distancia) {
		validarTramo(una,otra,distancia);
		Set<String> s = ciudades();
		int anteriordistancia = -1;

		Map<String,Integer> mapauna = new HashMap<>();
		mapauna.put(otra,distancia);
		Map<String,Integer> mapaotra = new HashMap<>();
		mapauna.put(una,distancia);

		if(s.contains(una)){											//si existe ciudad una
			if(red.get(una).containsKey(otra))							//si existe el tramo
				anteriordistancia = red.get(una).put(otra,distancia);
			else														//si no existe el tramo
				red.get(una).put(otra,distancia);
		}else															//no existe la ciudad uno
			red.put(una,mapauna);

		if(s.contains(otra)){											//si existe ciudad otra
			if(red.get(otra).containsKey(una))							//si existe el tramo
				anteriordistancia = red.get(una).put(otra,distancia);
			else														//si no existe el tramo
				red.get(otra).put(otra,distancia);
		}else															//no existe la ciudad otra
			red.put(otra,mapaotra);

		return anteriordistancia;
	}

	/**
	 * Comprueba si existe un tramo entre dos ciudades.
	 * 
	 * @return La distancia del tramo, o -1 si no existe
	 */
	public int existeTramo(String una, String otra) {
		int distancia = -1;
		if(una==null||otra==null)
			return distancia;

		if(red.get(una)!=null && red.get(otra)!=null) {
			if (red.get(una).get(otra) != null)
				distancia = red.get(una).get(otra);
			if (red.get(otra).get(una) != null)
				distancia = red.get(otra).get(una);
		}
		return distancia;
	}

	/**
	 * Borra el tramo entre dos ciudades si existe.
	 * 
	 * @return La distancia del tramo, o -1 si no existía.
	 */
	public int borraTramo(String una, String otra) {
		int distancia = -1;
		if(red.get(una).get(otra)!=null)
			distancia=red.get(una).remove(otra);
		if(red.get(otra).get(una)!=null)
			distancia=red.get(otra).remove(una);
		return distancia;
	}

	/**
	 * Comprueba si un camino es posible.
	 * 
	 * Un camino es una lista de ciudades. El camino es posible si existe un
	 * tramo entre cada para de ciudades consecutivas. Si dos ciudades
	 * consecutivas del camino son la misma el camino es posible y la distancia
	 * añadida es 0. Si el camino incluye una sóla ciudad de la red el resultado es 0.
	 * 
	 * @return La distancia total del camino, es decir la suma de las distancias
	 *         de los tramos, o -1 si el camino no es posible o no incluye ninguna ciudad.
	 */
	public int compruebaCamino(List<String> camino) {
		int distanciatotal=0;
		String origen,destino;

		if(camino.size()==0)
			distanciatotal=-1;

		for(int i=0;i<camino.size()-1;i++){
			origen=camino.get(i);
			destino=camino.get(i+1);
			if(origen!=destino){
				if(existeTramo(origen,destino)==-1){
					distanciatotal=-1;
					break;
				}
				distanciatotal += existeTramo(origen,destino);
				}

		}
		return distanciatotal;
	}

}

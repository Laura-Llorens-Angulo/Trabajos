package valora;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class ValoracionesLocal {


	/**
	 * Muestra el menu de opciones y lee repetidamente de teclado hasta obtener una opción válida
	 *
	 * @param teclado	stream para leer la opción elegida de teclado
	 * @return			opción elegida
	 */
	public static int menu(Scanner teclado) {
		int opcion;
		System.out.println("\n\n");
		System.out.println("=====================================================");
		System.out.println("============            MENU        =================");
		System.out.println("=====================================================");
		System.out.println("0. Salir");
		System.out.println("1. Listar los restaurantes");
		System.out.println("2. Consultar valoraciones de un restaurante dado");
		System.out.println("3. Hacer una valoración");
		System.out.println("4. Borrar una valoración");
		System.out.println("5. Modificar una valoración");
		do {
			System.out.print("\nElige una opción (0..5): ");
			opcion = teclado.nextInt();
		} while ( (opcion<0) || (opcion>5) );
		teclado.nextLine();
		return opcion;
	}

	/**
	 * Devuelve la fecha actual en el formato dd-mm-aaaa
	 * @return fecha actual en formato dd-mm-aaaa
	 */
	static private String fechaHoy() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate hoy = LocalDate.now();
		return dtf.format(hoy);
	}

	/**
	 * Lee un valor de estrellas hasta que se teclea uno valido: (1..5)
	 * @param teclado	teclado del que se lee
	 * @return	numero de estrellas leído
	 */
	private static long leeEstrellas(Scanner teclado) {
		long estrella;
		do {
			System.out.print("Introduce tu valoración en estrellas, del 1 al 5: ");
			estrella = teclado.nextLong();

			if(estrella < 1)
				System.out.println("Número de estrellas incorrecto, el valor debe estar entre 1 y 5, "+estrella+" es menor que 1.");
			if(estrella > 5)
				System.out.println("Número de estrellas incorrecto, el valor debe estar entre 1 y 5, "+estrella+" es mayor que 5.");

		} while ((estrella<1) || (estrella>5));

		teclado.nextLine();
		return estrella;
	}

	/**
	 * Lee y construye la fecha hasta que se teclea una valida, con el formato: dd-mm-yyyy
	 * @param teclado teclado desde el que se lee.
	 * @return fecha en el formato válido.
	 */
	private static String leeFecha(Scanner teclado) {
		int currentYear = LocalDate.now().getYear();
		int maxYearsBehind = 5;

		JSONArray dateArray = new JSONArray();
		dateArray.add(formateaNumeroParaDateString(
				leeNumeroEntreIntervaloParaFecha(teclado, "Día", 1, 31), 2));
		dateArray.add(formateaNumeroParaDateString(
				leeNumeroEntreIntervaloParaFecha(teclado, "Mes", 1, 12), 2));
		dateArray.add(formateaNumeroParaDateString(
				leeNumeroEntreIntervaloParaFecha(teclado, "Año", currentYear-maxYearsBehind, currentYear), 4));

		return String.join("-", dateArray);
	}

	/**
	 * Lee y construye el número entre dos valores hasta que se teclea uno valido.
	 * @param teclado teclado desde el que se lee.
	 * @param field nombre del valor que se desea obtener.
	 * @param min valor mínimo aceptado para el valor que se desea obtener.
	 * @param max valor máximo aceptado para el valor que se desea obtener.
	 * @return numero válido leído.
	 */
	private static int leeNumeroEntreIntervaloParaFecha(Scanner teclado, String field, int min, int max) {
		int value;
		do {
			System.out.print(field + " de la valoración -> Introduce un valor entre el " + min + " y el " + max + ": ");
			value = teclado.nextInt();

			if(value < min)
				System.out.println("Número introducido incorrecto, el valor debe estar entre " + min + " y " + max +
						", " + value + " es menor que " + min + ".");
			if(value > max)
				System.out.println("Número introducido incorrecto, el valor debe estar entre " + min + " y " + max +
						", " + value + " es mayor que " + max + ".");

		} while ( (value < min) || (value > max) );

		teclado.nextLine();
		return value;
	}

	/**
	 * Transforma un número entero en una cadena de texto añadiendo tantos ceros a la izquierda como se requiera para
	 * conseguir la cantidad de dígitos especificada.
	 * @param value valor numérico a formatear.
	 * @param digits cantidad de dígitos que debe tener la cadena resultante (incluyendo posibles ceros a la izquierda).
	 * @return cadena de texto generada.
	 */
	private static String formateaNumeroParaDateString(int value, int digits) {
		return String.format("%0" + digits + "d", value);
	}


	/**
	 * Programa principal. Muestra el menú repetidamente y atiende las peticiones del cliente.
	 * @param args	no se usan argumentos de entrada al programa principal
	 */
	public static void main(String[] args)  {
		Scanner teclado = new Scanner(System.in);
		GestorValoraciones gestor = new GestorValoraciones();

		System.out.print("Introduce tu código de cliente: ");
		String codcliente = teclado.nextLine();

		int opcion;
		do {
			opcion = menu(teclado);
			switch (opcion) {
				case 0 -> {
					guardarDatosYTerminar(gestor);
				}
				case 1 -> {
					listarNombresRestaurantes(gestor);
				}
				case 2 -> {
					consultaValoracionesRestaurantePorNombre(gestor, teclado);
				}
				case 3 -> {
					crearValoracion(gestor, teclado, codcliente);
				}
				case 4 -> {
					borrarValoracion(gestor, teclado, codcliente);
				}
				case 5 -> {
					modificarValoracion(gestor, teclado, codcliente);
				}
			}

		} while (opcion != 0);
	} // fin de main

	/**
	 * 0. Guardar los datos en el fichero y sale del programa.
	 * @param gestor
	 */
	private static void guardarDatosYTerminar(GestorValoraciones gestor) {
		gestor.guardaDatos();
		System.out.println("Se han guardado los datos correctamente. Adiós");
	}

	/**
	 * 1. Listar los nombres de los restaurantes.
	 * @param gestor
	 */
	private static void listarNombresRestaurantes(GestorValoraciones gestor) {
		JSONArray restaurantes = gestor.listaRestaurantes();
		if(restaurantes.isEmpty()){
			System.out.println("No hay ninguna valoración.");
		}else{
			System.out.print("Los restaurantes con valoraciones son: " +
					String.join(", ", restaurantes));
		}
	}

	/**
	 * 2. Consultar valoraciones de un restaurante dado.
	 * @param gestor
	 */
	private static void consultaValoracionesRestaurantePorNombre(GestorValoraciones gestor, Scanner teclado) {
		System.out.print("Introduce el nombre del restaurante del que desea ver valoraciones: ");
		String nomRestaurante = teclado.nextLine();
		JSONArray valoraciones = gestor.consultaValoraciones(nomRestaurante);
		if(valoraciones.isEmpty()) System.out.println("No hay valoraciones para el restaurante "+nomRestaurante+".");
		else{
			System.out.println("Las valoraciones del restaurante "+nomRestaurante+" son:");
			for(Object valoracion:valoraciones){
				Valoracion val = new Valoracion((JSONObject) valoracion);
				System.out.println(construyeTextoValoracion(val));
			}
		}
	}

	/**
	 * 3. Hacer una valoración.
	 * @param gestor
	 */
	private static void crearValoracion(GestorValoraciones gestor, Scanner teclado, String codcliente) {
		System.out.print("Introduce el nombre del restaurante: ");
		String nomRest = teclado.nextLine();
		long estrellas = leeEstrellas(teclado);
		System.out.print("Introduce un breve comentario con tu opinión sobre el restaurante: ");
		String comentario = teclado.nextLine();

		JSONObject valoracion = gestor.hazValoracion(codcliente, nomRest, fechaHoy(), estrellas, comentario);
		if (valoracion == null || valoracion.isEmpty()) {
			System.out.print("No se ha podido realizar la valoración, ya has realizado una valoración " +
					"a este restaurante en el mismo día.");
		} else {
			Valoracion v = new Valoracion(valoracion);
			System.out.print("Valoracion realizada con éxito. " + construyeTextoValoracion(v));
		}
	}

	/**
	 * 4. Borrar una valoración tuya.
	 * @param gestor
	 */
	private static void borrarValoracion(GestorValoraciones gestor, Scanner teclado, String codcliente) {
		System.out.print("Introduce el nombre del restaurante: ");
		String nomRest = teclado.nextLine();
		String fecha = leeFecha(teclado);
		JSONObject valoracion = gestor.borraValoracion(codcliente,nomRest,fecha);
		if(valoracion.isEmpty()) System.out.println("No se ha podido borrar la valoración del restaurante "+
				nomRest+" y fecha "+fecha+" .");
		else{
			Valoracion val = new Valoracion(valoracion);
			System.out.println("Se ha borrado la siguiente valoracion del restaurante "+
					val.getNomRest()+":\nValorado con "+val.getEstrellas()+ " estrellas en la fecha "+
					val.getFecha()+ " y el comentario: \""+val.getComentario()+"\".");
		}
	}

	/**
	 * 5. Modificar una valoración tuya
	 * @param gestor
	 */
	private static void modificarValoracion(GestorValoraciones gestor, Scanner teclado, String codcliente) {
		System.out.print("Introduce el nombre del restaurante: ");
		String nomRest = teclado.nextLine();
		String fecha = leeFecha(teclado);
		long estrellas = leeEstrellas(teclado);
		System.out.print("Introduce un breve comentario con tu opinión sobre el restaurante: ");
		String comentario = teclado.nextLine();

		JSONObject valoracion = gestor.modificaValoracion(codcliente, nomRest, fecha, estrellas, comentario);
		if (valoracion == null || valoracion.isEmpty()) {
			System.out.print("No se ha podido realizar la modificación de la valoración.");
		} else {
			Valoracion v = new Valoracion(valoracion);
			System.out.print("Valoracion actualizada con éxito. " + construyeTextoValoracion(v));
		}
	}

	private static String construyeTextoValoracion(Valoracion valoracion) {
		return "El cliente " + valoracion.getCodcliente() + " ha valorado el restaurante " + valoracion.getNomRest() +
				" con " + valoracion.getEstrellas() + " estrellas en la fecha " +  valoracion.getFecha() +
				" y ha añadido el comentario: \"" + valoracion.getComentario() + "\".";
	}
} // fin class

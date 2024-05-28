package client;

import common.IntCallbackCliente;
import common.IntServidorValora;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;


public class ClienteValoraRMI {


	// Sustituye esta clase por tu versión de la clase ValoraLocal de la práctica 1

	// Modifícala para que instancie un objeto de la clase IntServidorValora

	// Modifica todas las llamadas al objeto de la clase IntServidorValora
	// por llamadas al objeto de la clase IntServidorValora.
	// Los métodos a llamar tendrán la misma signatura.


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
        System.out.println("6. Suscribirse a restaurante");
        System.out.println("7. Darse de baja de restaurante");
        do {
            System.out.print("\nElige una opción (0..7): ");
            opcion = teclado.nextInt();
        } while ( (opcion<0) || (opcion>7) );
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
        ImpCallbackCliente callbackObj;
        try {
            callbackObj = new ImpCallbackCliente();
        } catch (RemoteException e) { throw new RuntimeException(e); }
        try {
            IntServidorValora interfazServidor = (IntServidorValora) Naming.lookup("rmi://localhost:1099/Valoraciones");
            System.out.print("Introduce tu código de cliente: ");
            String codcliente = teclado.nextLine();

            int opcion;
            do {
                opcion = menu(teclado);
                switch (opcion) {
                    case 0 -> {
                        guardarDatosYTerminar(interfazServidor);
                    }
                    case 1 -> {
                        listarNombresRestaurantes(interfazServidor);
                    }
                    case 2 -> {
                        consultaValoracionesRestaurantePorNombre(interfazServidor, teclado);
                    }
                    case 3 -> {
                        crearValoracion(interfazServidor, teclado, codcliente);
                    }
                    case 4 -> {
                        borrarValoracion(interfazServidor, teclado, codcliente);
                    }
                    case 5 -> {
                        modificarValoracion(interfazServidor, teclado, codcliente);
                    }
                    case 6 -> {
                        suscribirARestaurante(callbackObj, interfazServidor, teclado, codcliente);
                    }
                    case 7 -> {
                        bajaDeRestaurante(callbackObj, interfazServidor, teclado, codcliente);
                    }
                }

            } while (opcion != 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    } // fin de main

    /**
     * 0. Guardar los datos en el fichero y sale del programa.
     * @param interfazServidor
     */
    private static void guardarDatosYTerminar(IntServidorValora interfazServidor) throws IOException {
        interfazServidor.guardaDatos();
        System.out.println("Se han guardado los datos correctamente. Adiós");
        System.exit(0);
    }

    /**
     * 1. Listar los nombres de los restaurantes.
     * @param interfazServidor
     */
    private static void listarNombresRestaurantes(IntServidorValora interfazServidor) throws IOException, ParseException {
        JSONArray restaurantes = interfazServidor.listaRestaurantes();
        if(restaurantes.isEmpty()){
            System.out.println("No hay ninguna valoración.");
        }else{
            System.out.print("Los restaurantes con valoraciones son: " +
                    String.join(", ", restaurantes));
        }
    }

    /**
     * 2. Consultar valoraciones de un restaurante dado.
     * @param interfazServidor
     */
    private static void consultaValoracionesRestaurantePorNombre(IntServidorValora interfazServidor, Scanner teclado) throws IOException, ParseException {
        System.out.print("Introduce el nombre del restaurante del que desea ver valoraciones: ");
        String nomRestaurante = teclado.nextLine();
        JSONArray valoraciones = interfazServidor.consultaValoraciones(nomRestaurante);
        if(valoraciones.isEmpty()) System.out.println("No hay valoraciones para el restaurante "+nomRestaurante+".");
        else{
            System.out.println("Las valoraciones del restaurante "+nomRestaurante+" son:");
            for(Object val : valoraciones){
                System.out.println(construyeTextoValoracion((JSONObject) val));
            }
        }
    }

    /**
     * 3. Hacer una valoración.
     * @param interfazServidor
     */
    private static void crearValoracion(IntServidorValora interfazServidor, Scanner teclado, String codcliente) throws IOException, ParseException {
        System.out.print("Introduce el nombre del restaurante: ");
        String nomRest = teclado.nextLine();
        long estrellas = leeEstrellas(teclado);
        System.out.print("Introduce un breve comentario con tu opinión sobre el restaurante: ");
        String comentario = teclado.nextLine();

        JSONObject valoracion = interfazServidor.hazValoracion(codcliente, nomRest, fechaHoy(), estrellas, comentario);
        if (valoracion == null || valoracion.isEmpty()) {
            System.out.print("No se ha podido realizar la valoración, ya has realizado una valoración " +
                    "a este restaurante en el mismo día.");
        } else {
            System.out.print("Valoracion realizada con éxito: \n" + construyeTextoValoracion(valoracion));
        }
    }

    /**
     * 4. Borrar una valoración tuya.
     * @param interfazServidor
     */
    private static void borrarValoracion(IntServidorValora interfazServidor, Scanner teclado, String codcliente) throws IOException, ParseException {
        System.out.print("Introduce el nombre del restaurante: ");
        String nomRest = teclado.nextLine();
        String fecha = leeFecha(teclado);
        JSONObject valoracion = interfazServidor.borraValoracion(codcliente,nomRest,fecha);
        if(valoracion.isEmpty()) System.out.println("No se ha podido borrar la valoración del restaurante "+
                nomRest+" y fecha "+fecha+" .");
        else{
            System.out.println("Se ha borrado la siguiente valoracion del restaurante: \n " + construyeTextoValoracion(valoracion) );
        }
    }

    /**
     * 5. Modificar una valoración tuya
     * @param interfazServidor
     */
    private static void modificarValoracion(IntServidorValora interfazServidor, Scanner teclado, String codcliente) throws IOException, ParseException {
        System.out.print("Introduce el nombre del restaurante: ");
        String nomRest = teclado.nextLine();
        String fecha = leeFecha(teclado);
        long estrellas = leeEstrellas(teclado);
        System.out.print("Introduce un breve comentario con tu opinión sobre el restaurante: ");
        String comentario = teclado.nextLine();

        JSONObject valoracion = interfazServidor.modificaValoracion(codcliente, nomRest, fecha, estrellas, comentario);
        if (valoracion == null || valoracion.isEmpty()) {
            System.out.print("No se ha podido realizar la modificación de la valoración.");
        } else {
            System.out.print("Valoracion actualizada con éxito: \n " + construyeTextoValoracion(valoracion));
        }
    }

    /**
     * 6. Suscribirse a un restaurante para recibir sus actualizaciones
     * @param interfazServidor
     */
    public static void suscribirARestaurante(IntCallbackCliente callbackObj, IntServidorValora interfazServidor,
                                             Scanner teclado, String codcliente) throws IOException, ParseException {
        System.out.print("Introduce el nombre del restaurante: ");
        String nomRest = teclado.nextLine();
        boolean resultadoSuscripcion = interfazServidor.registerForCallback(callbackObj, nomRest);
        if (resultadoSuscripcion)
            System.out.println("Suscripcion realizada para el restaurante " + nomRest);
        else
            System.out.println("No se ha podido suscribir al restaurante " + nomRest);
    }

    /**
     * 7. Darse de baja de un restaurante para no recibir más actualizaciones
     */
    public static void bajaDeRestaurante(IntCallbackCliente callbackObj, IntServidorValora interfazServidor,
                                         Scanner teclado, String codcliente) throws IOException, ParseException {
        System.out.print("Introduce el nombre del restaurante: ");
        String nomRest = teclado.nextLine();
        boolean resultadoSuscripcion = interfazServidor.unregisterForCallback(callbackObj, nomRest);
        if (resultadoSuscripcion)
            System.out.println("Baja realizada para el restaurante " + nomRest);
        else
            System.out.println("No se ha podido dar de baja del restaurante " + nomRest);
    }

    private static String construyeTextoValoracion(JSONObject valoracion) {
        return "El cliente " + valoracion.get("codcliente") + " ha valorado el restaurante " + valoracion.get("nomRest") +
                " con " + valoracion.get("estrellas") + " estrellas en la fecha " +  valoracion.get("fecha") +
                " y ha añadido el comentario: \"" + valoracion.get("comentario") + "\".";
    }
} // fin class

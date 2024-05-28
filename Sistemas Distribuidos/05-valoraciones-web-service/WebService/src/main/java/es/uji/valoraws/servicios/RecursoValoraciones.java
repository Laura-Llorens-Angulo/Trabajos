package es.uji.valoraws.servicios;


import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;

import es.uji.valoraws.modelo.GestorValoraciones;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


@Path("valoraciones")
public class RecursoValoraciones {

	final private GestorValoraciones gestor;

	/**
	 * Constructor por defecto
	 */	
	public RecursoValoraciones() {
		super();
		gestor = new GestorValoraciones();
		System.out.println("construyo RecursoValoraciones");
	}

	/**
	 * Cuando cada cliente cierra su sesión volcamos los datos en el fichero para mantenerlos actualizados
	 */
	@PUT
	@Path("guardaDatos")
	public Response guardaDatos() {
		System.out.println("Guardando los datos... ");
		gestor.guardaDatos();
		ResponseBuilder builder = Response.ok();
		return builder.build();
	}

	/**
	 * Devuelve los nombres de los restaurantes
	 *
	 * @return JSONArray de restaurantes. Vacío si no hay ningún restaurante
	 */
	@GET
	@Path("listaRestaurantes")
	@Produces("text/plain")
	public Response listaRestaurantes() {
		System.out.println("Consultando restaurantes...");
		String resultado = "";
		JSONArray restaurantes = gestor.listaRestaurantes();

		if (restaurantes.isEmpty()) {
			return Response.status(Response.Status.NOT_FOUND).build();
		} else {
			resultado = restaurantes.toJSONString();
		}
		ResponseBuilder builder = Response.ok(resultado);
		return builder.build();
	}


	/**
	 * Devuelve las valoraciones de un restaurante dado
	 *
	 * @param nomRest	nombre del restaurante
	 * @return JSONArray de valoraciones del restaurante. Vacío si no hay valoraciones de ese restaurante
	 */
	@GET
	@Path("consultaValoraciones/{nomRest}")
	@Produces("text/plain")
	public Response consultaValoraciones(@PathParam("nomRest") String nomRest) {
		System.out.println("Consultando valoraciones del restaurante" + nomRest +"...");
		String resultado = "";
		JSONArray valoraciones = gestor.consultaValoraciones(nomRest);

		if (valoraciones.isEmpty()) {
			return Response.status(Response.Status.NOT_FOUND).build();
		} else {
			resultado = valoraciones.toJSONString();
		}
		ResponseBuilder builder = Response.ok(resultado);
		return builder.build();
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
	@POST
	@Path("hazValoracion/{codcliente}/{nomRest}")
	@Produces("text/plain")
	public Response hazValoracion(@PathParam("codcliente") String codcliente,
								  @PathParam("nomRest") String nomRest,
								  @QueryParam("fecha") String fecha,
								  @QueryParam("estrellas") long estrellas,
								  @QueryParam("comentario") String comentario
								 ) {
		System.out.println("Realizando valoracion...");
		String resultado = "";
		JSONObject valoracion = gestor.hazValoracion(codcliente,nomRest,fecha,estrellas,comentario);

		if (valoracion.isEmpty()) {
			return Response.status(Response.Status.NOT_FOUND).build();
		} else {
			resultado = valoracion.toJSONString();
		}
		ResponseBuilder builder = Response.ok(resultado);
		return builder.build();
	}

	/**
	 * El cliente codcli borra una valoracion
	 *
	 * @param codcliente	código del cliente que borra la valoración
	 * @param nomRest		nombre del restaurante
	 * @param fecha			fecha de la valoración a borrar
	 * @return	JSON de la valoración borrada. JSON vacío si no se ha borrado
	 */
	@DELETE
	@Path("borraValoracion/{codcliente}/{nomRest}")
	@Produces("text/plain")
	public Response borraValoracion(@PathParam("codcliente") String codcliente,
									@PathParam("nomRest") String nomRest,
									@QueryParam("fecha") String fecha) {
		System.out.println("Borrando valoracion...");
		String resultado = "";
		JSONObject valoracion = gestor.borraValoracion(codcliente,nomRest,fecha);

		if (valoracion.isEmpty()) {
			return Response.status(Response.Status.NOT_FOUND).build();
		} else {
			resultado = valoracion.toJSONString();
		}
		ResponseBuilder builder = Response.ok(resultado);
		return builder.build();
	}

	/**
	 * El cliente codcliente modifica las estrellas y/o el comentario de una valoración propia
	 *
	 * @param codcliente	código del cliente que modifica su valoración
	 * @param nomRest		nombre del restaurante
	 * @param fecha			fecha en que se realizo la valoración
	 * @param estrellas			fecha en que se realizo la valoración
	 * @param comentario			fecha en que se realizo la valoración
	 * @return	JSON de la valoración modificada. JSON vacío si no se ha modificado
	 */
	@PUT
	@Path("modificaValoracion/{codcliente}/{nomRest}")
	@Produces("text/plain")
	public Response modificaValoracion(@PathParam("codcliente") String codcliente,
									   @PathParam("nomRest") String nomRest,
									   @QueryParam("fecha") String fecha,
									   @QueryParam("estrellas") long estrellas,
									   @QueryParam("comentario") String comentario
										) {
		System.out.println("Modificando valoracion...");
		String resultado = "";
		JSONObject valoracion = gestor.modificaValoracion(codcliente,nomRest,fecha,estrellas,comentario);

		if (valoracion.isEmpty()) {
			return Response.status(Response.Status.NOT_FOUND).build();
		} else {
			resultado = valoracion.toJSONString();
		}
		ResponseBuilder builder = Response.ok(resultado);
		return builder.build();
	}



}

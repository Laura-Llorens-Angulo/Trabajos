package es.uji.valoraservlets.modelo;

import org.json.simple.JSONObject;

import java.io.Serializable;

public class Valoracion implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Clase Valoracion
	 */
	private String codcliente;	// código único del cliente que hace la valoracion
	private String nomRest;     // Nombre único del restaurante
    private String fecha;		// fecha de la valoracion
    private long estrellas;     // numero de estrellas de valoracion (0 a 5)
    private String comentario;	// Breve comentario de valoracion (0 a 40 caracteres)

	// Supondremos que un cliente no puede hacer dos valoraciones de un mismo restaurante el mismo día,
	// aunque puede modificar una que ha hecho ese mismo día.

	/**
	 * 	 * Constructor con los atributos de la clase
	 *
	 *	 * @param codcliente	código del cliente
	 * 	 * @param nomRest		nombre del restaurante
	 * 	 * @param fecha			fecha de la valoración
	 * 	 * @param estrellas		número de estrellas
	 * 	 * @param comentario	comentario
	 *

	 */
	public Valoracion(String codcliente, String nomRest, String fecha, long estrellas, String comentario) {
		super();
		this.codcliente = codcliente;
		this.nomRest = nomRest;
		this.fecha = fecha;
		this.estrellas = estrellas;
		this.comentario = comentario;
	}

	/**
	 * Constructor de una Valoración a partir de su representación en formato JSON
	 *
	 * @param jsonValoracion	objeto JSON con los datos de una valoración
	 */
	public Valoracion(JSONObject jsonValoracion) { //IMPLEMENTADO
		super();
		this.codcliente = (String) jsonValoracion.get("codcliente");
		this.nomRest = (String) jsonValoracion.get("nomRest");
		this.fecha = (String) jsonValoracion.get("fecha");
		this.estrellas = (long) jsonValoracion.get("estrellas");
		this.comentario = (String) jsonValoracion.get("comentario");
	}

	/**
	 * Devuelve los datos de la valoración como cadena en formato JSOM
	 *
	 * @return	cadena en formato JSON con los datos de la valoración
	 */
	@Override
	public String toString() {
		return this.toJSON().toJSONString();
	}

	/**
	 * Devuelve un objeto JSON con los datos de la valoración
	 *
	 * @return	objeto JSON con los datos de la valoración
	 */
	@SuppressWarnings("unchecked")
	public JSONObject toJSON() {// MODIFICADO
		JSONObject valoracion = new JSONObject();
		valoracion.put("codcliente",codcliente);
		valoracion.put("nomRest",nomRest);
		valoracion.put("fecha",fecha);
		valoracion.put("estrellas",estrellas);
		valoracion.put("comentario",comentario);
		return valoracion;
	}


	public String getCodcliente() {
		return codcliente;
	}

	public void setCodcliente(String codcliente) {
		this.codcliente = codcliente;
	}

	public String getNomRest() {
		return nomRest;
	}

	public void setNomRest(String nomRest) {
		this.nomRest = nomRest;
	}


	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public long getEstrellas() {
		return estrellas;
	}

	public void setEstrellas(long estrellas) {
		this.estrellas = estrellas;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

   
} // fin clase Valoracion

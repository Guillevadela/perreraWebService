package com.ipartek.formacion.perrera.pojo;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Clase para obtener la fecha y hora actuales
 * 
 * @author JHM
 *
 */
public class FechaHora {
	private Timestamp fechaHora;

	/**
	 * Costructor vacio
	 */
	public FechaHora() {
		super();
		Date date = new Date();
		this.setFechaHora(new Timestamp(date.getTime()));
	}

	/**
	 * Método GET que devuelve la fecha y hora actuales
	 * 
	 * @return fechaHora Fecha y hora actuales
	 */
	public Timestamp getFechaHora() {
		return fechaHora;
	}

	/**
	 * Método SET para modificar la fecha y hora actual
	 * 
	 * @param fechaHora
	 *            Valores de fecha y hora nuevos
	 */
	public void setFechaHora(Timestamp fechaHora) {
		this.fechaHora = fechaHora;
	}
}
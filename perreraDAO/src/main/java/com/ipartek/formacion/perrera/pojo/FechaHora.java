package com.ipartek.formacion.perrera.pojo;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 
 * @author ADassoy
 * 
 *         Obtener fecha y hora en Timestamp
 *
 */
public class FechaHora {
	/**
	 * Timestamp fechaHora
	 */
	private Timestamp fechaHora;

	/**
	 * constructor
	 */
	public FechaHora() {
		super();
		final Date date = new Date();
		this.setFechaHora(new Timestamp(date.getTime()));
	}

	/**
	 * GET
	 * 
	 * @return fechahora
	 */
	public Timestamp getFechaHora() {
		return fechaHora;
	}

	/**
	 * SET
	 * 
	 * @param fechaHora
	 */
	final public void setFechaHora(final Timestamp fechaHora) {
		this.fechaHora = fechaHora;
	}
}

package com.ipartek.formacion.perrera.pojo;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * POJO Perro
 * 
 * @author Jon Fraile
 */
@Entity
@Table(name = "perro")
public class Perro {

	// clave y se genera automaticamente
	@Id
	@GeneratedValue
	private long id;

	// Persistente, un tipo basico (string)
	@Basic
	@Column(name = "nombre")
	private String nombre;

	@Basic
	@Column(name = "raza")
	private String raza;

	/**
	 * Constructor de Perro pasandole parametros
	 * 
	 * @param nombre
	 *            &lt;String&gt;
	 * @param raza
	 *            &lt;String&gt;
	 */
	public Perro(String nombre, String raza) {
		super();
		this.nombre = nombre;
		this.raza = raza;
	}

	/**
	 * Constructor de Perro sin pasarle parametros
	 */
	public Perro() {
		super();
		this.nombre = "";
		this.raza = "";
	}

	/**
	 * Retorna el nombre del perro
	 * 
	 * @return nombre &lt;String&gt;
	 */
	public String getNombre() {
		return this.nombre;
	}

	/**
	 * Retorna la id del perro
	 * 
	 * @return id &lt;long&gt;
	 */
	public long getId() {
		return this.id;
	}

	/**
	 * Establece un valor al campo id
	 * 
	 * @param id
	 *            &lt;long&gt;
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Establece un valor al campo nombre
	 * 
	 * @param nombre
	 *            &lt;String&gt;
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Retorna la raza
	 * 
	 * @return raza &lt;String&gt;
	 */
	public String getRaza() {
		return this.raza;
	}

	/**
	 * Establece un valor al campo raza
	 * 
	 * @param raza
	 *            &lt;String&gt;
	 */
	public void setRaza(String raza) {
		this.raza = raza;
	}

	/**
	 * Si id = 0 es nuevo perro que no se ha persistido en una BBDD, cualquier
	 * id superior a 0 es perro persistido
	 * 
	 * @return True si el perro es nuevo
	 */
	public boolean isNew() {
		return this.id > 0 ? true : false;
	}

	@Override
	public String toString() {
		return "Perro [nombre=" + this.nombre + ", raza=" + this.raza + "]";
	}

}
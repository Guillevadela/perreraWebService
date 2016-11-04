package com.ipartek.formacion.perrera.pojo;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity()
@Table(name = "perro")
public class Perro {

	@Id()
	@GeneratedValue()
	// clave y se genera automaticamente
	private long id;

	/* Persistente, un tipo basico (string) */
	@Basic()
	@Column(name = "nombre")
	private String nombre;

	@Basic()
	@Column(name = "raza")
	private String raza;

	/**
	 * @param nombre
	 *            - nombre del perro
	 * @param raza
	 *            - raza del perro
	 */
	public Perro(final String nombre, final String raza) {
		super();
		this.nombre = nombre;
		this.raza = raza;
	}

	public Perro() {
		super();
		this.nombre = "";
		this.raza = "";
	}

	public String getNombre() {
		return this.nombre;
	}

	public long getId() {
		return this.id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public void setNombre(final String nombre) {
		this.nombre = nombre;
	}

	public String getRaza() {
		return this.raza;
	}

	public void setRaza(final String raza) {
		this.raza = raza;
	}

	/**
	 * Si id = 0 es nuevo perro que no se ha persistido en una BBDD, cualquier
	 * id superior a 0 es perro persistido
	 * 
	 * @return booleano depdendiendo de si el perro es nuevo o no.
	 */
	public boolean isNew() {
		return this.id > 0 ? true : false;
	}

	@Override()
	public String toString() {
		return "Perro [nombre=" + this.nombre + ", raza=" + this.raza + "]";
	}

}

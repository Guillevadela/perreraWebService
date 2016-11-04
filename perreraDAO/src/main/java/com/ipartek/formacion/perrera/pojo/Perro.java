package com.ipartek.formacion.perrera.pojo;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Clase Perro
 * 
 * @author JHM
 *
 */
@Entity()
@Table(name = "perro")
public class Perro {

	@Id()
	@GeneratedValue()
	private long id;// clave y se genera automaticamente

	/* Persistente, un tipo basico (string) */
	@Basic()
	@Column(name = "nombre")
	private String nombre;

	@Basic()
	@Column(name = "raza")
	private String raza;

	/**
	 * @param nombre
	 *            nombre del perro
	 * @param raza
	 *            raza del perro
	 */
	public Perro(String nombre, String raza) {
		super();
		this.nombre = nombre;
		this.raza = raza;
	}

	/**
	 * Constructor vacio para el objeto Perro
	 */
	public Perro() {
		super();
		this.nombre = "";
		this.raza = "";
	}

	/**
	 * Método GET para el 'nombre' del Perro
	 * 
	 * @return String Devuelve un String con el nombre del Perro
	 */
	public String getNombre() {
		return this.nombre;
	}

	/**
	 * Método GET para el 'id' del Perro
	 * 
	 * @return long Devuelve un long con el 'id' del Perro
	 */
	public long getId() {
		return this.id;
	}

	/**
	 * Método SET que actualiza el 'id' del Perro
	 * 
	 * @param id
	 *            Id del Perro
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Método SET que actualiza el nombre del Perro
	 * 
	 * @param nombre
	 *            Nombre del Perro
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Método GET para la 'raza' del Perro
	 * 
	 * @return String Devuelve un String con la raza del Perro
	 */
	public String getRaza() {
		return this.raza;
	}

	/**
	 * Método SET que actualiza la raza del Perro
	 * 
	 * @param raza
	 *            Raza del Perro
	 */
	public void setRaza(String raza) {
		this.raza = raza;
	}

	/**
	 * Si id = 0 es nuevo perro que no se ha persistido en una BBDD, cualquier
	 * id superior a 0 es perro persistido
	 * 
	 * @return true si id &gt; 0 false si id &lt; 0
	 */
	public boolean isNew() {
		return this.id > 0 ? true : false;
	}

	@Override()
	public String toString() {
		return "Perro [nombre=" + this.nombre + ", raza=" + this.raza + "]";
	}

}

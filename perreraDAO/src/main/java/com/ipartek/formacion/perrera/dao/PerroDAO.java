package com.ipartek.formacion.perrera.dao;

import java.util.List;

import com.ipartek.formacion.perrera.pojo.Perro;

public interface PerroDAO {

	/**
	 * Lista todos los Perros ordenandolos en orden ascendente o descendente
	 * y por campo facilitados mediante los parametros 'order' y 'campo' respectivamente
	 * Devuelve una lista de Perros
	 */
	List<Perro> getAll(String order, String campo);

	/**
	 * Devuelve un Perro pasandole su 'id'
	 */
	Perro getById(int idPerro);

	/**
	 * Elimina un Perro por su 'id'
	 * Devuelve true si lo elimina con exito o false si no consigue eliminarlo
	 */
	boolean delete(int idPerro);

	/**
	 * Modifica un Perro
	 * Devuelve true si lo modifica con exito o false si no consigue modificarlo
	 */
	boolean update(Perro perro);

	/**
	 * Crea un Perro
	 * Devuelve true si lo crea con exito o false si no consigue crearlo
	 */
	boolean insert(Perro perro);
}

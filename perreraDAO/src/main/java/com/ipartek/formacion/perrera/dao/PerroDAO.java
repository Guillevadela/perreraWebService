package com.ipartek.formacion.perrera.dao;

import java.util.List;

import com.ipartek.formacion.perrera.pojo.Perro;

/**
 * Interface que obliga a todas las clases que extiendan de ella a implementar
 * las funciones CRUD
 * 
 * @author JHM
 *
 */
public interface PerroDAO {

	/**
	 * Implementar método que devuelve lista de perros ordenada por campo
	 * seleccionado
	 * 
	 * @param order
	 *            tipo de ordenación. Posibles valores 'asc'/'desc'
	 * @param campo
	 *            campo por el que se va a realizar la ordenación.Posibles
	 *            valores 'id'/'nombre'/'raza'
	 * @return List&gt;Perro&lt; retorna una lista de objetos Perro
	 */
	List<Perro> getAll(String order, String campo);

	/**
	 * Implemetar método que devuelva un Perro de la BBDD por su 'id'
	 * 
	 * @param idPerro
	 *            id del Perro a buscar
	 * @return Perro Devuelve un objeto Perro con id='idPerro'
	 */
	Perro getById(long idPerro);

	/**
	 * Implementar método para eliminar un Perro de la BBDD por su 'id'
	 * 
	 * @param idPerro
	 *            id del Perro a eliminar de la BBDD
	 * @return boolean Devuelve 'true' si el Perro se borra OK, 'false' en caso
	 *         contrario
	 */
	boolean delete(long idPerro);

	/**
	 * Implementar método para modificar los datos de un Perro de la BBDD
	 * 
	 * @param perro
	 *            Objeto Perro a modificar
	 * @return boolean Devuelve 'true' si el Perro se borra OK, 'false' en caso
	 *         contrario
	 */
	boolean update(Perro perro);

	/**
	 * Implementar método para crear un nuevo Perro en la BBDD
	 * 
	 * @param perro
	 *            Objeto Perro a crear en la BBDD
	 * @return boolean Devuelve 'true' si el Perro se borra OK, 'false' en caso
	 *         contrario
	 */
	boolean insert(Perro perro);
}

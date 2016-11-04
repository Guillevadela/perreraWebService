package com.ipartek.formacion.perrera.dao;

import java.util.List;

import com.ipartek.formacion.perrera.pojo.Perro;

/**
 * interface del DAO de PerreraWebService
 * 
 * @author EkaitzAF
 *
 */
public interface PerroDAO {

	/**
	 * Función a implementar que devuelve una lista de perros
	 * 
	 * @param order
	 *            orden de lista
	 * @param campo
	 *            campo por el que ordenar
	 * @return <List> lista de perros
	 */
	List<Perro> getAll(String order, String campo);

	/**
	 * Función a implementar que devuelve una perro mediante busqueda por id
	 * 
	 * @param idPerro
	 *            id de perro a encontrar
	 * @return Perro buscado por id
	 */
	Perro getById(long idPerro);

	/**
	 * Función a implementar que elimina un perro mediante busqueda por id
	 * 
	 * @param idPerro
	 *            id de perro a eliminar
	 * @return boolean true si eliminado false si no
	 */
	boolean delete(long idPerro);

	/**
	 * Función a implementar que actualiza un perro
	 * 
	 * @param perro
	 *            a modificar
	 * @return boolean true si modificado false si no
	 */
	boolean update(Perro perro);

	/**
	 * Función a implementar que inserta un perro
	 * 
	 * @param perro
	 *            a insertar
	 * @return boolean true si insertado false si no
	 */
	boolean insert(Perro perro);
}

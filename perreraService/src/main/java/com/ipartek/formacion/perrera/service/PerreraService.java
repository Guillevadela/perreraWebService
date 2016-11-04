package com.ipartek.formacion.perrera.service;

import java.util.List;

import com.ipartek.formacion.perrera.pojo.Perro;

/**
 * @author Curso
 *
 *         Interface de la capa Servicio que declara la logica de negocio de
 *         perreraWebService
 */

public interface PerreraService {

	/**
	 * Retorna listado perros creados
	 * 
	 * @param order
	 *            orden por el que se puede ordenar la lista. Posible valores
	 *            asc/desc
	 * @param campo
	 *            campo por el que realizar la ordenación
	 * @return List &gt;Perro&lt;
	 */
	List<Perro> getAll(String order, String campo);

	/**
	 * Busca perro por su identificador
	 * 
	 * @param idPerro
	 *            id del perro a buscar
	 * @return Perro si encuentra, null si no existe
	 */
	Perro getById(long idPerro);

	/**
	 * Elimina Perro por su identificador
	 * 
	 * @param idPerro
	 *            id del perro a borrar
	 * @return boolean 'true' si el perro se ha borrado con éxito, 'false' si no
	 *         se ha borrado
	 */
	boolean delete(long idPerro);

	/**
	 * Modifica un Perro
	 * 
	 * @param perro
	 *            Objeto Perro a modificar
	 * @return Perro salvado
	 */
	boolean update(Perro perro);

	/**
	 * Crea un nuevo Perro
	 * 
	 * @param perro
	 *            Objeto Perro a crear
	 * @return Perro creado
	 */
	boolean insert(Perro perro);

}

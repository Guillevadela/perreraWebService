package com.ipartek.formacion.perrera.service;


import java.util.List;

import com.ipartek.formacion.perrera.pojo.Perro;

/**
 * @author Curso
 *
 * Interface de la capa Servicio
 * que declara la logica de negocio de perreraWebService
 */

 
public interface perreraService  {
	
	/**
	 * Retorna listado perros creados
	 * @return
	 */
	List<Perro> getAll(String order, String campo);

	
	/**
	 * Busca perro por su identificador
	 * @param id
	 * @return Perro si encuentra, null si no existe
	 */
	Perro getById(long idPerro);

	/**
	 * Elimina Perro por su identificador
	 * @param id
	 * @return 
	 */
	boolean delete(long idPerro);

	/**
	 * Modifica un Planeta
	 * @param perro
	 * @return Perro salvado
	 */
	boolean update(Perro perro);

	/**
	 * Crea un nuevo Perro
	 * @param perro
	 * @return Perro creado
	 */
	boolean insert(Perro perro);

}

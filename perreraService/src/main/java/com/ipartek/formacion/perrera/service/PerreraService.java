package com.ipartek.formacion.perrera.service;

import java.util.List;

import com.ipartek.formacion.perrera.pojo.Perro;

/**
 * 
 * @author Adassoy
 *
 */
interface PerreraService {
	/**
	 * 
	 * @param order
	 * @param campo
	 * @return
	 */

	List<Perro> getAll(String order, String campo);

	/**
	 * 
	 * @param idPerro
	 * @return
	 */

	Perro getById(long idPerro);

	/**
	 * 
	 * @param idPerro
	 * @return
	 */

	boolean delete(long idPerro);

	/**
	 * 
	 * @param perro
	 * @return
	 */

	boolean update(Perro perro);

	/**
	 * 
	 * @param perro
	 * @return
	 */

	boolean insert(Perro perro);

}

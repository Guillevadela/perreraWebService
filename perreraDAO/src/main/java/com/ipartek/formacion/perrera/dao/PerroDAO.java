package com.ipartek.formacion.perrera.dao;

import java.util.List;

import com.ipartek.formacion.perrera.pojo.Perro;

/**
 * 
 * @author Adassoy
 * 
 *         Interface PerroDAO
 *
 */
public interface PerroDAO {
	/**
	 * 
	 * @param order
	 *            String
	 * @param campo
	 *            String
	 * @return listado completo
	 */

	List<Perro> getAll(String order, String campo);

	/**
	 * 
	 * @param idPerro
	 *            long
	 * @return perro por id
	 */
	Perro getById(long idPerro);

	/**
	 * 
	 * @param idPerro
	 *            long
	 * @return boolean
	 */
	boolean delete(long idPerro);

	/**
	 * 
	 * @param perro
	 *            Objeto tipo Perro
	 * @return boolean
	 */
	boolean update(Perro perro);

	/**
	 * 
	 * @param perro
	 *            Objeto tipo Perro
	 * @return boolean
	 */
	boolean insert(Perro perro);
}

package com.ipartek.formacion.perrera.dao;

import java.util.List;

import com.ipartek.formacion.perrera.pojo.Perro;

/**
 * Interface DAO
 * 
 * @author Jon Fraile
 *
 */
public interface PerroDAO {

	/**
	 * Metodo que devuelve una lista de perros
	 * 
	 * @param order
	 *            Modo de ordenacion de la lista.<br>
	 *            Posibles valores asc/desc.
	 * @param campo
	 *            Campo por el que se va a ordenar. <br>
	 *            Posibles valores id/nombre/raza.
	 * @return List &lt;Perro&gt; Retorna Lista con los perros.
	 */
	List<Perro> getAll(String order, String campo);

	/**
	 * Metodo que devuelve un perro por Id
	 * 
	 * @param idPerro &lt;long&gt;
	 *       
	 * @return resul &lt;boolean&gt;
	 * 			Retorna "True" si la accion <br>
	 *          ha sido realiaza correctamente.
	 */
	Perro getById(long idPerro);

	/**
	 * Metodo que elimina un perro por id
	 * 
	 * @param idPerro
	 *            &lt;long&gt;
	 * 
	 * True si se ha realizado correctamente
	 */
	boolean delete(long idPerro);

	/**
	 * Metodo que modifica un perro
	 * 
	 * @param perro &lt;Perro&gt;
	 * 
	 * True si se ha realizado correctamente
	 */
	boolean update(Perro perro);

	/**
	 * Metodo que modifica un perro
	 * 
	 * @param perro &lt;Perro&gt;
	 *	
	 * True si se ha realizado correctamente
	 */
	boolean insert(Perro perro);
}
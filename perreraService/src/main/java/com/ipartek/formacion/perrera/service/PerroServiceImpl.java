package com.ipartek.formacion.perrera.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ipartek.formacion.perrera.dao.PerroDAOImpl;
import com.ipartek.formacion.perrera.pojo.Perro;

/**
 * Clase encargada del servicio de perreraWebService
 * 
 * @author EkaitzAF
 *
 */
public final class PerroServiceImpl implements PerroService { // NOPMD by Curso
																// on 3/11/16
																// 17:23
	/**
	 * log para las trazas
	 */
	protected final Logger log = LoggerFactory.getLogger(getClass());

	/**
	 * instancia unica para 'patron Singleton'
	 */
	private static PerroServiceImpl instance = null;

	// constructor privado para que no se pueda instanciar esta clase
	private PerroServiceImpl() {
		super();
	}

	/**
	 * unico metodo para crear un objeto de esta Clase
	 * 
	 * @return instancia
	 */
	public synchronized static PerroServiceImpl getInstance() {
		if (instance == null) {
			instance = new PerroServiceImpl();
		}
		return instance;
	}

	/**
	 * Función que devuelve una lista de perros
	 * 
	 * @param order
	 *            orden de lista
	 * @param campo
	 *            campo por el que ordenar
	 * @return Array<List> lista de perros
	 */
	@Override()
	public List<Perro> getAll(final String order, final String campo) {
		this.log.trace("Llamada a dao: Obteniendo lista de perros");
		final PerroDAOImpl dao = PerroDAOImpl.getInstance();
		return (ArrayList<Perro>) dao.getAll(order, campo);
	}

	/**
	 * Función que devuelve una perro mediante busqueda por id
	 * 
	 * @param idPerro
	 *            id de perro a encontrar
	 * @return Perro buscado por id
	 */
	@Override()
	public Perro getById(final long idPerro) {
		this.log.trace("Llamada a dao: Obteniendo perro con su id");
		final PerroDAOImpl dao = PerroDAOImpl.getInstance();
		return (Perro) dao.getById(idPerro);
	}

	/**
	 * Función que elimina un perro mediante busqueda por id
	 * 
	 * @param idPerro
	 *            id de perro a eliminar
	 * @return boolean true si eliminado false si no
	 */
	@Override()
	public boolean delete(final long idPerro) {
		this.log.trace("Llamada a dao: Eliminando perro con id:" + idPerro);
		final PerroDAOImpl dao = PerroDAOImpl.getInstance();

		return dao.delete(idPerro);
	}

	/**
	 * Función que inserta un perro
	 * 
	 * @param perro
	 *            a insertar
	 * @return boolean true si insertado false si no
	 */
	@Override()
	public boolean insert(final Perro perro) {
		this.log.trace(
				"Llamada a dao: Insertando perro nuevo con nombre:" + perro.getNombre() + " y raza:" + perro.getRaza());
		final PerroDAOImpl dao = PerroDAOImpl.getInstance();
		return dao.insert(perro);
	}

	/**
	 * Función que actualiza un perro
	 * 
	 * @param perro
	 *            a modificar
	 * @return boolean true si modificado false si no
	 */
	@Override()
	public boolean update(final Perro perro) {
		this.log.trace("Llamada a dao: Modificando perro con id:" + perro.getId());
		final PerroDAOImpl dao = PerroDAOImpl.getInstance();
		return dao.update(perro);
	}

}

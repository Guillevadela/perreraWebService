package com.ipartek.formacion.perrera.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ipartek.formacion.perrera.dao.PerroDAOImpl;
import com.ipartek.formacion.perrera.pojo.Perro;

/**
 * 
 * @author ADassoy
 * 
 *         Capa Service que une PerroController con PerroDAOImpl
 *
 */
public final class PerreraServiceImpl implements PerreraService {

	protected final Logger log = LoggerFactory.getLogger(getClass());
	// instancia unica para 'patron Singleton'
	/**
	 * instancia de PerreraServiceImpl
	 */
	private static PerreraServiceImpl instance;

	// constructor privado para que no se pueda instanciar esta clase
	private PerreraServiceImpl() {
		super();
	}

	// unico metodo para crear un objeto de esta Clase
	/**
	 * Patron singleton
	 * 
	 * @return instancia de PerreraServiceImpl
	 */
	public synchronized static PerreraServiceImpl getInstance() {
		if (instance == null) {
			instance = new PerreraServiceImpl();
		}
		return instance;
	}

	/**
	 * Servicio para realizar el listado completo de perros
	 * 
	 * @param order
	 * @param campo
	 * 
	 * @return lista de perros retornada por el dao
	 */
	@Override()
	public List<Perro> getAll(final String order, final String campo) {
		this.log.info("Peticion del servicio para listar todos los perros");
		final PerroDAOImpl dao = PerroDAOImpl.getInstance();
		return (ArrayList<Perro>) dao.getAll(order, campo);
	}

	/**
	 * Servicio para botener un perro por id
	 * 
	 * @param idPerro
	 *            - variable de tipo long
	 * 
	 * @return objeto de tipo perro obtenido del dao
	 */
	@Override()
	public Perro getById(final long idPerro) {
		this.log.info("Peticion del servicio para listar un perro por 'id'");
		final PerroDAOImpl dao = PerroDAOImpl.getInstance();
		return (Perro) dao.getById(idPerro);
	}

	/**
	 * Servicio para eliminar un perro por su id
	 * 
	 * @param idPerro
	 *            - variable de tipo long
	 */
	@Override()
	public boolean delete(final long idPerro) {
		this.log.info("Peticion del servicio para eliminar un perro por 'id'");
		final PerroDAOImpl dao = PerroDAOImpl.getInstance();
		return dao.delete(idPerro);
	}

	/**
	 * Servicio para insertar un perro
	 */
	@Override()
	public boolean insert(final Perro perro) {
		this.log.info("Peticion del servicio para añadir un perro");
		final PerroDAOImpl dao = PerroDAOImpl.getInstance();
		return dao.insert(perro);
	}

	/**
	 * servicio para actualizar un perro
	 */
	@Override()
	public boolean update(final Perro perro) {
		this.log.info("Peticion del servicio para modificar un perro");
		final PerroDAOImpl dao = PerroDAOImpl.getInstance();
		return dao.update(perro);
	}

}

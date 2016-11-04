package com.ipartek.formacion.perrera.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.QueryException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ipartek.formacion.perrera.pojo.Perro;
import com.ipartek.formacion.perrera.util.HibernateUtil;

/**
 * Clase encargada del modelo de perreraWebService
 * 
 * @author EkaitzAF
 *
 */
public final class PerroDAOImpl implements PerroDAO {
	/**
	 * log para trazas
	 */
	protected final Logger log = LoggerFactory.getLogger(getClass());
	/**
	 * instancia unica para 'patron Singleton'
	 */
	private static PerroDAOImpl instance;

	// constructor privado para que no se pueda instanciar esta clase
	private PerroDAOImpl() {
		super();
	}

	/**
	 * unico metodo para crear un objeto de esta Clase
	 * 
	 * @return instancia de PerroDAOImpl
	 */
	public synchronized static PerroDAOImpl getInstance() {
		if (instance == null) {
			instance = new PerroDAOImpl();
		}
		return instance;
	}

	/**
	 * Función que devuelve una lista de perros
	 * 
	 * @param order
	 *            Modo de ordenacion de la lista.<br>
	 *            Posibles valores asc/desc
	 * @param campo
	 *            Campo por el que se va a ordenar. <br>
	 *            Posibles valores id/nombre/raza
	 * @return List<Perro>
	 */
	public List<Perro> getAll(final String order, final String campo) {
		this.log.trace("DAO: Obteniendo lista de perros");
		// inicializamos lista como un ArrayList de objetos Perro
		ArrayList<Perro> lista = new ArrayList<Perro>();
		// obtenemos la sesion
		final Session session = HibernateUtil.getSession();
		try {

			try {
				if ("desc".equals(order)) {
					this.log.trace("DAO: Obteniendo lista de perros por orden desc");
					lista = (ArrayList<Perro>) session.createCriteria(Perro.class).addOrder(Order.desc(campo)).list();
				} else {
					this.log.trace("DAO: Obteniendo lista de perros por orden asc");
					lista = (ArrayList<Perro>) session.createCriteria(Perro.class).addOrder(Order.asc(campo)).list();
				}
				// Si falla porque esta mal la Query, por ejemplo una columna
				// que no existe
				// retorno listado perros ordenados por id desc
			} catch (QueryException e) {
				this.log.error("QueryException: mostrar lista de perros orden desc por id");
				lista = (ArrayList<Perro>) session.createCriteria(Perro.class).addOrder(Order.desc("id")).list();
			}

		} catch (Exception e) {
			this.log.error("Exception: no se ha podido mostrar lista de perros");
		} finally {
			// cerramos la transaccion
			session.close();
		}
		this.log.info("DAO: Devolviendo lista de perros");
		return lista;
	}

	/**
	 * Función que devuelve una perro mediante busqueda por id
	 * 
	 * @param idPerro
	 *            Id del perro que se quiere buscar<br>
	 *            Posibles valores 0 < X
	 * 
	 * @return Perro
	 * 
	 */
	public Perro getById(final long idPerro) {
		this.log.trace("DAO: Obteniendo perro por id:" + idPerro);
		Perro resul = null;

		final Session session = HibernateUtil.getSession();
		try {
			resul = (Perro) session.get(Perro.class, idPerro);
			if (resul == null) {
				resul = new Perro();
			}
		} catch (Exception e) {
			this.log.error("Exception: no se ha podido encontrar perro con id" + idPerro);
		} finally {
			session.close();
		}
		this.log.info("DAO: Perro obtenido id:" + idPerro);
		return resul;
	}

	/**
	 * Función que elimina un perro mediante busqueda por id
	 * 
	 * @param idPerro
	 *            Id del perro que se quiere eliminar<br>
	 *            Posibles valores 0 < X
	 * 
	 * @return boolean true si eliminado, false si no.
	 * 
	 */
	public boolean delete(final long idPerro) {
		this.log.trace("DAO: Eliminando perro por id:" + idPerro);
		Perro pElimnar = null;
		boolean resul = false;
		final Session session = HibernateUtil.getSession();
		try {
			session.beginTransaction();
			this.log.trace("DAO: Buscando perro por id:" + idPerro);
			pElimnar = (Perro) session.get(Perro.class, idPerro);
			if (pElimnar != null) {
				this.log.trace("DAO: Perro con id:" + idPerro + " existente a eliminar");
				session.delete(pElimnar);
				session.beginTransaction().commit();
				resul = true;
			}
		} catch (final Exception e) {
			this.log.error("Exception: no se ha podido eliminar perro con id" + idPerro);
			session.beginTransaction().rollback();
		} finally {
			session.close();
		}
		this.log.info("DAO: Resultado de perro con id:" + idPerro + " eliminado: " + resul);
		return resul;
	}

	/**
	 * Función que actualiza un perro
	 * 
	 * @param perro
	 *            Perro que se quiere modificar<br>
	 * 
	 * 
	 * @return boolean true si modificado, false si no.
	 * 
	 */
	public boolean update(final Perro perro) {
		this.log.trace("DAO: Modificando perro con id:" + perro.getId());
		boolean resul = false;
		final Session session = HibernateUtil.getSession();
		try {
			session.beginTransaction();
			session.update(perro);
			session.beginTransaction().commit();
			resul = true;
		} catch (final Exception e) {
			this.log.error("Exception: no se ha podido modificar perro con id" + perro.getId());
			session.beginTransaction().rollback();
		} finally {
			session.close();
		}
		this.log.info("DAO: Resultado de perro con id:" + perro.getId() + " modificado: " + resul);
		return resul;
	}

	/**
	 * Función que inserta un perro
	 * 
	 * @param perro
	 *            Perro que se quiere insertar<br>
	 * 
	 * 
	 * @return boolean true si insertado, false si no.
	 * 
	 */
	public boolean insert(final Perro perro) {
		this.log.trace("DAO: Insertando perro con nombre:" + perro.getNombre() + " y raza:" + perro.getRaza());
		boolean resul = false;
		final Session session = HibernateUtil.getSession();
		try {
			session.beginTransaction();
			final long idCreado = (Long) session.save(perro);
			if (idCreado > 0) {
				this.log.info("Insertando perro");
				resul = true;
				session.beginTransaction().commit();
			} else {
				this.log.info("Error al insertar perro");
				session.beginTransaction().rollback();
			}

		} catch (Exception e) {
			this.log.error("Exception: no se ha podido insertar perro con nombre:" + perro.getNombre() + " y raza:"
					+ perro.getRaza());
			session.beginTransaction().rollback();
		} finally {
			session.close();
		}
		this.log.info("DAO: Resultado de perro insertar perro con nombre:" + perro.getNombre() + " y raza:"
				+ perro.getRaza() + " insertado: " + resul);
		return resul;
	}

}

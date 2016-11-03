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
public class PerroDAOImpl implements PerroDAO {

	protected final Logger log = LoggerFactory.getLogger(getClass());
	// instancia unica para 'patron Singleton'
	private static PerroDAOImpl INSTANCE = null;

	// constructor privado para que no se pueda instanciar esta clase
	private PerroDAOImpl() {
		super();
	}

	// unico metodo para crear un objeto de esta Clase
	public synchronized static PerroDAOImpl getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new PerroDAOImpl();
		}
		return INSTANCE;
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
	public List<Perro> getAll(String order, String campo) {
		this.log.trace("DAO: Obteniendo lista de perros");
		// inicializamos lista como un ArrayList de objetos Perro
		ArrayList<Perro> lista = new ArrayList<Perro>();
		// obtenemos la sesion
		Session s = HibernateUtil.getSession();
		try {

			try {
				if ("desc".equals(order)) {
					this.log.trace("DAO: Obteniendo lista de perros por orden desc");
					lista = (ArrayList<Perro>) s.createCriteria(Perro.class).addOrder(Order.desc(campo)).list();
				} else {
					this.log.trace("DAO: Obteniendo lista de perros por orden asc");
					lista = (ArrayList<Perro>) s.createCriteria(Perro.class).addOrder(Order.asc(campo)).list();
				}
				// Si falla porque esta mal la Query, por ejemplo una columna
				// que no existe
				// retorno listado perros ordenados por id desc
			} catch (QueryException e) {
				this.log.error("QueryException: mostrar lista de perros orden desc por id");
				lista = (ArrayList<Perro>) s.createCriteria(Perro.class).addOrder(Order.desc("id")).list();
			}

		} catch (Exception e) {
			this.log.error("Exception: no se ha podido mostrar lista de perros");
			e.printStackTrace();
		} finally {
			// cerramos la transaccion
			s.close();
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
	public Perro getById(long idPerro) {
		this.log.trace("DAO: Obteniendo perro por id:" + idPerro);
		Perro resul = null;

		Session s = HibernateUtil.getSession();
		try {
			resul = (Perro) s.get(Perro.class, idPerro);
			if (resul == null) {
				resul = new Perro();
			}
		} catch (Exception e) {
			this.log.error("Exception: no se ha podido encontrar perro con id" + idPerro);
			e.printStackTrace();
		} finally {
			s.close();
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
	public boolean delete(long idPerro) {
		this.log.trace("DAO: Eliminando perro por id:" + idPerro);
		Perro pElimnar = null;
		boolean resul = false;
		Session s = HibernateUtil.getSession();
		try {
			s.beginTransaction();
			this.log.trace("DAO: Buscando perro por id:" + idPerro);
			pElimnar = (Perro) s.get(Perro.class, idPerro);
			if (pElimnar != null) {
				this.log.trace("DAO: Perro con id:" + idPerro + " existente a eliminar");
				s.delete(pElimnar);
				s.beginTransaction().commit();
				resul = true;
			}
		} catch (final Exception e) {
			this.log.error("Exception: no se ha podido eliminar perro con id" + idPerro);
			e.printStackTrace();
			s.beginTransaction().rollback();
		} finally {
			s.close();
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
	public boolean update(Perro perro) {
		this.log.trace("DAO: Modificando perro con id:" + perro.getId());
		boolean resul = false;
		Session s = HibernateUtil.getSession();
		try {
			s.beginTransaction();
			s.update(perro);
			s.beginTransaction().commit();
			resul = true;
		} catch (final Exception e) {
			this.log.error("Exception: no se ha podido modificar perro con id" + perro.getId());
			e.printStackTrace();
			s.beginTransaction().rollback();
		} finally {
			s.close();
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
	public boolean insert(Perro perro) {
		this.log.trace("DAO: Insertando perro con nombre:" + perro.getNombre() + " y raza:" + perro.getRaza());
		boolean resul = false;
		Session s = HibernateUtil.getSession();
		try {
			s.beginTransaction();
			long idCreado = (Long) s.save(perro);
			if (idCreado > 0) {
				this.log.info("Insertando perro");
				resul = true;
				s.beginTransaction().commit();
			} else {
				this.log.info("Error al insertar perro");
				s.beginTransaction().rollback();
			}

		} catch (Exception e) {
			this.log.error("Exception: no se ha podido insertar perro con nombre:" + perro.getNombre() + " y raza:"
					+ perro.getRaza());
			s.beginTransaction().rollback();
			e.printStackTrace();
		} finally {
			s.close();
		}
		this.log.info("DAO: Resultado de perro insertar perro con nombre:" + perro.getNombre() + " y raza:"
				+ perro.getRaza() + " insertado: " + resul);
		return resul;
	}

}

package com.ipartek.formacion.perrera.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.QueryException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import com.ipartek.formacion.perrera.pojo.Perro;
import com.ipartek.formacion.perrera.util.HibernateUtil;

/**
 * Implementación de las funciones de CRUD. Contiene Patrón Singleton
 * 
 * @author JHM
 * 
 *
 */
public final class PerroDAOImpl implements PerroDAO {

	private static final Logger LOGGER = Logger.getLogger(PerroDAOImpl.class);

	// instancia unica para 'patron Singleton'
	private static PerroDAOImpl INSTANCE = null;

	/**
	 * Constructor privado para que no se pueda instanciar esta clase
	 */
	private PerroDAOImpl() {
		super();
	}

	/**
	 * unico metodo para crear un objeto de esta Clase
	 * 
	 * @return INSTANCE
	 */
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
	 * @return List &gt;Perro&lt;
	 */
	public List<Perro> getAll(String order, String campo) {
		// inicializamos lista como un ArrayList de objetos Perro
		ArrayList<Perro> lista = new ArrayList<Perro>();
		// obtenemos la sesion
		Session s = HibernateUtil.getSession();
		try {
			try {
				if ("desc".equals(order)) {
					lista = (ArrayList<Perro>) s.createCriteria(Perro.class).addOrder(Order.desc(campo)).list();
					LOGGER.info("DAO: Listado ordenado desc por" + campo + "OK");
				} else {
					lista = (ArrayList<Perro>) s.createCriteria(Perro.class).addOrder(Order.asc(campo)).list();
					LOGGER.info("DAO: Listado ordenado asc por" + campo + "OK");
				}
				// Si falla porque esta mal la Query, por ejemplo una columna
				// que no existe
				// retorno listado perros ordenados por id desc
			} catch (QueryException e) {
				LOGGER.warn("DAO: Query incorrecta. Se retornará el listado perros ordenados por id desc", e);
				lista = (ArrayList<Perro>) s.createCriteria(Perro.class).addOrder(Order.desc("id")).list();
			}

		} catch (Exception e) {
			LOGGER.warn("DAO: Error al obtener la lista de perros", e);
			// e.printStackTrace();
		} finally {
			// cerramos la transaccion
			s.close();
			LOGGER.info("DAO: Cerrada transacción 'getAll' OK");
		}
		return lista;
	}

	/**
	 * Método que obtiene de la BBDD un Perro por su 'id'
	 * 
	 * @param idPerro
	 *            Id del Perro a obtener
	 * @return Perro Devuelve un objeto Perro
	 */
	public Perro getById(long idPerro) {
		Perro resul = null;
		Session s = HibernateUtil.getSession();
		try {
			resul = (Perro) s.get(Perro.class, idPerro);
		} catch (Exception e) {
			LOGGER.warn("DAO: La id=" + idPerro + "no existe", e);
			e.printStackTrace();
		} finally {
			s.close();
			LOGGER.info("DAO: Cerrada transacción 'getById' OK");
		}
		return resul;
	}

	/**
	 * Método que elimina de la BBDD un Perro por su 'id'
	 * 
	 * @param idPerro
	 *            Id del Perro a obtener
	 * @return boolean Devuelve 'true' si el perro es eliminado OK, 'false' en
	 *         caso contrario
	 */
	public boolean delete(long idPerro) {
		Perro pElimnar = null;
		boolean resul = false;
		Session s = HibernateUtil.getSession();
		try {
			s.beginTransaction();
			pElimnar = (Perro) s.get(Perro.class, idPerro);
			if (pElimnar != null) {
				s.delete(pElimnar);
				s.beginTransaction().commit();
				LOGGER.info("DAO: Perro con id=" + idPerro + "borrado OK");
				resul = true;
			}
		} catch (final Exception e) {
			LOGGER.warn("DAO: Error al borrar perro con id=" + idPerro, e);
			e.printStackTrace();
			s.beginTransaction().rollback();
		} finally {
			s.close();
			LOGGER.info("DAO: Cerrada transacción 'delete' OK");
		}
		return resul;
	}

	/**
	 * Método que actualiza de la BBDD un Perro
	 * 
	 * @param perro
	 *            Perro a modificar
	 * @return boolean Devuelve 'true' si el perro es modificado OK, 'false' en
	 *         caso contrario
	 */
	public boolean update(Perro perro) {
		boolean resul = false;
		Session s = HibernateUtil.getSession();
		try {
			s.beginTransaction();
			s.update(perro);
			s.beginTransaction().commit();
			LOGGER.info("DAO: Perro actualizado OK");
			resul = true;
		} catch (final Exception e) {
			LOGGER.warn("DAO: Error al actualizar perro", e);
			e.printStackTrace();
			s.beginTransaction().rollback();
		} finally {
			s.close();
			LOGGER.info("DAO: Cerrada transacción 'update' OK");
		}
		return resul;
	}

	/**
	 * Método que crea en la BBDD un Perro nuevo
	 * 
	 * @param perro
	 *            Perro a crear
	 * @return boolean Devuelve 'true' si el perro es creado OK, 'false' en caso
	 *         contrario
	 */
	public boolean insert(Perro perro) {
		boolean resul = false;
		Session s = HibernateUtil.getSession();
		try {
			s.beginTransaction();
			long idCreado = (Long) s.save(perro);
			if (idCreado > 0) {
				resul = true;
				s.beginTransaction().commit();
				LOGGER.info("DAO: Perro creado con id=" + idCreado + " OK");
			} else {
				s.beginTransaction().rollback();
			}
		} catch (Exception e) {
			LOGGER.warn("DAO: Error al crear perro", e);
			s.beginTransaction().rollback();
			e.printStackTrace();
		} finally {
			s.close();
			LOGGER.info("DAO: Cerrada transacción 'insert' OK");
		}
		return resul;
	}

}

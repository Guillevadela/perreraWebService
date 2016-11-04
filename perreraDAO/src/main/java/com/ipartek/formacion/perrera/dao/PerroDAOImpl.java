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
 * Esta clase se encarga de conectar con la bd para realizar tareas de CRUD
 * 
 * @author Adassoy
 *
 */
public final class PerroDAOImpl implements PerroDAO {
	/**
	 * Logger log
	 */
	protected final Logger log = LoggerFactory.getLogger(getClass());
	/**
	 * Instancia de PerroDAOImpl
	 */
	// instancia unica para 'patron Singleton'
	private static PerroDAOImpl instance;

	// constructor privado para que no se pueda instanciar esta clase
	private PerroDAOImpl() {
		super();
	}

	/**
	 * unico metodo para crear un objeto de esta Clase
	 * 
	 * @return instancia creada
	 */
	public synchronized static PerroDAOImpl getInstance() {
		if (instance == null) {
			instance = new PerroDAOImpl();
		}
		return instance;
	}

	/**
	 * Se encarga de listar todos los perros de la bd.
	 * 
	 * Si al solicitar el listado se ponen mal los parametros por defecto el
	 * orden es en orden descendiente por id.
	 * 
	 * @param order
	 *            Filtro para ordenar los perros de forma ascendente o
	 *            descendente, posibles valores [asc|desc]
	 * @param campo
	 *            Filtro para ordenar por 'campo' los perros, posibles valores
	 *            [id|nombre|raza]
	 * @return Devuelve una lista con todos los elementos
	 */

	public List<Perro> getAll(final String order, final String campo) {
		// inicializamos lista como un ArrayList de objetos Perro
		ArrayList<Perro> lista = new ArrayList<Perro>();
		// obtenemos la sesion
		final Session session = HibernateUtil.getSession();

		try {

			try {
				if ("desc".equals(order)) {
					this.log.trace("listando todos los perros en orden desdendente");
					lista = (ArrayList<Perro>) session.createCriteria(Perro.class).addOrder(Order.desc(campo)).list();
				} else {
					this.log.trace("listando todos los perros en orden ascendente");
					lista = (ArrayList<Perro>) session.createCriteria(Perro.class).addOrder(Order.asc(campo)).list();
				}
				// Si falla porque esta mal la Query, por ejemplo una columna
				// que no existe
				// retorno listado perros ordenados por id desc
			} catch (QueryException e) {
				this.log.error("listando todos los perros en orden descendente por un error en la Query");
				lista = (ArrayList<Perro>) session.createCriteria(Perro.class).addOrder(Order.desc("id")).list();
			}

		} catch (Exception e) {
			this.log.error("Error al intentar obtener el listado");
			// e.printStackTrace();
		} finally {
			// cerramos la transaccion
			session.close();
			this.log.trace("cerrando sesion, finaliza getAll");
		}
		return lista;
	}

	/**
	 * Se encarga de obtener un elemento de la bd por su id.
	 * 
	 * @param idPerro
	 *            - id del perro a obtener
	 * 
	 * @return - Objeto de tipo Perro
	 */
	public Perro getById(final long idPerro) {
		Perro resul = null;
		final Session session = HibernateUtil.getSession();
		try {
			resul = (Perro) session.get(Perro.class, idPerro);
			this.log.trace("Iniciada peticion de perro por 'id'");
		} catch (Exception e) {
			this.log.error("Error al intentar obtener perro por id");
			// e.printStackTrace();
		} finally {
			session.close();
			this.log.trace("cerrando sesion, finaliza getById");
		}
		return resul;
	}

	/**
	 * Se encarga de eliminar un elemento de la bd por su id.
	 * 
	 * @param idPerro
	 *            - id del perro a eliminar
	 * @return - Devuelve un booleano (true, para eliminado - false - para NO
	 *         eliminado)
	 */
	public boolean delete(final long idPerro) {
		Perro pElimnar = null;
		boolean resul = false;
		final Session session = HibernateUtil.getSession();
		try {
			session.beginTransaction();
			pElimnar = (Perro) session.get(Perro.class, idPerro);
			if (pElimnar != null) {
				session.delete(pElimnar);
				this.log.info("Eliminando un perro");
				session.beginTransaction().commit();
				resul = true;
			}
		} catch (final Exception e) {
			this.log.error("No se ha podido realizar borrado de perro, se hace rollback");
			// e.printStackTrace();
			session.beginTransaction().rollback();
		} finally {
			session.close();
			this.log.trace("cerrando sesion, finaliza delete");
		}
		return resul;
	}

	/**
	 * Actualiza un perro
	 * 
	 * @param perro
	 *            - objeto de tipo Perro
	 * 
	 * @return Booleano 'resul' true/false - Actualizado/NO actualizado
	 */
	public boolean update(final Perro perro) {
		boolean resul = false;
		final Session session = HibernateUtil.getSession();
		try {
			session.beginTransaction();
			this.log.info("Modificando un perro");
			session.update(perro);
			session.beginTransaction().commit();
			resul = true;
		} catch (final Exception e) {
			this.log.error("No se ha podido modificar, se inicia rollback");
			// e.printStackTrace();
			session.beginTransaction().rollback();
		} finally {
			this.log.trace("cerrando sesion, finaliza update");
			session.close();
		}
		return resul;
	}

	/**
	 * Añade un nuevo elemento a la bd
	 * 
	 * @param perro
	 *            de tipo Perro
	 * 
	 * @return Booleano 'resul' true/false - Insertado/NO Insertado
	 */
	public boolean insert(final Perro perro) {
		boolean resul = false;
		final Session session = HibernateUtil.getSession();
		try {
			session.beginTransaction();
			final long idCreado = (Long) session.save(perro);
			if (idCreado > 0) {
				this.log.info("Guardando un nuevo perro");
				resul = true;
				session.beginTransaction().commit();
			} else {
				this.log.info("Error al guardar un nuevo perro - rollback-");
				session.beginTransaction().rollback();
			}
		} catch (Exception e) {
			this.log.error("Error al intentar Insertar un nuevo elemento, se hace rollback");
			session.beginTransaction().rollback();
			// e.printStackTrace();
		} finally {
			session.close();
			this.log.trace("cerrando sesion, finaliza insert");
		}
		return resul;
	}

}

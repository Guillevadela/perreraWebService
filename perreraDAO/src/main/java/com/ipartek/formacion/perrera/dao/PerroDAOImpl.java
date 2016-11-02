package com.ipartek.formacion.perrera.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.QueryException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import com.ipartek.formacion.perrera.pojo.Perro;
import com.ipartek.formacion.perrera.util.HibernateUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
		// inicializamos lista como un ArrayList de objetos Perro
		ArrayList<Perro> lista = new ArrayList<Perro>();
		// obtenemos la sesion
		Session s = HibernateUtil.getSession();
		try {

			try {
				if ("desc".equals(order)) {
					this.log.info("listando todos los perros en orden desdendente");
					lista = (ArrayList<Perro>) s.createCriteria(Perro.class).addOrder(Order.desc(campo)).list();
				} else {
					this.log.info("listando todos los perros en orden ascendente");
					lista = (ArrayList<Perro>) s.createCriteria(Perro.class).addOrder(Order.asc(campo)).list();
				}
				// Si falla porque esta mal la Query, por ejemplo una columna
				// que no existe
				// retorno listado perros ordenados por id desc
			} catch (QueryException e) {				
				this.log.info("listando todos los perros en orden descendente por un error en la Query");				
				lista = (ArrayList<Perro>) s.createCriteria(Perro.class).addOrder(Order.desc("id")).list();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// cerramos la transaccion
			s.close();
		}
		return lista;
	}

	public Perro getById(long idPerro) {
		Perro resul = null;
		Session s = HibernateUtil.getSession();
		try {
			resul = (Perro) s.get(Perro.class, idPerro);
			this.log.info("Iniciada peticion de perro por 'id'");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			s.close();
		}
		return resul;
	}

	public boolean delete(long idPerro) {
		Perro pElimnar = null;
		boolean resul = false;
		Session s = HibernateUtil.getSession();
		try {
			s.beginTransaction();
			pElimnar = (Perro) s.get(Perro.class, idPerro);
			if (pElimnar != null) {
				s.delete(pElimnar);
				this.log.info("Iniciada peticion de eliminar perro");
				s.beginTransaction().commit();
				resul = true;
			}
		} catch (final Exception e) {
			e.printStackTrace();
			s.beginTransaction().rollback();
		} finally {
			s.close();
		}
		return resul;
	}

	public boolean update(Perro perro) {
		boolean resul = false;
		Session s = HibernateUtil.getSession();
		try {
			s.beginTransaction();
			this.log.info("Iniciada peticion de actualizar perro");
			s.update(perro);
			s.beginTransaction().commit();
			resul = true;
		} catch (final Exception e) {
			e.printStackTrace();
			s.beginTransaction().rollback();
		} finally {
			s.close();
		}
		return resul;
	}

	public boolean insert(Perro perro) {
		boolean resul = false;
		Session s = HibernateUtil.getSession();
		try {
			s.beginTransaction();
			long idCreado = (Long) s.save(perro);
			if (idCreado > 0) {
				this.log.info("Guardando un nuevo perro");
				resul = true;
				s.beginTransaction().commit();
			} else {
				this.log.info("Error al guardar un nuevo perro - rollback-");
				s.beginTransaction().rollback();
			}
		} catch (Exception e) {
			s.beginTransaction().rollback();
			e.printStackTrace();
		} finally {
			s.close();
		}
		return resul;
	}

}

package com.ipartek.formacion.perrera.dao;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.QueryException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import com.ipartek.formacion.perrera.pojo.Perro;
import com.ipartek.formacion.perrera.util.HibernateUtil;

public class PerroDAOImpl implements PerroDAO {

	private static PerroDAOImpl INSTANCE = null;

	private PerroDAOImpl() {
		super();
	}

	public static synchronized PerroDAOImpl getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new PerroDAOImpl();
		}
		return INSTANCE;
	}
	
	/*@param campo
	 *            Campo por el que se va a ordenar. <br>
	 * @return List$gt;Perro$lt;
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
				} else {
					lista = (ArrayList<Perro>) s.createCriteria(Perro.class).addOrder(Order.asc(campo)).list();
				}
				// Si falla porque esta mal la Query, por ejemplo una columna
				// que no existe
				// retorno listado perros ordenados por id desc
			} catch (QueryException e) {
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
			if (resul == null) {
				resul = new Perro();
			}
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
			s.save(perro);
			resul = true;
			s.beginTransaction().commit();

		} catch (Exception e) {
			s.beginTransaction().rollback();
			e.printStackTrace();
		} finally {
			s.close();
		}
		return resul;
	}

}

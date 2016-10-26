package com.ipartek.formacion.perrera.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.QueryException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import com.ipartek.formacion.perrera.pojo.Perro;
import com.ipartek.formacion.perrera.util.HibernateUtil;

public class PerroDAOImpl implements PerroDAO {

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
	@SuppressWarnings("unchecked")
	@Override
	public List<Perro> getAll(String order, String campo) {
		// inicializamos lista como un ArrayList de objetos Perro
		ArrayList<Perro> lista = new ArrayList<Perro>();
		// obtenemos la sesion
		final Session s = HibernateUtil.getSession();
		try {
			try {
				if ("desc".equals(order)) {
					lista = (ArrayList<Perro>) s.createCriteria(Perro.class).addOrder(Order.desc(campo)).list();
				} else {
					lista = (ArrayList<Perro>) s.createCriteria(Perro.class).addOrder(Order.asc(campo)).list();
				}
				// Si falla por que el campo no existe se ordenara de manera
				// ascendente por id
			} catch (final QueryException e) {
				lista = (ArrayList<Perro>) s.createCriteria(Perro.class).addOrder(Order.desc("id")).list();
			}

		} catch (final Exception e) {
			e.printStackTrace();
		} finally {
			// cerramos la transaccion
			s.close();
		}
		return lista;
	}

	@Override
	public Perro getById(long idPerro) {
		Perro resul = null;
		final Session s = HibernateUtil.getSession();
		try {
			resul = (Perro) s.get(Perro.class, idPerro);
		} catch (final Exception e) {
			e.printStackTrace();
		} finally {
			s.close();
		}
		return resul;
	}

	@Override
	public boolean delete(long idPerro) {
		Perro pElimnar = null;
		boolean resul = false;
		final Session s = HibernateUtil.getSession();
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

	@Override
	public boolean update(Perro perro) {
		boolean resul = false;
		final Session s = HibernateUtil.getSession();
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

	@Override
	public boolean insert(Perro perro) {
		boolean resul = false;
		final Session s = HibernateUtil.getSession();
		try {
			s.beginTransaction();
			final long idCreado = (Long) s.save(perro);
			if (idCreado > 0) {
				resul = true;
				s.beginTransaction().commit();
			} else {
				s.beginTransaction().rollback();
			}
		} catch (final Exception e) {
			s.beginTransaction().rollback();
			e.printStackTrace();
		} finally {
			s.close();
		}
		return resul;
	}

}
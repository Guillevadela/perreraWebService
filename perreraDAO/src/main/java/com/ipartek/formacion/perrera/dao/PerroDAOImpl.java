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

public class PerroDAOImpl implements PerroDAO {

	private static final Logger logger = LoggerFactory.getLogger(PerroDAOImpl.class);

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
	@Override
	public List<Perro> getAll(String order, String campo) {
		// inicializamos lista como un ArrayList de objetos Perro
		ArrayList<Perro> lista = new ArrayList<Perro>();
		// obtenemos la sesion
		this.logger.info("Obteniendo la sesion...");
		Session s = HibernateUtil.getSession();
		try {
			if (order.equals("desc")) {
				this.logger.info("Ordenando el array de forma descendente");
				lista = (ArrayList<Perro>) s.createCriteria(Perro.class).addOrder(Order.desc(campo)).list();
			} else {
				this.logger.info("Ordenando el array de forma ascendente");
				lista = (ArrayList<Perro>) s.createCriteria(Perro.class).addOrder(Order.asc(campo)).list();
			}
		} catch (QueryException e) {
			this.logger.error("Error al obtener la lista");
			lista = (ArrayList<Perro>) s.createCriteria(Perro.class).addOrder(Order.desc(campo)).list();
		} catch (Exception e) {
			this.logger.error("No se ha podido obtener la lista ordenada");
			e.printStackTrace();
		} finally {
			// cerramos la transaccion
			this.logger.info("Cerrando la transaccion...");
			s.close();
		}
		this.logger.info("Devolviendo la lista...");
		return lista;
	}

	@Override
	public Perro getById(long idPerro) {
		Perro resul = null;
		this.logger.info("Obteniendo la sesion...");
		Session s = HibernateUtil.getSession();
		try {
			this.logger.info("Buscando al perro cuya id es:" + idPerro);
			resul = (Perro) s.get(Perro.class, idPerro);
		} catch (Exception e) {
			this.logger.error("Error al buscar al perro cuya id es:" + idPerro);
			e.printStackTrace();
		} finally {
			this.logger.info("Cerrando la transaccion...");
			s.close();
		}
		this.logger.info("Devolviendo el resultado...");
		return resul;
	}

	@Override
	public boolean delete(long idPerro) {
		Perro pElimnar = null;
		boolean resul = false;
		this.logger.info("Obteniendo la sesion...");
		Session s = HibernateUtil.getSession();
		try {
			s.beginTransaction();
			this.logger.info("Eliminando al perro de id:" + idPerro);
			pElimnar = (Perro) s.get(Perro.class, idPerro);
			if (pElimnar != null) {
				s.delete(pElimnar);
				s.beginTransaction().commit();
				this.logger.info("El perro de id:" + idPerro + " ha sido eliminado");
				resul = true;
			}
		} catch (final Exception e) {
			e.printStackTrace();
			this.logger.error("Error al eliminar al perro de id:" + idPerro);
			s.beginTransaction().rollback();
		} finally {
			this.logger.info("Cerrando la transaccion...");
			s.close();
		}
		this.logger.info("Devolviendo el resultado...");
		return resul;
	}

	@Override
	public boolean update(Perro perro) {
		boolean resul = false;
		this.logger.info("Obteniendo la sesion...");
		Session s = HibernateUtil.getSession();
		try {
			s.beginTransaction();
			this.logger.info("Modificando al perro de id:" + perro.getId());
			s.update(perro);
			s.beginTransaction().commit();
			resul = true;
			this.logger.info("El perro:" + perro.toString() + " ha sido modificado");
		} catch (final Exception e) {
			e.printStackTrace();
			this.logger.error("Error al modificar al perro de id:" + perro.getId());
			s.beginTransaction().rollback();
		} finally {
			this.logger.info("Cerrando la transaccion...");
			s.close();
		}
		this.logger.info("Devolviendo el resultado...");
		return resul;
	}

	@Override
	public boolean insert(Perro perro) {
		boolean resul = false;
		this.logger.info("Obteniendo la sesion...");
		Session s = HibernateUtil.getSession();
		try {
			s.beginTransaction();
			this.logger.info("Dando de alta al perro:" + perro);
			long idCreado = (Long) s.save(perro);
			if (idCreado > 0) {
				resul = true;
				this.logger.info("El perro " + perro.toString() + ", ha sido dado de alta");
				s.beginTransaction().commit();
			} else {
				this.logger.info("El perro " + perro.toString() + ", no ha sido dado de alta");
				s.beginTransaction().rollback();
			}
		} catch (Exception e) {
			this.logger.error("Error al dar de alta al perro:" + perro.toString());
			s.beginTransaction().rollback();
			e.printStackTrace();
		} finally {
			this.logger.info("Cerrando la transaccion...");
			s.close();
		}
		this.logger.info("Devolviendo el resultado...");
		return resul;
	}

}
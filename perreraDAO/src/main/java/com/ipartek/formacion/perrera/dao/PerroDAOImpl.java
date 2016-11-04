package com.ipartek.formacion.perrera.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ipartek.formacion.perrera.pojo.Perro;
import com.ipartek.formacion.perrera.util.HibernateUtil;

public class PerroDAOImpl implements PerroDAO {

	
	 private final Logger logger = LoggerFactory.getLogger(PerroDAOImpl.class);
	 
	// Instancia unica para 'patron Singleton'
	private static PerroDAOImpl instance = null;

	// constructor privado para que no se pueda instanciar esta clase
	private PerroDAOImpl() {
		super();
	}

	// unico metodo para crear un objeto de esta Clase
	public static synchronized PerroDAOImpl getInstance() {
		if (instance == null) {
			instance = new PerroDAOImpl();
		}
		return instance;
	}

	@Override
	public List<Perro> getAll(String order, String campo) {

		ArrayList<Perro> perros = new ArrayList<Perro>();
		Session s = HibernateUtil.getSession();

		try {
			// controlar los QueryParam
			if("desc".equals(order)){
				this.logger.trace("Listando todos los perros por "+campo+" en orden DESCENDENTE");
				perros = (ArrayList<Perro>) s.createCriteria(Perro.class).addOrder(Order.desc(campo)).list(); 
			}else{
				this.logger.trace("Listando todos los perros por "+campo+" en orden ASCENDENTE");
				perros = (ArrayList<Perro>) s.createCriteria(Perro.class).addOrder(Order.asc(campo)).list();				
			}
	


		//Si falla porque esta mal la Query, por ejemplo una columna que no existe
		//retorno por orden descendente por id

		} catch (Exception e) {
			this.logger.trace("Listando todos los perros por ID en orden DESCENDENTE");
			perros = (ArrayList<Perro>) s.createCriteria(Perro.class).addOrder(Order.desc("id")).list();
		} finally {
			s.close();
		}
		return perros;
	}

	@Override
	public Perro getById(int idPerro) {
		Session s = HibernateUtil.getSession();
		Perro perro = null;
		try {
			this.logger.trace("Recogiendo perro "+idPerro);
			s = HibernateUtil.getSession();
			perro = (Perro) s.get(Perro.class, idPerro);
		} catch (Exception e) {
			this.logger.error("Ocurrio un error recogiendo el perro por id", e);
		} finally {
			s.close();
		}
		return perro;
	}

	@Override
	public boolean delete(int idPerro) {
		Perro pElimnar = null;
		Session s = HibernateUtil.getSession();
		boolean resul = false;
		try {
			s.beginTransaction();
			pElimnar = (Perro) s.get(Perro.class, idPerro);
			if (pElimnar != null) {
				s.delete(pElimnar);
				s.beginTransaction().commit();
				resul = true;
				this.logger.info("Perro "+idPerro+" eliminado con exito");
			}
		} catch (Exception e) {
			this.logger.error("Error al eliminar Perro "+idPerro, e);
			s.beginTransaction().rollback();
		}finally{
			s.close();
		}
		return resul;
	}

	@Override
	public boolean update(Perro perro) {
		boolean resul = false;
		Session s = HibernateUtil.getSession();
		try {
			s.beginTransaction();
			s.update(perro);
			s.beginTransaction().commit();
			resul = true;	
			this.logger.info("Modificado Perro "+perro.getId());		
			
		} catch (Exception e) {
			s.beginTransaction().rollback();
			this.logger.error("Error al modificar Perro "+perro.getId(), e);	
		} finally {
			s.close();
		}
		return resul;
	}

	@Override
	public boolean insert(Perro perro) {

		Session s = HibernateUtil.getSession();
		boolean resul = false;

		try {

			s.beginTransaction();			
			
			int idpCreado = (Integer) s.save(perro);
			if (idpCreado > 0) {
				s.beginTransaction().commit();
				resul = true;
				this.logger.info("Creado Perro "+perro.getId());	
			} else {
				s.beginTransaction().rollback();
				this.logger.error("Error al crear Perro "+perro.getId());	
			}

		} catch (Exception e) {
			this.logger.error("Error al crear Perro "+perro.getId(), e);
			s.beginTransaction().rollback();	
		} finally {
			s.close();
		}

		return resul;
	}

}

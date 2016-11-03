package com.ipartek.formacion.perrera.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ipartek.formacion.perrera.dao.PerroDAOImpl;
import com.ipartek.formacion.perrera.pojo.Perro;

public class PerreraServiceImpl implements PerreraService {

	private static final long serialVersionUID = 1L;

	protected final Logger log = LoggerFactory.getLogger(getClass());
	// instancia unica para 'patron Singleton'
	private static PerreraServiceImpl INSTANCE = null;

	// constructor privado para que no se pueda instanciar esta clase
	private PerreraServiceImpl() {
		super();
	}

	// unico metodo para crear un objeto de esta Clase
	public synchronized static PerreraServiceImpl getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new PerreraServiceImpl();
		}
		return INSTANCE;
	}

	@Override
	public List<Perro> getAll(String order, String campo) {
		this.log.info("Peticion del servicio para listar todos los perros");
		PerroDAOImpl dao = PerroDAOImpl.getInstance();
		return (ArrayList<Perro>) dao.getAll(order, campo);
	}

	@Override
	public Perro getById(long idPerro) {
		this.log.info("Peticion del servicio para listar un perro por 'id'");
		PerroDAOImpl dao = PerroDAOImpl.getInstance();
		return (Perro) dao.getById(idPerro);
	}

	@Override
	public boolean delete(long idPerro) {
		PerroDAOImpl dao = PerroDAOImpl.getInstance();
		return dao.delete(idPerro);
	}

	@Override
	public boolean insert(Perro perro) {
		PerroDAOImpl dao = PerroDAOImpl.getInstance();
		return dao.insert(perro);
	}

	@Override
	public boolean update(Perro perro) {
		PerroDAOImpl dao = PerroDAOImpl.getInstance();
		return dao.update(perro);
	}

}
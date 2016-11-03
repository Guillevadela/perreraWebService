package com.ipartek.formacion.perrera.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ipartek.formacion.perrera.dao.PerroDAOImpl;
import com.ipartek.formacion.perrera.pojo.Perro;

public class PerroServiceImpl implements PerroService {
	
	private final Logger logger = LoggerFactory.getLogger(PerroDAOImpl.class);

	// instancia unica para 'patron Singleton'
	private static PerroServiceImpl INSTANCE = null;

	// constructor privado para que no se pueda instanciar esta clase
	private PerroServiceImpl() {
		super();
	}

	// unico metodo para crear un objeto de esta Clase
	public synchronized static PerroServiceImpl getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new PerroServiceImpl();
		}
		return INSTANCE;
	}

	
	@Override
	public List<Perro> getAll(String order, String campo) {
		this.logger.trace("Listando Perros");
		PerroDAOImpl dao = PerroDAOImpl.getInstance();
		return dao.getAll(order, campo);
	}

	@Override
	public Perro getById(int idPerro) {
		this.logger.trace("Listando perro con id " + idPerro);
		PerroDAOImpl dao = PerroDAOImpl.getInstance();
		return dao.getById(idPerro);
	}

	@Override
	public boolean delete(int idPerro) {
		this.logger.trace("Borrando perro con id " + idPerro);
		PerroDAOImpl dao = PerroDAOImpl.getInstance();
		return dao.delete(idPerro);
	}

	@Override
	public boolean update(Perro perro) {
		this.logger.trace("Modificando perro con id " + perro.getId());
		PerroDAOImpl dao = PerroDAOImpl.getInstance();
		return dao.update(perro);
	}

	@Override
	public boolean insert(Perro perro) {
		this.logger.trace("Creando perro " + perro.getNombre());
		PerroDAOImpl dao = PerroDAOImpl.getInstance();
		return dao.insert(perro);
	}

}

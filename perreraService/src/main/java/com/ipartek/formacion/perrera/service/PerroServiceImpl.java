package com.ipartek.formacion.perrera.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ipartek.formacion.perrera.dao.PerroDAOImpl;
import com.ipartek.formacion.perrera.pojo.Perro;

public class PerroServiceImpl implements PerroService {

	private static final Logger logger = LoggerFactory.getLogger(PerroDAOImpl.class);

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
		PerroDAOImpl dao = PerroDAOImpl.getInstance();
		this.logger.trace("llamando al DAO para obtener la lista...");
		return (ArrayList<Perro>) dao.getAll(order, campo);
	}

	@Override
	public Perro getById(long idPerro) {
		PerroDAOImpl dao = PerroDAOImpl.getInstance();
		this.logger.trace("llamando al DAO para obtener al Perro con id:"+idPerro);
		return (Perro) dao.getById(idPerro);
	}

	@Override
	public boolean delete(long idPerro) {
		PerroDAOImpl dao = PerroDAOImpl.getInstance();
		this.logger.trace("llamando al DAO para borrar al perro de id:"+idPerro);
		return dao.delete(idPerro);
	}

	@Override
	public boolean insert(Perro perro) {
		PerroDAOImpl dao = PerroDAOImpl.getInstance();
		this.logger.trace("llamando al DAO para dar de alta al perro "+perro.toString());
		return dao.insert(perro);
	}

	@Override
	public boolean update(Perro perro) {
		PerroDAOImpl dao = PerroDAOImpl.getInstance();
		this.logger.trace("llamando al DAO para modificar al perro "+perro.toString());
		return dao.update(perro);
	}

}

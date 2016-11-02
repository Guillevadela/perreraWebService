package com.ipartek.formacion.perrera.service;

import java.util.ArrayList;
import java.util.List;

import com.ipartek.formacion.perrera.dao.PerroDAOImpl;
import com.ipartek.formacion.perrera.pojo.Perro;

public class PerroServiceImpl implements PerroService {

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
		return (ArrayList<Perro>) dao.getAll(order, campo);
	}

	@Override
	public Perro getById(long idPerro) {
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

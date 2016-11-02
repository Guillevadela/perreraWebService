package com.ipartek.formacion.perrera.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ipartek.formacion.perrera.dao.PerroDAOImpl;
import com.ipartek.formacion.perrera.pojo.Perro;

public class PerroServiceImpl implements PerroService {

	protected final Logger log = LoggerFactory.getLogger(getClass());

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
		this.log.info("Llamada a dao: Obteniendo lista de perros");
		PerroDAOImpl dao = PerroDAOImpl.getInstance();
		return (ArrayList<Perro>) dao.getAll(order, campo);
	}

	@Override
	public Perro getById(long idPerro) {
		this.log.info("Llamada a dao: Obteniendo perro con su id");
		PerroDAOImpl dao = PerroDAOImpl.getInstance();
		return (Perro) dao.getById(idPerro);
	}

	@Override
	public boolean delete(long idPerro) {
		this.log.info("Llamada a dao: Eliminando perro con id:" + idPerro);
		PerroDAOImpl dao = PerroDAOImpl.getInstance();
		return dao.delete(idPerro);
	}

	@Override
	public boolean insert(Perro perro) {
		this.log.info(
				"Llamada a dao: Insertando perro nuevo con nombre:" + perro.getNombre() + " y raza:" + perro.getRaza());
		PerroDAOImpl dao = PerroDAOImpl.getInstance();
		return dao.insert(perro);
	}

	@Override
	public boolean update(Perro perro) {
		this.log.info("Llamada a dao: Modificando perro con id:" + perro.getId());
		PerroDAOImpl dao = PerroDAOImpl.getInstance();
		return dao.update(perro);
	}

}

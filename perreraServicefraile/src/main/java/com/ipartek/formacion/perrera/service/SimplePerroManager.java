package com.ipartek.formacion.perrera.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ipartek.formacion.perrera.dao.PerroDAOImpl;
import com.ipartek.formacion.perrera.pojo.Perro;

public class SimplePerroManager implements PerroManager {

	private static final Logger logger = LoggerFactory.getLogger(SimplePerroManager.class);
	
	private static final long serialVersionUID = 1L;
	
	private List<Perro> perros;

	Perro perro = null;

	private PerroDAOImpl perroDAOImp;
	
	@Override
	public List<Perro> getAll(String order, String campo) {
		this.perros = this.perroDAOImp.getAll(order, campo);
		return this.perros;
	}

	@Override
	public Perro getById(long idPerro) {
		this.perro = this.perroDAOImp.getById(idPerro);
		return perro;
	}

	@Override
	public boolean delete(long idPerro) {
		boolean resul = this.perroDAOImp.delete(idPerro);
		return resul;
	}

	@Override
	public boolean update(Perro perro) {
		boolean resul = this.perroDAOImp.update(perro);
		return resul;
	}

	@Override
	public boolean insert(Perro perro) {
		boolean resul = this.perroDAOImp.insert(perro);
		return resul;
	}

}

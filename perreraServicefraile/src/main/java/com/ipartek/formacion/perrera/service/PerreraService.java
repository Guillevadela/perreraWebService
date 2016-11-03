package com.ipartek.formacion.perrera.service;

import java.io.Serializable;
import java.util.List;

import com.ipartek.formacion.perrera.pojo.Perro;

public interface PerreraService extends Serializable {
	
	List<Perro> getAll(String order, String campo);

	Perro getById(long idPerro);

	boolean delete(long idPerro);

	boolean update(Perro perro);

	boolean insert(Perro perro);
}

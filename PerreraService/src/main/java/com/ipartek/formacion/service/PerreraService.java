package com.ipartek.formacion.service;
//package com.ipartek.formacion.perrera.dao;

import java.util.List;
import com.ipartek.formacion.perrera.pojo.Perro;

public interface PerreraService {
	
	List<Perro> getAll(String order, String campo);

	Perro getById(long idPerro);

	boolean delete(long idPerro);

	boolean update(Perro perro);

	boolean insert(Perro perro);
}
	







package com.ipartek.formacion.perrera.dao;

import java.util.List;

import com.ipartek.formacion.perrera.pojo.Perro;

public interface PerroDAO {

	List<Perro> getAll(String order, String campo);

	Perro getById(int idPerro);

	boolean delete(int idPerro);

	boolean update(Perro perro);

	boolean insert(Perro perro);
}

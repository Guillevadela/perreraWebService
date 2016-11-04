package com.ipartek.formacion.perrera.service;

import java.util.ArrayList;
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
	
	/**
	 * Función que devuelve una lista de perros
	 *
	 * @param order
	 *            Modo de ordenacion de la lista.<br>
	 *            Posibles valores asc/desc
	 * @param campo
	 *            Campo por el que se va a ordenar. <br>
	 *            Posibles valores id/nombre/raza
	 * @return List$gt;Perro$lt;
	 */
	
	@Override
	public List<Perro> getAll(String order, String campo) {
		PerroDAOImpl dao = PerroDAOImpl.getInstance();
		this.logger.trace("llamando al DAO para obtener la lista...");
		return (ArrayList<Perro>) dao.getAll(order, campo);
	}
	
	/**
	 * Función que devuelve un objeto Perro buscado por el parametro id de este
	 *
	 * @param idPerro
	 *            Variable para localizar al perro.
	 *            Valores numericos
	 * @return Perro;
	 */
	
	@Override
	public Perro getById(long idPerro) {
		PerroDAOImpl dao = PerroDAOImpl.getInstance();
		this.logger.trace("llamando al DAO para obtener al Perro con id:"+idPerro);
		return (Perro) dao.getById(idPerro);
	}

	/**
	 * Función que elimina un objeto Perro buscado por el parametro id de este.
	 * Al finalizar devolvera una variable booleana informando del resultado
	 *
	 * @param idPerro
	 *            Variable para localizar al perro.
	 *            Valores numericos
	 * @return resul;
	 */
	
	@Override
	public boolean delete(long idPerro) {
		PerroDAOImpl dao = PerroDAOImpl.getInstance();
		this.logger.trace("llamando al DAO para borrar al perro de id:"+idPerro);
		return dao.delete(idPerro);
	}

	/**
	 * Función que inserta un objeto Perro en la BBDD.
	 * Al finalizar devolvera una variable booleana informando del resultado
	 *
	 * @param perro
	 *            Objeto Perro que contiene los datos del Perro.
	 * @return resul;
	 */
	
	@Override
	public boolean insert(Perro perro) {
		PerroDAOImpl dao = PerroDAOImpl.getInstance();
		this.logger.trace("llamando al DAO para dar de alta al perro "+perro.toString());
		return dao.insert(perro);
	}

	/**
	 * Función que recibe un objeto Perro y modifica su nombre y raza.
	 * Al finalizar devolvera una variable booleana informando del resultado
	 *
	 * @param perro
	 *            Objeto Perro que contiene los datos nuevos del Perro existente.
	 * @return resul;
	 */
	
	@Override
	public boolean update(Perro perro) {
		PerroDAOImpl dao = PerroDAOImpl.getInstance();
		this.logger.trace("llamando al DAO para modificar al perro "+perro.toString());
		return dao.update(perro);
	}

}

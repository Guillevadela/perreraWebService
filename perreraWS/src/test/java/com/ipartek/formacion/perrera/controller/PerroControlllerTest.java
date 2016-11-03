package com.ipartek.formacion.perrera.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ipartek.formacion.perrera.pojo.Perro;

public class PerroControlllerTest {

	private final int SUCCESS00 = 200;
	private final int SUCCESS01 = 201;
	private final int FAILURE04 = 204;
	private final int FAILURE09 = 209;
	private final int ERROR00 = 50;
	private ArrayList<Perro> lista = null;
	private Perro perro = null;

	@BeforeClass()
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass()
	public static void tearDownAfterClass() throws Exception {
	}

	@Before()
	public void setUp() throws Exception {
	}

	@After()
	public void tearDown() throws Exception {
	}

	// *****************************************************
	// ******* COMPROBAR EL METODO getAll() ****************
	// *****************************************************
	@Test()
	public void testGetAll() {

		PerroController controller = new PerroController();
		// obtenemos la lista de perros
		Response response = controller.getAll("asc", "id");
		// comprobamos que nos ha devuelto lo que queremos
		assertEquals(SUCCESS00, response.getStatus());

		// comprobamos la ordenacion ascendente por id
		lista = (ArrayList<Perro>) response.getEntity();
		long idActual = -1;
		for (Perro p : lista) {
			assertTrue("ordenacion ascendente por id", idActual < p.getId());
			idActual = p.getId();
		}

		// comprobamos la ordenacion descendente por id
		response = controller.getAll("desc", "id");
		lista = (ArrayList<Perro>) response.getEntity();
		if (!lista.isEmpty()) {
			idActual = lista.get(0).getId() + 1;
			for (Perro p : lista) {
				assertTrue("ordenacion ascendente por id", idActual > p.getId());
				idActual = p.getId();
			}
		}

		// comprobamos la ordenacion campo inexistente
		response = controller.getAll("desc", "XXX");
		lista = (ArrayList<Perro>) response.getEntity();
		if (!lista.isEmpty()) {
			idActual = lista.get(0).getId() + 1;
			for (Perro p : lista) {
				assertTrue("ordenacion campo inexistente,debe ordernar desc por id", idActual > p.getId());
				idActual = p.getId();
			}
		}

	}

	// *********************************************************
	// ******* COMPROBAR EL METODO getById(id) *********
	// *****************************************************
	@Test()
	public void testGetById() {

		PerroController controller = new PerroController();
		// obtenemos toda la lista
		Response response = controller.getAll("asc", "id");
		lista = (ArrayList<Perro>) response.getEntity();
		// obtenemos el id del ultimo elemento
		int idExist = (int) (lista.get(lista.size() - 1).getId());
		// sumando 1 al ultimo id nos aseguramos de que no existe para
		// comprobarlo
		int idInexist = idExist + 1;
		// comprobamos que nos ha devuelto lo que queremos con id que existe
		response = controller.getById(idExist);
		assertEquals(SUCCESS00, response.getStatus());
		// comprobamos que nos ha devuelto lo que queremos con id que no existe
		response = controller.getById(idInexist);
		assertEquals(FAILURE04, response.getStatus());

	}

	// ******************************************************
	// ******* COMPROBAR EL METODO delete(id) ****************
	// *****************************************************
	@Test()
	public void testDelete() {

		PerroController controller = new PerroController();
		// obtenemos toda la lista
		Response response = controller.getAll("asc", "id");
		lista = (ArrayList<Perro>) response.getEntity();
		// obtenemos el id del ultimo elemento
		int idExist = (int) (lista.get(lista.size() - 1).getId());
		// sumando 1 al ultimo id nos aseguramos de que no existe para
		// comprobarlo
		int idInexist = idExist + 1;
		// comprobamos que nos ha devuelto lo que queremos con id que existe
		response = controller.delete(idExist);
		assertEquals(SUCCESS00, response.getStatus());
		// comprobamos que nos ha devuelto lo que queremos con id que no existe
		response = controller.delete(idInexist);
		assertEquals(FAILURE04, response.getStatus());

	}

	// *********************************************************
	// ******* COMPROBAR EL METODO insert(Perro perro) *********
	// *****************************************************
	@Test()
	public void testInsert() {

		PerroController controller = new PerroController();
		// insertamos un perro
		Response response = controller.post("Hola", "Caracola");
		// comprobamos que nos ha devuelto lo que queremos
		assertEquals(SUCCESS01, response.getStatus());

	}

	// *****************************************************
	// ******* COMPROBAR EL METODO update() ****************
	// *****************************************************
	@Test()
	public void testUpdate() {

		PerroController controller = new PerroController();
		// obtenemos toda la lista
		Response response = controller.getAll("asc", "id");
		lista = (ArrayList<Perro>) response.getEntity();
		// obtenemos el id del ultimo elemento
		int idExist = (int) (lista.get(lista.size() - 1).getId());
		// sumando 1 al ultimo id nos aseguramos de que no existe para
		// comprobarlo
		int idInexist = idExist + 1;
		// comprobamos que nos ha devuelto lo que queremos con id que existe
		response = controller.put(idExist, "txutxillo", "txotxillo");
		assertEquals(SUCCESS00, response.getStatus());
		// comprobamos que nos ha devuelto lo que queremos con id que no existe
		response = controller.put(idInexist, "esto no", "funciona");
		assertEquals(FAILURE04, response.getStatus());

	}

}

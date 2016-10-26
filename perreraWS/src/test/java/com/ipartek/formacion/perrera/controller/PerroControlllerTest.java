package com.ipartek.formacion.perrera.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ipartek.formacion.perrera.pojo.Perro;

public class PerroControlllerTest {

	ArrayList<Perro> lista = null;
	Perro perro = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetAll() {

		PerroController controller = new PerroController();

		Response response = controller.getAll("asc", "id");

		assertEquals(200, response.getStatus());

		// ordenacion ascendente por id
		lista = (ArrayList<Perro>) response.getEntity();
		long idActual = -1;
		for (Perro p : lista) {
			assertTrue("ordenacion ascendente por id", idActual < p.getId());
			idActual = p.getId();
		}

		// ordenacion descendente por id
		response = controller.getAll("desc", "id");
		lista = (ArrayList<Perro>) response.getEntity();
		if (!lista.isEmpty()) {
			idActual = lista.get(0).getId() + 1;
			for (Perro p : lista) {
				assertTrue("ordenacion ascendente por id", idActual > p.getId());
				idActual = p.getId();
			}
		}

		// ordenacion campo inexistente
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

	@Test
	public void testGetById() {

		PerroController controller = new PerroController();

		Response response = controller.getAll("asc", "id");
		lista = (ArrayList<Perro>) response.getEntity();
		int idExist = (int) (lista.get(lista.size() - 1).getId());
		int idInexist = idExist + 1;

		response = controller.getById(idExist);
		assertEquals(200, response.getStatus());

		response = controller.getById(idInexist);
		assertEquals(204, response.getStatus());

	}

	@Test
	public void testDelete() {

		PerroController controller = new PerroController();

		Response response = controller.getAll("asc", "id");
		lista = (ArrayList<Perro>) response.getEntity();
		int idExist = (int) (lista.get(lista.size() - 1).getId());
		int idInexist = idExist + 1;

		response = controller.delete(idExist);
		assertEquals(200, response.getStatus());

		response = controller.delete(idInexist);
		assertEquals(204, response.getStatus());

	}

	@Test
	public void testInsert() {

		PerroController controller = new PerroController();

		Response response = controller.post("Hola", "Caracola");
		assertEquals(201, response.getStatus());

	}

	@Test
	public void testUpdate() {

		PerroController controller = new PerroController();

		Response response = controller.getAll("asc", "id");
		lista = (ArrayList<Perro>) response.getEntity();
		int idExist = (int) (lista.get(lista.size() - 1).getId());
		int idInexist = idExist + 1;
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Date today = Calendar.getInstance().getTime();
		String nomORaza = df.format(today);
		response = controller.put(idExist, nomORaza, nomORaza);
		assertEquals(200, response.getStatus());

		response = controller.put(idInexist, nomORaza, nomORaza);
		assertEquals(204, response.getStatus());

	}

}

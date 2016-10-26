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

public class PerroControllerTest {

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

		ArrayList<Perro> lista = (ArrayList<Perro>) response.getEntity();
		long idActual = -1;
		for (Perro p : lista) {
			assertTrue("ordenacion ascendente por id", idActual < p.getId());
			idActual = p.getId();
		}

		// comprobar ordenacion ascendente por id

		response = controller.getAll("desc", "id");
		lista = (ArrayList<Perro>) response.getEntity();
		if (!lista.isEmpty()) {
			idActual = lista.get(0).getId() + 1;
			for (Perro p : lista) {
				assertTrue("ordenacion descendente por id", idActual > p.getId());
				idActual = p.getId();
			}
		}

		// comprobar ordenacion descendente por id

		// ordenacion campo inexistente
		response = controller.getAll("XXX", "id");
		lista = (ArrayList<Perro>) response.getEntity();
		if (!lista.isEmpty()) {
			idActual = lista.get(0).getId() + 1;
			for (Perro p : lista) {
				assertTrue("ordenacion campo inexistente, debe ordenar desc por id", idActual > p.getId());
				idActual = p.getId();
			}
		}
	}

	@Test
	public void testGetById() {
		PerroController controller = new PerroController();

		Response response = controller.getById(1);

		assertEquals(200, response.getStatus());
	}

	@Test
	public void testInsertar() {
		PerroController controller = new PerroController();

		Response response = controller.getAll("asc", "id");

		assertEquals(200, response.getStatus());

		response = controller.post("rufo", "perro salchicha");

		assertEquals(201, response.getStatus());
	}

	@Test
	public void testModificar() {
		PerroController controller = new PerroController();

		Response response = controller.getAll("asc", "id");

		assertEquals(200, response.getStatus());

		response = controller.put(4, "kit", "san bernardo");

		assertEquals(200, response.getStatus());
	}

	@Test
	public void testEliminar() {
		PerroController controller = new PerroController();

		Response response = controller.getAll("asc", "id");

		assertEquals(200, response.getStatus());

		response = controller.delete(4);

		assertEquals(200, response.getStatus());
	}
}

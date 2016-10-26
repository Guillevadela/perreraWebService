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

	@SuppressWarnings("unchecked")
	@Test
	public void testGetAll() {

		final PerroController controller = new PerroController();

		Response response = controller.getAll("asc", "id");

		assertEquals(200, response.getStatus());

		// ordenacion ascendente por id

		ArrayList<Perro> perros = (ArrayList<Perro>) response.getEntity();
		long idActual = -1;
		if (!perros.isEmpty()) {
			for (final Perro perro : perros) {
				assertTrue("ordenacion ascendente por id", idActual < perro.getId());
				idActual = perro.getId();
			}
		}

		// ordenacion descendiente por id
		response = controller.getAll("desc", "id");
		perros = (ArrayList<Perro>) response.getEntity();
		if (!perros.isEmpty()) {
			idActual = perros.get(0).getId() + 1;
			for (final Perro perro : perros) {
				assertTrue("ordenacion descendente por id", idActual > perro.getId());
				idActual = perro.getId();
			}
		}

		// ordenacion descendiente por id al ser inexistente
		response = controller.getAll("desc", "xxxx");
		perros = (ArrayList<Perro>) response.getEntity();
		if (!perros.isEmpty()) {
			idActual = perros.get(0).getId() + 1;
			for (final Perro perro : perros) {
				assertTrue("ordenacion descendente por id", idActual > perro.getId());
				idActual = perro.getId();
			}
		}
	}

	@Test
	public void testInsert() {

		final PerroController controller = new PerroController();

		final Response response = controller.post("NombrePrueba", "RazaPrueba");

		assertEquals(201, response.getStatus());

		final Perro perro = (Perro) response.getEntity();

		assertEquals("NombrePrueba", perro.getNombre());
		assertEquals("RazaPrueba", perro.getRaza());
	}

	@Test
	public void testGetById() {

		final PerroController controller = new PerroController();

		final Response response = controller.getById(-1);

		assertEquals(204, response.getStatus());

		// ordenacion ascendente por id

		final Perro perro = (Perro) response.getEntity();

		assertEquals(1, perro.getId());

	}

}
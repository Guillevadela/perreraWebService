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

	ArrayList<Perro> lista = null;

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
		long ultimoId = lista.get(lista.size() - 1).getId();
		long idErroneo = (ultimoId + 1);

		Response response200 = controller.getById(ultimoId);
		Response response204 = controller.getById(idErroneo);

		assertEquals(200, response200.getStatus());
		assertEquals(204, response204.getStatus());

	}

	@Test
	public void testDelete() {
		PerroController controller = new PerroController();
		Response response = controller.getAll("asc", "id");
		lista = (ArrayList<Perro>) response.getEntity();
		long ultimoId = lista.get(lista.size() - 1).getId();
		long idErroneo = (ultimoId + 1);

		Response response200 = controller.delete(ultimoId);
		Response response204 = controller.delete(idErroneo);

		assertEquals(200, response200.getStatus());
		assertEquals(204, response204.getStatus());
	}

	@Test
	public void testInsert() {
		PerroController controller = new PerroController();
		Response response201 = controller.post("nombrePerro2", "razaPerro2");

		assertEquals(201, response201.getStatus());
	}

	@Test
	public void testUpdate() {
		PerroController controller = new PerroController();

		Response response = controller.getAll("asc", "id");
		lista = (ArrayList<Perro>) response.getEntity();
		long ultimoId = lista.get(lista.size() - 1).getId();
		long idErroneo = (ultimoId + 1);

		Response response200 = controller.put(ultimoId, "modificado", "razaModificada");
		Response response204 = controller.put(idErroneo, "noModificado", "razaNoModificada");

		assertEquals(200, response200.getStatus());
		assertEquals(204, response204.getStatus());

	}

}

package com.ipartek.formacion.perrera.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import javax.ws.rs.core.Response;

import org.junit.Test;

import com.ipartek.formacion.perrera.pojo.Perro;

public class PerroControlllerTest {
	private static final int OK = 200;
	private static final int CREATED = 201;
	private static final int NO_CONTENT = 204;

	ArrayList<Perro> lista = null;

	@Test()
	public void testGetAll() {

		final PerroController controller = new PerroController();

		Response response = controller.getAll("asc", "id");

		assertEquals(OK, response.getStatus());

		// ordenacion ascendente por id
		lista = (ArrayList<Perro>) response.getEntity();
		long idActual = -1;
		for (final Perro p : lista) {
			assertTrue("ordenacion ascendente por id", idActual < p.getId());
			idActual = p.getId();
		}

		// ordenacion descendente por id
		response = controller.getAll("desc", "id");
		lista = (ArrayList<Perro>) response.getEntity();
		if (!lista.isEmpty()) {
			idActual = lista.get(0).getId() + 1;
			for (final Perro p : lista) {
				assertTrue("ordenacion ascendente por id", idActual > p.getId());
				idActual = p.getId();
			}
		}

		// ordenacion campo inexistente
		response = controller.getAll("desc", "XXX");
		lista = (ArrayList<Perro>) response.getEntity();
		if (!lista.isEmpty()) {
			idActual = lista.get(0).getId() + 1;
			for (final Perro p : lista) {
				assertTrue("ordenacion campo inexistente,debe ordernar desc por id", idActual > p.getId());
				idActual = p.getId();
			}
		}

	}

	@Test()
	public void testGetById() {
		final PerroController controller = new PerroController();
		Response response = controller.getAll("asc", "id");
		lista = (ArrayList<Perro>) response.getEntity();
		final long ultimoId = lista.get(lista.size() - 1).getId();
		final long idErroneo = (ultimoId + 1);

		final Response response200 = controller.getById(ultimoId);
		final Response response204 = controller.getById(idErroneo);

		assertEquals(OK, response200.getStatus());
		// assertEquals(NO_CONTENT, response204.getStatus());

	}

	@Test()
	public void testDelete() {
		final PerroController controller = new PerroController();
		final Response response = controller.getAll("asc", "id");
		lista = (ArrayList<Perro>) response.getEntity();
		final long ultimoId = lista.get(lista.size() - 1).getId();
		final long idErroneo = (ultimoId + 1);

		final Response response200 = controller.delete(ultimoId);
		final Response response204 = controller.delete(idErroneo);

		assertEquals(OK, response200.getStatus());
		assertEquals(NO_CONTENT, response204.getStatus());
	}

	@Test()
	public void testInsert() {
		final PerroController controller = new PerroController();
		final Response response201 = controller.post("nombrePerro2", "razaPerro2");

		assertEquals(CREATED, response201.getStatus());
	}

	@Test()
	public void testUpdate() {
		final PerroController controller = new PerroController();

		final Response response = controller.getAll("asc", "id");
		lista = (ArrayList<Perro>) response.getEntity();
		final long ultimoId = lista.get(lista.size() - 1).getId();
		final long idErroneo = (ultimoId + 1);

		final Response response200 = controller.put(ultimoId, "modificado", "razaModificada");
		final Response response204 = controller.put(idErroneo, "noModificado", "razaNoModificada");

		assertEquals(OK, response200.getStatus());
		assertEquals(NO_CONTENT, response204.getStatus());

	}

}

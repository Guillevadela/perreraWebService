package com.ipartek.formacion.perrera.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import com.ipartek.formacion.perrera.dao.PerroDAOImpl;
import com.ipartek.formacion.perrera.pojo.Perro;

public class PerroServiceImplTest {

	PerroDAOImpl dao = PerroDAOImpl.getInstance();

	ArrayList<Perro> lista = null;
	
	Perro perro = null;

	@Test
	public void testGetAll() {

		// ordenacion ascendente por id
		lista = (ArrayList<Perro>) dao.getAll("asc", "id");
		long idActual = -1;
		for (Perro p : lista) {
			assertTrue("ordenacion ascendente por id", idActual < p.getId());
			idActual = p.getId();
		}

		// ordenacion descendente por id
		lista = (ArrayList<Perro>) dao.getAll("desc", "id");
		if (!lista.isEmpty()) {
			idActual = lista.get(0).getId() + 1;
			for (Perro p : lista) {
				assertTrue("ordenacion ascendente por id", idActual > p.getId());
				idActual = p.getId();
			}
		}

		// ordenacion campo inexistente
		lista = (ArrayList<Perro>) dao.getAll("XXX", "id");
		if (!lista.isEmpty()) {
			idActual = -1;
			for (Perro p : lista) {
				assertTrue("ordenacion campo inexistente,debe ordernar desc por id", idActual < p.getId());
				idActual = p.getId();
			}
		}
	}
	
	@Test
	public void testGetById() {
		
		perro = dao.getById(1);

		assertEquals(perro.getId(), 1);
		assertEquals(perro.getNombre(), "buba");
		assertEquals(perro.getRaza(), "boster");
	}

	@Test
	public void testInsertar() {

		perro = new Perro("prueba", "mezcla");
		assertTrue(dao.insert(perro));
	}

	@Test
	public void testModificar() {
		
		ArrayList<Perro> lista = (ArrayList<Perro>) dao.getAll("asc", "id");
		long ultimaId = 0;
		for (Perro p : lista) {
			ultimaId = p.getId();
		}

		perro = new Perro(ultimaId, "pepe", "salchicha");
		assertTrue(dao.update(perro));
	}

	@Test
	public void testEliminar() {
		
		lista = (ArrayList<Perro>) dao.getAll("asc", "id");
		long ultimaId = 0;
		for (Perro p : lista) {
			ultimaId = p.getId();
		}
		assertTrue(dao.delete(ultimaId));
	}
}

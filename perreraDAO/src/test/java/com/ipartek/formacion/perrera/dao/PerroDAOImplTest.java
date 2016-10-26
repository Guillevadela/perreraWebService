package com.ipartek.formacion.perrera.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import com.ipartek.formacion.perrera.pojo.Perro;

public class PerroDAOImplTest {

	@Test
	public void test() {
		PerroDAOImpl dao = PerroDAOImpl.getInstance();

		ArrayList<Perro> lista = (ArrayList<Perro>) dao.getAll("asc", "id");

		int perrosIniciales = lista.size();

		Perro perro = new Perro("Lasy", "chihuahua");
		assertTrue("Fallo al insertar", dao.insert(perro));
		assertTrue("Se nos olvido settear id en perro", perro.getId() > 0);

		lista = (ArrayList<Perro>) dao.getAll("asc", "id");

		assertEquals(perrosIniciales + 1, lista.size());
		assertEquals("Lasy", dao.getById(perro.getId()).getNombre());
		assertEquals("chihuahua", dao.getById(perro.getId()).getRaza());

		perro.setNombre("Kai");
		perro.setRaza("Pastor Vasco");
		assertTrue("Fallo al modificar", dao.update(perro));
		assertEquals("Kai", dao.getById(perro.getId()).getNombre());
		assertEquals("Pastor Vasco", dao.getById(perro.getId()).getRaza());

		assertTrue("Fallo al eliminar", dao.delete(perro.getId()));
		lista = (ArrayList<Perro>) dao.getAll("asc", "id");
		assertEquals(perrosIniciales, lista.size());
		
		assertFalse("No se puede eliminar algo que no existe", dao.delete(0));
		assertNull("No se puede recuperar algo que no existe", dao.getById(0));

	}

}

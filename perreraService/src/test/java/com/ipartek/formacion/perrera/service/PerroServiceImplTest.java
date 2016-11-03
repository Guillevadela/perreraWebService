package com.ipartek.formacion.perrera.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import com.ipartek.formacion.perrera.pojo.Perro;

public class PerroServiceImplTest {

	@Test
	public void test() {
		PerroServiceImpl service = PerroServiceImpl.getInstance();

		ArrayList<Perro> lista = (ArrayList<Perro>) service.getAll("asc", "id");

		int perrosIniciales = lista.size();

		Perro perro = new Perro("Lasy", "chihuahua");
		assertTrue("Fallo al insertar", service.insert(perro));
		assertTrue("Se nos olvido settear id en perro", perro.getId() > 0);

		lista = (ArrayList<Perro>) service.getAll("asc", "id");

		assertEquals(perrosIniciales + 1, lista.size());
		assertEquals("Lasy", service.getById(perro.getId()).getNombre());
		assertEquals("chihuahua", service.getById(perro.getId()).getRaza());

		perro.setNombre("Kai");
		perro.setRaza("Pastor Vasco");
		assertTrue("Fallo al modificar", service.update(perro));
		assertEquals("Kai", service.getById(perro.getId()).getNombre());
		assertEquals("Pastor Vasco", service.getById(perro.getId()).getRaza());

		assertTrue("Fallo al eliminar", service.delete(perro.getId()));
		lista = (ArrayList<Perro>) service.getAll("asc", "id");
		assertEquals(perrosIniciales, lista.size());
		
		assertFalse("No se puede eliminar algo que no existe", service.delete(0));
		assertNull("No se puede recuperar algo que no existe", service.getById(0));
	}

}

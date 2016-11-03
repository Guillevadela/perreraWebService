package com.ipartek.formacion.perrera.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import javax.ws.rs.core.Response;

import org.junit.Test;

import com.ipartek.formacion.perrera.pojo.Perro;

public class PerroControllerTest {
	
	PerroController controller = new PerroController();

	ArrayList<Perro> lista = null;
	
	@Test
	public void testGetAll() {

		Response response = controller.getAll("asc", "id");
		
		assertEquals( 200, response.getStatus() );
		
		//ordenacion ascendente por id	
		lista = (ArrayList<Perro>) response.getEntity();	
		long idActual = -1;
		for ( Perro p : lista ){			
			assertTrue("ordenacion ascendente por id", idActual < p.getId() );
			idActual = p.getId();						
		}		
		
		//ordenacion descendente por id
		response = controller.getAll("desc", "id");
		lista = (ArrayList<Perro>) response.getEntity();
		if ( !lista.isEmpty() ){
			idActual = lista.get(0).getId()+1;
			for ( Perro p : lista ){			
				assertTrue("ordenacion ascendente por id", idActual > p.getId() );
				idActual = p.getId();						
			}	
		}	
		
		//ordenacion campo inexistente		
		response = controller.getAll("XXX", "id");
		lista = (ArrayList<Perro>) response.getEntity();
		if ( !lista.isEmpty() ){
			idActual = -1;
			for ( Perro p : lista ){			
				assertTrue("ordenacion campo inexistente,debe ordernar desc por id", idActual < p.getId() );
				idActual = p.getId();						
			}
		}
	}

	@Test
	public void testGetById() {

		Response response = controller.getById(1);

		assertEquals(200, response.getStatus());
	}

	@Test
	public void testInsertar() {

		Response response = controller.getAll("asc", "id");

		assertEquals(200, response.getStatus());

		response = controller.post("rufo", "perro salchicha");

		assertEquals(201, response.getStatus());
	}

	@Test
	public void testModificar() {

		Response response = controller.getAll("asc", "id");

		assertEquals(200, response.getStatus());
		
		ArrayList<Perro> lista = (ArrayList<Perro>) response.getEntity();
		long ultimaId = 0;
		for (Perro p : lista) {
			ultimaId = p.getId();
		}

		response = controller.put(ultimaId, "kit", "san bernardo");

		assertEquals(200, response.getStatus());
	}

	@Test
	public void testEliminar() {

		Response response = controller.getAll("asc", "id");

		assertEquals(200, response.getStatus());
		
		lista = (ArrayList<Perro>) response.getEntity();
		long ultimaId = 0;
		for (Perro p : lista) {
			ultimaId = p.getId();
		}

		response = controller.delete(ultimaId);

		assertEquals(200, response.getStatus());
	}
}

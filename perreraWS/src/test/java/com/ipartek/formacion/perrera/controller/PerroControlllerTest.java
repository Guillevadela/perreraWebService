package com.ipartek.formacion.perrera.controller;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ipartek.formacion.perrera.pojo.Perro;

import javax.ws.rs.core.Response;

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

	/**
	 * Test para probar metodo getAll(orden, campo) y comprobar que devuelve una lista de perros
	 */
	@Test
	public void testGetAll() {
		
		PerroController controller = new PerroController();
		
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
		response = controller.getAll("desc", "XXX");
		lista = (ArrayList<Perro>) response.getEntity();
		if ( !lista.isEmpty() ){
			idActual = lista.get(0).getId()+1;
			for ( Perro p : lista ){			
				assertTrue("ordenacion campo inexistente,debe ordernar desc por id", idActual > p.getId() );
				idActual = p.getId();						
			}
		}	
		
	}
	
	
	/**
	 * Test para probar metodo getById(id) y comprobar que devuelve Perro con id='id'
	 */
	@Test
	public void testGetById() {
		
		PerroController controller = new PerroController();
		
		//creamos un nuevo Perro y le asignamos un id
				
		Response response = controller.post("manolin", "esquimal");
		
		 Perro perro = response.getEntity();
		
		
		
		//comprobamos que nos devuelve el perro con id=1
		assertEquals(200, response.getStatus());
		
		//comprobamos que devuelve 1 perro
		//assertTrue("no devuelve perro", response. );
		
		// comprobamos que no existe perro con id=-1 
		response = controller.getById(-1);
		assertEquals(204, response.getStatus());
	
	}
	
	
	/**
	 * Test para probar metodo delete(id) y comprobar que borra Perro con id='id'
	 */
	@Test
	public void testDelete() {
		
		PerroController controller = new PerroController();
		
		//creamos un nuevo Perro y le asignamos un id
		Perro miPerro =  new Perro();
		miPerro.setId(1);
		
		//comprobamos que borra el Perro con id=1
		Response response = controller.delete(1);
		assertEquals(200, response.getStatus());
		
		// comprobamos que no existe perro con id=-1 
		response = controller.getById(-1);
		assertEquals(204, response.getStatus());
		
		
	}
	
	
	

}

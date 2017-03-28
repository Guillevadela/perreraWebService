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

	@Test
	public void testGetAll() {
		
		PerroController controller = new PerroController();
		
		Response response = controller.getAll("asc", "id");
		
		int status = response.getStatus();
		assertTrue("Tiene que retornar 200 o 204",  status == 200 || status == 204  );
		
		
		//ordenacion ascendente por id	
		lista = (ArrayList<Perro>) response.getEntity();
		
		if ( lista != null && !lista.isEmpty() ){
			
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
		}//lista is empty	
		
	}

}

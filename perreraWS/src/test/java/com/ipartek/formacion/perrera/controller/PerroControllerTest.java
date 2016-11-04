package com.ipartek.formacion.perrera.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import javax.ws.rs.core.Response;

import org.junit.Test;

import com.ipartek.formacion.perrera.pojo.Perro;

public class PerroControllerTest {
	
	private static final String NOM_MODIF = "NombreModificado";
	private static final String RAZA_MODIF = "RazaModificada";

	@Test
	public void testGetAll() {
		
		PerroController controller = new PerroController();
		
		Response response = controller.getAll("asc", "id");

		//comprobar ordenacion ascendente por id
		assertEquals(200, response.getStatus());
		ArrayList<Perro> lista =  (ArrayList<Perro>) response.getEntity();
		int idActual = -1;
		for(Perro p : lista){
				assertTrue("Ordenacion ascendente por id",idActual < p.getId());
				idActual = p.getId();		
		}		
		
		
		//comprobar ordenacion DEScendente por id
		response = controller.getAll("desc", "id");
		lista =  (ArrayList<Perro>) response.getEntity();
		if(!lista.isEmpty()){
			idActual = lista.get(0).getId()+1;
			for(Perro p : lista){
					assertTrue("Ordenacion ascendente por id",idActual > p.getId());
					idActual = p.getId();						
			}
		}
		
		// ordenacion campo inexistente
		response = controller.getAll("desc", "XXX");
		lista =  (ArrayList<Perro>) response.getEntity();
		if(!lista.isEmpty()){
			idActual = lista.get(0).getId()+1;
			for(Perro p : lista){
					assertTrue("Ordenacion campo inexistente, debe ordenar desc por id",idActual > p.getId());
					idActual = p.getId();				
			
			}			
		}		
		
		//crear perro
		Perro perro = new Perro("NombreTest", "RazaTest");
		
		response = controller.post(perro.getNombre(), perro.getRaza());
		assertEquals("Hubo algun error al crear el Perro", 201,response.getStatus());
		Perro pRecibido = (Perro) response.getEntity();
		
		//getbyID
		response = controller.getById(pRecibido.getId());
		pRecibido = (Perro) response.getEntity();
		assertTrue("NombreTest".equals(pRecibido.getNombre()));
		assertTrue("RazaTest".equals(pRecibido.getRaza()));
		
		//modificar perro
		response = controller.put(pRecibido.getId(), NOM_MODIF, RAZA_MODIF);
		assertEquals("Hubo algun error al modificar el Perro", 200,response.getStatus());
		Perro pModificado = (Perro) response.getEntity();
		
		assertTrue(NOM_MODIF.equals(pModificado.getNombre()));
		assertTrue(RAZA_MODIF.equals(pModificado.getRaza()));
		
		//modificar un perro con id que no existe
		response = controller.put(0, NOM_MODIF, RAZA_MODIF);
		assertEquals("El id no deberia existir", 204,response.getStatus());
		
		//eliminar perro pasando id
		response = controller.delete(pModificado.getId());
		assertEquals(200, response.getStatus());
	
		
	}
}

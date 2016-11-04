package com.ipartek.formacion.perrera.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import javax.ws.rs.core.Response;

import org.junit.Test;

import com.ipartek.formacion.perrera.pojo.Perro;

/**
 * Test del controlador de perreraWebService
 * 
 * @author EkaitzAF
 *
 */
public class PerroControlllerTest {

	/**
	 * variable para exito 200 en response
	 */
	private static final int SUCCESS00 = 200;
	/**
	 * variable para exito 201 en response
	 */
	private static final int SUCCESS01 = 201;
	/**
	 * variable para fallo 204 en response
	 */
	private static final int FAILURE04 = 204;
	/**
	 * lista perros para los tests
	 */
	private ArrayList<Perro> lista = null;
	/**
	 * lista perros para los tests
	 */
	private static final String ORDEN_ASC = "asc";

	/**
	 * ******* COMPROBAR EL METODO getAll() ****************
	 */
	@SuppressWarnings("unchecked")
	@Test()
	public void testGetAll() {

		final PerroController controller = new PerroController();
		// obtenemos la lista de perros
		Response response = controller.getAll(ORDEN_ASC, "id");
		// comprobamos que nos ha devuelto lo que queremos
		assertEquals(SUCCESS00, response.getStatus());

		// comprobamos la ordenacion ascendente por id
		lista = (ArrayList<Perro>) response.getEntity();
		long idActual = -1;
		for (final Perro p : lista) {
			assertTrue("ordenacion ascendente por id", idActual < p.getId());
			idActual = p.getId();
		}

		// comprobamos la ordenacion descendente por id
		response = controller.getAll("desc", "id");
		lista = (ArrayList<Perro>) response.getEntity();
		if (!lista.isEmpty()) {
			idActual = lista.get(0).getId() + 1;
			for (final Perro p : lista) {
				assertTrue("ordenacion ascendente por id", idActual > p.getId());
				idActual = p.getId();
			}
		}

		// comprobamos la ordenacion campo inexistente
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

	/**
	 * ******* COMPROBAR EL METODO getById(id) *********
	 */
	@Test()
	public void testGetById() {

		final PerroController controller = new PerroController();
		// obtenemos toda la lista
		Response response = controller.getAll(ORDEN_ASC, "id");
		lista = (ArrayList<Perro>) response.getEntity();
		// obtenemos el id del ultimo elemento
		final int idExist = (int) (lista.get(lista.size() - 1).getId());
		// sumando 1 al ultimo id nos aseguramos de que no existe para
		// comprobarlo
		final int idInexist = idExist + 1;
		// comprobamos que nos ha devuelto lo que queremos con id que existe
		response = controller.getById(idExist);
		assertEquals(SUCCESS00, response.getStatus());
		// comprobamos que nos ha devuelto lo que queremos con id que no existe
		response = controller.getById(idInexist);
		assertEquals(FAILURE04, response.getStatus());

	}

	/**
	 * ******* COMPROBAR EL METODO delete(id) ****************
	 * 
	 */
	@Test()
	public void testDelete() {

		final PerroController controller = new PerroController();
		// obtenemos toda la lista
		Response response = controller.getAll(ORDEN_ASC, "id");
		lista = (ArrayList<Perro>) response.getEntity();
		// obtenemos el id del ultimo elemento
		final int idExist = (int) (lista.get(lista.size() - 1).getId());
		// sumando 1 al ultimo id nos aseguramos de que no existe para
		// comprobarlo
		final int idInexist = idExist + 1;
		// comprobamos que nos ha devuelto lo que queremos con id que existe
		response = controller.delete(idExist);
		assertEquals(SUCCESS00, response.getStatus());
		// comprobamos que nos ha devuelto lo que queremos con id que no existe
		response = controller.delete(idInexist);
		assertEquals(FAILURE04, response.getStatus());

	}

	/**
	 * ******* COMPROBAR EL METODO insert(Perro perro) *********
	 */
	@Test()
	public void testInsert() {

		final PerroController controller = new PerroController();
		// insertamos un perro
		final Response response = controller.post("Hola", "Caracola");
		// comprobamos que nos ha devuelto lo que queremos
		assertEquals(SUCCESS01, response.getStatus());

	}

	/**
	 * ******* COMPROBAR EL METODO update() ****************
	 */
	@Test()
	public void testUpdate() {

		final PerroController controller = new PerroController();
		// obtenemos toda la lista
		Response response = controller.getAll(ORDEN_ASC, "id");
		lista = (ArrayList<Perro>) response.getEntity();
		// obtenemos el id del ultimo elemento
		final int idExist = (int) (lista.get(lista.size() - 1).getId());
		// sumando 1 al ultimo id nos aseguramos de que no existe para
		// comprobarlo
		final int idInexist = idExist + 1;
		// comprobamos que nos ha devuelto lo que queremos con id que existe
		response = controller.put(idExist, "txutxillo", "txotxillo");
		assertEquals(SUCCESS00, response.getStatus());
		// comprobamos que nos ha devuelto lo que queremos con id que no existe
		response = controller.put(idInexist, "esto no", "funciona");
		assertEquals(FAILURE04, response.getStatus());

	}

}

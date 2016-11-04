package com.ipartek.formacion.perrera.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import javax.ws.rs.core.Response;

import org.junit.Test;

import com.ipartek.formacion.perrera.pojo.Perro;

/**
 * Test para comprobar funcionamiento del Controlador
 * 
 * @author JHM
 *
 */
public class PerroControlllerTest {

	final static String NOMBRE_PERRO = "Bernardo";
	public final static String RAZA_PERRO = "San Berardo";
	public final static String NOMBRE_PERRO_UPDATE = "Bernardino";
	public final static String RAZA_PERRO_UPDATE = "Esquimal";
	public final static long ID_ERRONEA = -1;

	private static final int CODE200 = 200;
	private static final int CODE201 = 201;
	private static final int CODE204 = 204;
	private static final int CODE409 = 409;
	private static final int CODE500 = 500;

	private ArrayList<Perro> lista = null;

	/**
	 * Test para probar metodo getAll(orden, campo) y comprobar que devuelve una
	 * lista de perros
	 */
	@Test()
	public void testGetAll() {

		PerroController controller = new PerroController();

		Response response = controller.getAll("asc", "id");

		assertEquals(CODE200, response.getStatus());

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

	/**
	 * Test para probar metodo post(nombre, raza) y comprobar que crea un perro
	 * nuevo
	 */
	@Test()
	public void testPost() {

		long idPerroTest = 0;

		PerroController controller = new PerroController();

		Response response = controller.post(NOMBRE_PERRO, RAZA_PERRO);

		// Comprobamos respuesta 201 al crear el perro correctamente
		assertEquals(CODE201, response.getStatus());

		// Obtenemos datos del perro creado y comprobamos que son los correctos
		Perro perro = (Perro) response.getEntity();

		idPerroTest = perro.getId();
		assertEquals(NOMBRE_PERRO, perro.getNombre());
		assertEquals(RAZA_PERRO, perro.getRaza());
		assertNotEquals(0, idPerroTest);

	}

	/**
	 * Test para probar a la vez los metodos: insertar post(nombre, raza) buscar
	 * por id getById(id) modificar(update) put(id, nombre, raza) borrar
	 * delete(id)
	 * 
	 * 
	 * nuevo
	 */
	@Test()
	public void test() {

		/*
		 * ----------------------- TEST INSERTAR ------------------------
		 */

		long idPerroTest = 0;

		final PerroController controller = new PerroController();

		// crear perro
		Response response = controller.post(NOMBRE_PERRO, RAZA_PERRO);

		// respuesta 201 al crear correctamente el perro
		assertEquals(CODE201, response.getStatus());

		// recogido perro creado
		Perro perro = (Perro) response.getEntity();

		idPerroTest = perro.getId();
		assertEquals(NOMBRE_PERRO, perro.getNombre());
		assertEquals(RAZA_PERRO, perro.getRaza());
		assertNotEquals(0, idPerroTest);

		/*
		 * ----------------------- TEST BUSQUEDA POR ID ----------------------
		 */

		// buscamos por id erronea
		response = controller.getById(ID_ERRONEA);

		// devuelve un valor distinto a 500 si no ha habido un error en el
		// servidor
		assertNotEquals(CODE500, response.getStatus());
		// devuelve 204 si la id es erronea
		assertEquals(CODE204, response.getStatus());

		// buscamos por id creada anteriormente en el test insert
		response = controller.getById(idPerroTest);

		// devuelve 200 si la respuesta es correcta
		assertEquals(CODE200, response.getStatus());

		// recogemos perro de la respuesta anterior
		perro = (Perro) response.getEntity();

		// tiene que devolver la misma id
		assertEquals(idPerroTest, perro.getId());

		/*
		 * ------------------------ TEST UPDATE ------------------------
		 */

		// modificamos perro creado
		response = controller.put(ID_ERRONEA, NOMBRE_PERRO_UPDATE, RAZA_PERRO_UPDATE);

		// devuelve un valor distinto a 500 si no ha habido un error en el
		// servidor
		assertNotEquals(CODE500, response.getStatus());
		// devuelve 204 si la id es erronea
		assertEquals(CODE204, response.getStatus());

		// devuelve 409 si el perro no se puede modificar
		assertNotEquals(CODE409, response.getStatus());

		// modificamos perro creado con id correcta
		response = controller.put(idPerroTest, NOMBRE_PERRO_UPDATE, RAZA_PERRO_UPDATE);

		// devuelve 200 si la respuesta es correcta
		assertEquals(CODE200, response.getStatus());

		// recogemos perro de la respuesta anterior
		perro = (Perro) response.getEntity();

		// tiene que devolver la misma id
		assertEquals(idPerroTest, perro.getId());

		// tiene que devolver el nombre modificado
		assertEquals(NOMBRE_PERRO_UPDATE, perro.getNombre());

		// tiene que devolver el nombre modificado
		assertEquals(RAZA_PERRO_UPDATE, perro.getRaza());

		/*
		 * --------------------- TEST DELETE ---------------------
		 */

		// eliminamos por id erronea
		response = controller.delete(ID_ERRONEA);

		// devuelve un valor distinto a 500 si no ha habido un error en el
		// servidor
		assertNotEquals(CODE500, response.getStatus());

		// devuelve 204 ya que la id es erronea
		assertEquals(CODE204, response.getStatus());

		// buscamos por id creada anteriormente en el test insert para eliminar
		response = controller.delete(idPerroTest);

		// devuelve 200 si la respuesta es correcta
		assertEquals(CODE200, response.getStatus());

		// buscamos por id eliminada anteriormente
		response = controller.getById(idPerroTest);

		// devuelve 204 ya que la id ya no existe
		assertEquals(CODE204, response.getStatus());

	}

}

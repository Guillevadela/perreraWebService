package com.ipartek.formacion.perrera.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import javax.ws.rs.core.Response;

import org.junit.Test;

import com.ipartek.formacion.perrera.pojo.Perro;

public class PerroControlllerTest {

	public final static String NOMBRE_PERRO = "Jack";
	public final static String RAZA_PERRO = "Doberman";
	public final static String NOMBRE_PERRO_UPDATE = "JackU";
	public final static String RAZA_PERRO_UPDATE = "DobermanU";
	public final static int ID_ERRONEA = -1;
	

	/*
	 * @BeforeClass public static void setUpBeforeClass() throws Exception { }
	 * 
	 * @AfterClass public static void tearDownAfterClass() throws Exception { }
	 * 
	 * @Before public void setUp() throws Exception { }
	 * 
	 * @After public void tearDown() throws Exception { }
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testGetAll() {

		final PerroController controller = new PerroController();

		Response response = controller.getAll("asc", "id");

		assertEquals(200, response.getStatus());

		// ordenacion ascendente por id

		ArrayList<Perro> perros = (ArrayList<Perro>) response.getEntity();
		long idActual = -1;
		if (!perros.isEmpty()) {
			for (final Perro perro : perros) {
				assertTrue("ordenacion ascendente por id", idActual < perro.getId());
				idActual = perro.getId();
			}
		}

		// ordenacion descendiente por id
		response = controller.getAll("desc", "id");
		perros = (ArrayList<Perro>) response.getEntity();
		if (!perros.isEmpty()) {
			idActual = perros.get(0).getId() + 1;
			for (final Perro perro : perros) {
				assertTrue("ordenacion descendente por id", idActual > perro.getId());
				idActual = perro.getId();
			}
		}

		// ordenacion descendiente por id al ser inexistente
		response = controller.getAll("desc", "xxxx");
		perros = (ArrayList<Perro>) response.getEntity();
		if (!perros.isEmpty()) {
			idActual = perros.get(0).getId() + 1;
			for (final Perro perro : perros) {
				assertTrue("ordenacion descendente por id", idActual > perro.getId());
				idActual = perro.getId();
			}
		}
	}

	@Test
	public void test() {

		/*TEST INSERTAR*/
		
		long idPerroTest = 0;
		
		final PerroController controller = new PerroController();

		// crear perro
		Response response = controller.post(NOMBRE_PERRO, RAZA_PERRO);

		// respuesta 201 al crear correctamente el perro
		assertEquals(201, response.getStatus());

		// recogido perro creado
		Perro perro = (Perro) response.getEntity();

		idPerroTest=perro.getId();
		assertEquals(NOMBRE_PERRO, perro.getNombre());
		assertEquals(RAZA_PERRO, perro.getRaza());
		assertNotEquals(0, idPerroTest);

		/* TEST BUSQUEDA POR ID*/
		
		// buscamos por id erronea
		response = controller.getById(ID_ERRONEA);

		// devuelve un valor distinto a 500 si no ha habido un error en el
		// servidor
		assertNotEquals(500, response.getStatus());
		// devuelve 204 si la id es erronea
		assertEquals(204, response.getStatus());

		// buscamos por id creada anteriormente en el test insert
		response = controller.getById(idPerroTest);

		// devuelve 200 si la respuesta es correcta
		assertEquals(200, response.getStatus());

		// recogemos perro de la respuesta anterior
		perro = (Perro) response.getEntity();

		// tiene que devolver la misma id
		assertEquals(idPerroTest, perro.getId());

		/*TEST UPDATE*/
		
		//modificamos perro creado 
		response = controller.put(idPerroTest, NOMBRE_PERRO_UPDATE,RAZA_PERRO_UPDATE);
		
		// devuelve un valor distinto a 500 si no ha habido un error en el
		// servidor
		assertNotEquals(500, response.getStatus());
		// devuelve 204 si la id es erronea
		assertNotEquals(204, response.getStatus());
		// devuelve 409 si el perro no se puede modificar
		assertNotEquals(409, response.getStatus());
		// devuelve 200 si la respuesta es correcta
		assertEquals(200, response.getStatus());
		
		// recogemos perro de la respuesta anterior
		perro = (Perro) response.getEntity();
		
		// tiene que devolver la misma id
		assertEquals(idPerroTest, perro.getId());
		
		// tiene que devolver el nombre modificado
		assertEquals(NOMBRE_PERRO_UPDATE, perro.getNombre());
		
		// tiene que devolver el nombre modificado
		assertEquals(RAZA_PERRO_UPDATE, perro.getRaza());
		
		/*TEST DELETE*/
		
		// eliminamos por id erronea
		response = controller.delete(ID_ERRONEA);

		// devuelve un valor distinto a 500 si no ha habido un error en el
		// servidor
		assertNotEquals(500, response.getStatus());

		// devuelve 204 ya que la id es erronea
		assertEquals(204, response.getStatus());

		// buscamos por id creada anteriormente en el test insert para eliminar
		response = controller.delete(idPerroTest);

		// devuelve 200 si la respuesta es correcta
		assertEquals(200, response.getStatus());

		// buscamos por id eliminada anteriormente
		response = controller.getById(idPerroTest);

		// devuelve 204 ya que la id ya no existe
		assertEquals(204, response.getStatus());

	}

}
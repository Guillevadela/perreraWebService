package com.ipartek.formacion.perrera.service;

import java.util.ArrayList;

import org.junit.Test;

import com.ipartek.formacion.perrera.pojo.Perro;

import junit.framework.TestCase;

/**
 * Test del Service de perreraWebService
 * 
 * @author EkaitzAF
 *
 */
public class PerroServiceTest extends TestCase {

	/**
	 * ******* COMPROBAR EL METODO getAll() ****************
	 */
	@Test()
	public void testGetAll() {
		final PerroServiceImpl service = PerroServiceImpl.getInstance();
		// obtenemos la lista de perros
		final ArrayList<Perro> lista = (ArrayList<Perro>) service.getAll("asc", "id");
		// comprobamos que la lista no es nula
		assertNotNull("No puede ser null", lista);
	}

	/**
	 * ******* COMPROBAR EL METODO getById(id) *********
	 */
	@Test()
	public void testGetById() {
		final PerroServiceImpl service = PerroServiceImpl.getInstance();
		// obtenemos toda la lista
		final ArrayList<Perro> lista = (ArrayList<Perro>) service.getAll("asc", "id");
		// obtenemos el id del ultimo elemento
		final int idExist = (int) (lista.get(lista.size() - 1).getId());
		// sumando 1 al ultimo id nos aseguramos de que no existe para
		// comprobarlo
		final int idInexist = idExist + 1;
		final Perro perroExist = service.getById(idExist);
		/**
		 * comprobamos que nos ha devuelto lo que queremos con id que existe
		 */
		assertTrue(0 < perroExist.getId());
		final Perro perroNoExist = service.getById(idInexist);
		/**
		 * comprobamos que nos ha devuelto lo que queremos con id que no existe
		 */
		assertSame(0, perroNoExist.getId());
	}

	/**
	 * ******* COMPROBAR EL METODO delete(id) ****************
	 */
	@Test()
	public void testDelete() {
		final PerroServiceImpl service = PerroServiceImpl.getInstance();
		// obtenemos toda la lista
		final ArrayList<Perro> lista = (ArrayList<Perro>) service.getAll("asc", "id");
		// obtenemos el id del ultimo elemento
		final int idExist = (int) (lista.get(lista.size() - 1).getId());
		// sumando 1 al ultimo id nos aseguramos de que no existe para
		// comprobarlo
		final int idInexist = idExist + 1;
		/**
		 * comprobamos que nos ha devuelto lo que queremos con id que existe
		 */
		assertTrue(service.delete(idExist));
		/**
		 * comprobamos que nos ha devuelto lo que queremos con id que no existe
		 */
		assertFalse(service.delete(idInexist));
	}

	/**
	 * ******* COMPROBAR EL METODO insert(Perro perro) *********
	 */
	@Test()
	public void testInsert() {
		final PerroServiceImpl service = PerroServiceImpl.getInstance();
		final Perro perro = new Perro("txutxo", "acasuso");
		// comprobamos que nos ha devuelto lo que queremos con id que existe
		assertTrue("fallo al insertar perro", service.insert(perro));
		// comprobar que tenga id generado
		assertTrue("Se nos olvido settear id en perro", perro.getId() > 0);
	}

	/**
	 * ******* COMPROBAR EL METODO update() ****************
	 */
	@Test()
	public void testUpdate() {
		final PerroServiceImpl service = PerroServiceImpl.getInstance();
		// cambiamos los parámetro de 'nombre' y 'raza' al perro creado
		final Perro perro = new Perro("Lur", "Doberman");
		assertTrue("fallo al insertar perro", service.insert(perro));
		perro.setNombre("txutxo");
		perro.setRaza("modifikutxo");
		// comprobamos que lo actualiza correctamente
		assertTrue("Fallo al actualizar", service.update(perro));
	}
}

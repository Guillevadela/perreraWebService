package com.ipartek.formacion.perrera.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import com.ipartek.formacion.perrera.pojo.Perro;

/**
 * 
 * @author Adassoy
 *
 */
public class PerroDAOImplTest {

	@Test()
	/**
	 * Test para testear PerroDAOImpl
	 */
	public void test() {

		final PerroDAOImpl dao = PerroDAOImpl.getInstance();
		// pInicio = Perros iniciales
		int pInicio;
		// pMasUno = Numero de perros tras insertar uno
		int pMasUno;
		// pMenosUno = Numero de perros tras eliminar uno
		int pMenosUno;

		// *****************************************************
		// ******* COMPROBAR EL METODO getAll() ****************
		// *****************************************************
		// obtenemos la lista de perros
		final ArrayList<Perro> lista = (ArrayList<Perro>) dao.getAll("asc", "id");
		// comprobamos que la lista no es nula
		assertNotNull("No puede ser null", lista);
		pInicio = lista.size();

		// *********************************************************
		// ******* COMPROBAR EL METODO insert(Perro perro) *********
		// *****************************************************
		// creamos un perro nuevo
		final Perro perro = new Perro();
		perro.setNombre("Boby");
		perro.setRaza("Caniche");

		// comprobamos que inserta OK
		assertTrue("Fallo al insertar", dao.insert(perro));

		// comprobar que tenga id generado
		assertTrue("Se nos olvido settear id en perro", perro.getId() > 0);

		// obtenemos la lista despues de haber insertado un nuevo perro
		final ArrayList<Perro> listaDespuesInsertar = (ArrayList<Perro>) dao.getAll("asc", "id");
		pMasUno = listaDespuesInsertar.size();
		// comprobamos que el tamaño de la lista se ha incrementado en 1
		assertEquals(pInicio + 1, pMasUno);

		// *****************************************************
		// ******* COMPROBAR EL METODO update() ****************
		// *****************************************************
		// cambiamos los parámetro de 'nombre' y 'raza' al perro creado
		perro.setNombre("Lur");
		perro.setRaza("Doberman");
		// comprobamos que lo actualiza correctamente
		assertTrue("Fallo al actualizar", dao.update(perro));

		// *****************************************************
		// ******* COMPROBAR EL METODO getById(id) ****************
		// *****************************************************
		final long idPerro = perro.getId();
		final Perro perroObtenido = dao.getById(idPerro);
		assertNotNull("Fallo al obtener el perro con id=" + idPerro, dao.getById(idPerro));
		assertEquals("Lur", perroObtenido.getNombre());
		assertEquals("Doberman", perroObtenido.getRaza());

		// ******************************************************
		// ******* COMPROBAR EL METODO delete(id) ****************
		// *****************************************************
		// comprobamos que borra el perro con 'id' correctamente
		assertTrue("Fallo al borrar el perro con id=" + perro.getId(), dao.delete(idPerro));
		// volvemos a obtener la lista tras borrar un elemento
		final ArrayList<Perro> listaDespuesBorrar = (ArrayList<Perro>) dao.getAll("asc", "id");
		// comprobamos que la lista no es nula y que se ha decrementado en uno
		pMenosUno = listaDespuesBorrar.size();
		assertNotNull("No puede ser Null", pMenosUno);
		assertEquals(pMasUno - 1, pMenosUno);

		assertFalse("No se puede eliminar algo que no existe", dao.delete(0));
		assertNull("No se puede recuperar algo que no existe", dao.getById(0));

	}

}

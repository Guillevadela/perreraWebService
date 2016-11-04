package com.ipartek.formacion.perrera.util;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

/**
 * Test para comprobar funcionamiento de Hibernate
 * 
 * @author JHM
 *
 */
public class HibernateUtilTest {

	/**
	 * Implementación para comprobar la obtención de la sesión
	 */
	@Test()
	public void testGetSession() {

		assertNotNull("Configuracion BBDD incorrecta", HibernateUtil.getSession());
	}

}

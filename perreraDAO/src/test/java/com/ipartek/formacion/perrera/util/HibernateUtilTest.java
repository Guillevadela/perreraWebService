package com.ipartek.formacion.perrera.util;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

/**
 * 
 * @author Adassoy
 *
 */
public class HibernateUtilTest {

	@Test()
	/**
	 * Test de Session
	 */
	public void testGetSession() {

		assertNotNull("Configuracion BBDD incorrecta", HibernateUtil.getSession());
	}

}

package com.ipartek.formacion.perrera.util;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

/**
 * test del Hibernate
 * 
 * @author EkaitzAF
 *
 */
public class HibernateUtilTest {

	@Test()
	/**
	 * implementacion del test del Hibernate
	 * 
	 */
	public void testGetSession() {

		assertNotNull("Configuracion BBDD incorrecta", HibernateUtil.getSession());
	}

}

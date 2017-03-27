package com.ipartek.formacion.perrera.listener;

import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Application Lifecycle Listener implementation class InitListener
 *
 */
@WebListener
public class InitListener implements ServletContextListener {
	
	private final static Logger LOG = Logger.getLogger(InitListener.class);

	private final static String PATH_LOG4J      = "/log4j.properties";
  
	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce)  { 
    	LOG.info("Destruido Çontexto Servlets");
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce)  { 
    	
    	try {
        	Properties props = new Properties();
        	props.load(getClass().getResourceAsStream(PATH_LOG4J));
            PropertyConfigurator.configure(props);
            LOG.info("LOG CARGADO");

          } catch (Exception e) {
        	  e.printStackTrace();
          }
    	
    }
	
}

























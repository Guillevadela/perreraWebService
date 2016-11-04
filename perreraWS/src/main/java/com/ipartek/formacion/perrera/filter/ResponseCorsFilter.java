package com.ipartek.formacion.perrera.filter;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;

/**
 * 
 * @author Adassoy
 *
 */
public class ResponseCorsFilter implements ContainerResponseFilter {

	@Override()
	/**
	 * Filtro ResponseCors
	 */
	public ContainerResponse filter(ContainerRequest req, ContainerResponse contResp) {

		final ResponseBuilder resp = Response.fromResponse(contResp.getResponse());
		resp.header("Access-Control-Allow-Origin", "*");
		resp.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
		contResp.setResponse(resp.build());
		return contResp;
	}

}
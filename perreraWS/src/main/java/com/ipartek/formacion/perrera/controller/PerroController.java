package com.ipartek.formacion.perrera.controller;

import java.util.ArrayList;

import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ipartek.formacion.perrera.pojo.Perro;
import com.ipartek.formacion.perrera.service.PerroServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Clase controlador de perreraWebService
 * 
 * @author EkaitzAF
 *
 */
@Path("/perro")
@Api(value = "/perro")
public class PerroController {
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
	 * variable para fallo 209 en response
	 */
	private static final int FAILURE09 = 209;
	/**
	 * variable para error 500 en response
	 */
	private static final int ERROR00 = 500;
	/**
	 * variable para mensaje error en el servidor
	 */
	private static final String MENSAJE_ERROR = "Error inexperado en el servidor";
	/**
	 * log para las trazas
	 */
	protected final Logger log = LoggerFactory.getLogger(getClass());

	@GET()
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Listado de Perros", notes = "Listado de perros existentes en la perrera, limitado a 1.000", response = Perro.class, responseContainer = "List")

	@ApiResponses(value = { @ApiResponse(code = SUCCESS00, message = "Todo OK"),
			@ApiResponse(code = ERROR00, message = MENSAJE_ERROR) })
	/**
	 * Función que devuelve una lista de perros
	 * 
	 * @param orderBy
	 *            orden de la lista, ascendente "asc" o descendente "desc"
	 * @param campo
	 *            campo por el que se va a ordenar la lista
	 * @return response de exito con lista o error
	 */
	public Response getAll(
			@ApiParam(name = "orderBy", required = false, value = "Filtro para ordenar los perros de forma ascendente o descendente, posibles valores [asc|desc]") @DefaultValue("asc") @QueryParam("orderBy") final String orderBy,
			@ApiParam(name = "campo", required = false, value = "Filtro para ordenar por 'campo' los perros, posibles valores [id|nombre|raza]") @DefaultValue("id") @QueryParam("campo") final String campo) {
		Response response = null;
		try {
			this.log.trace("Llamada a servicio: Obteniendo lista de perros");
			final PerroServiceImpl service = PerroServiceImpl.getInstance();
			final ArrayList<Perro> perros = (ArrayList<Perro>) service.getAll(orderBy, campo);
			this.log.info("Controlador: Devolviendo lista de perros");
			response = Response.ok().entity(perros).build();

		} catch (Exception e) {
			this.log.error("Error al intentar conectarse al servidor en getAll");
			response = Response.serverError().build();
		}
		return response;
	}

	@GET()
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Busca un perro por su ID", notes = "devuelve un perro mediante el paso de su ID", response = Perro.class, responseContainer = "Perro")
	@ApiResponses(value = { @ApiResponse(code = SUCCESS00, message = "Todo OK"),
			@ApiResponse(code = FAILURE04, message = "No existe perro con esa ID"),
			@ApiResponse(code = ERROR00, message = MENSAJE_ERROR) })
	/**
	 * Función que devuelve una perro mediante busqueda por id
	 * 
	 * @param idPerro
	 *            id del perro a buscar
	 * @return response de exito con perro o error
	 */
	public Response getById(@PathParam("id") final int idPerro) {
		Response response = null;
		try {
			this.log.trace("Llamada a servicio: Obteniendo perro con su id");
			final PerroServiceImpl service = PerroServiceImpl.getInstance();
			final Perro perro = (Perro) service.getById(idPerro);
			if (0 == perro.getId()) {
				this.log.info("No se ha encontrado perros con id" + idPerro);
				response = Response.noContent().build();
			} else {
				this.log.info("Devolviendo perro con id" + idPerro);
				response = Response.ok().entity(perro).build();
			}

		} catch (Exception e) {
			this.log.error("Error al intentar conectarse al servidor en getById");
			response = Response.serverError().build();
		}
		return response;
	}

	@DELETE()
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Elimina un perro", notes = "Elimina un perro mediante el paso de su ID", response = Perro.class, responseContainer = "FechaHora")
	@ApiResponses(value = { @ApiResponse(code = SUCCESS00, message = "Perro eliminado"),
			@ApiResponse(code = FAILURE04, message = "No existe Perro con ese ID"),
			@ApiResponse(code = ERROR00, message = MENSAJE_ERROR) })
	/**
	 * Función que elimina un perro mediante busqueda por id
	 * 
	 * @param idPerro
	 *            id del perro a eliminar
	 * @return response de exito con true si exito o error
	 */
	public Response delete(@PathParam("id") final int idPerro) {
		Response response = null;
		try {
			this.log.trace("Llamada a servicio: Eliminando perro con id:" + idPerro);
			final PerroServiceImpl service = PerroServiceImpl.getInstance();
			final boolean perroBorrado = service.delete(idPerro);
			if (perroBorrado) {
				this.log.info("Se ha eliminado el perro con id:" + idPerro);
				response = Response.ok().entity(perroBorrado).build();
			} else {
				this.log.info("No se ha podido eliminar el perro con id:" + idPerro);
				response = Response.noContent().build();
			}

		} catch (Exception e) {
			this.log.error("Error al intentar conectarse al servidor en delete");
			response = Response.serverError().build();
		}
		return response;
	}

	@POST()
	@Path("/{nombre}/{raza}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Añade un perro", notes = "Crea y persiste un nuevo perro", response = Perro.class, responseContainer = "Perro")
	@ApiResponses(value = { @ApiResponse(code = SUCCESS01, message = "Perro Creado con exito"),
			@ApiResponse(code = FAILURE09, message = "Perro ya Existente"),
			@ApiResponse(code = ERROR00, message = MENSAJE_ERROR) })
	/**
	 * Función que inserta un perro
	 * 
	 * @param nombrePerro
	 *            nombre de perro a insertar
	 * @param razaPerro
	 *            raza de perro a insertar
	 * @return response de exito o error
	 */
	public Response post(@PathParam("nombre") final String nombrePerro, @PathParam("raza") final String razaPerro) {
		Response response = null;
		try {
			this.log.trace(
					"Llamada a servicio: Insertando perro nuevo con nombre:" + nombrePerro + " y raza:" + razaPerro);
			final PerroServiceImpl service = PerroServiceImpl.getInstance();
			final Perro perroNuevo = new Perro(nombrePerro, razaPerro);
			final boolean perroCreado = service.insert(perroNuevo);
			if (perroCreado) {
				this.log.info(
						"Se ha insertado en la tabla el perro con nombre:" + nombrePerro + " y raza:" + razaPerro);
				response = Response.status(SUCCESS01).build();
			} else {
				this.log.info("No se ha podido insertar en la tabla el perro con nombre:" + nombrePerro + " y raza:"
						+ razaPerro);
				response = Response.status(FAILURE09).build();
			}

		} catch (Exception e) {
			this.log.error("Error al intentar conectarse al servidor en post");
			// e.printStackTrace();
			response = Response.serverError().build();
		}
		return response;
	}

	@PUT()
	@Path("/{id}/{nombre}/{raza}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Modifica un perro", notes = "Modifica un perro ya existente mediante su identificador", response = Perro.class, responseContainer = "Perro")
	@ApiResponses(value = { @ApiResponse(code = SUCCESS00, message = "Todo OK"),
			@ApiResponse(code = FAILURE04, message = "No existe perro con ese ID"),
			@ApiResponse(code = FAILURE09, message = "Perro existente, no se puede modificar"),
			@ApiResponse(code = ERROR00, message = MENSAJE_ERROR) })
	/**
	 * Función que actualiza un perro
	 * 
	 * @param idPerro
	 *            id del perro a modificar
	 * @param nombrePerro
	 *            nuevo nombre del perro a modificar
	 * @param razaPerro
	 *            nueva raza del perro a modificar
	 * @return response de exito o error
	 */
	public Response put(@PathParam("id") final int idPerro, @PathParam("nombre") final String nombrePerro,
			@PathParam("raza") final String razaPerro) {
		Response response = null;
		try {
			this.log.trace("Llamada a servicio: Modificando perro con id:" + idPerro);
			final PerroServiceImpl service = PerroServiceImpl.getInstance();
			final Perro perro = (Perro) service.getById(idPerro);
			if (0 < perro.getId()) {
				perro.setNombre(nombrePerro);
				perro.setRaza(razaPerro);
				service.update(perro);
				this.log.info("Se ha modificado el perro con id:" + idPerro + " nuevo nombre:" + nombrePerro
						+ " y nueva raza:" + razaPerro);
				response = Response.status(SUCCESS00).build();
			} else {
				this.log.info("No se ha podido modificar el perro con id:" + idPerro + " nombre:" + nombrePerro
						+ " y raza:" + razaPerro);
				response = Response.status(FAILURE04).build();
			}

		} catch (Exception e) {
			this.log.error("Error al intentar conectarse al servidor en put");
			response = Response.status(ERROR00).build();

		}
		return response;
	}

}
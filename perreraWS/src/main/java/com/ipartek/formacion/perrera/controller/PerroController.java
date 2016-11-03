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

	private final int SUCCESS00 = 200;
	private final int SUCCESS01 = 201;
	private final int FAILURE04 = 204;
	private final int FAILURE09 = 209;
	private final int ERROR00 = 50;
	protected final Logger log = LoggerFactory.getLogger(getClass());

	@GET()
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Listado de Perros", notes = "Listado de perros existentes en la perrera, limitado a 1.000", response = Perro.class, responseContainer = "List")

	@ApiResponses(value = { @ApiResponse(code = SUCCESS00, message = "Todo OK"),
			@ApiResponse(code = ERROR00, message = "Error inexperado en el servidor") })
	public Response getAll(
			@ApiParam(name = "orderBy", required = false, value = "Filtro para ordenar los perros de forma ascendente o descendente, posibles valores [asc|desc]") @DefaultValue("asc") @QueryParam("orderBy") String orderBy,
			@ApiParam(name = "campo", required = false, value = "Filtro para ordenar por 'campo' los perros, posibles valores [id|nombre|raza]") @DefaultValue("id") @QueryParam("campo") String campo) {
		try {
			this.log.trace("Llamada a servicio: Obteniendo lista de perros");
			final PerroServiceImpl service = PerroServiceImpl.getInstance();
			final ArrayList<Perro> perros = (ArrayList<Perro>) service.getAll(orderBy, campo);
			this.log.info("Controlador: Devolviendo lista de perros");
			return Response.ok().entity(perros).build();

		} catch (Exception e) {
			this.log.error("Error al intentar conectarse al servidor en getAll");
			return Response.serverError().build();
		}
	}

	@GET()
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Busca un perro por su ID", notes = "devuelve un perro mediante el paso de su ID", response = Perro.class, responseContainer = "Perro")
	@ApiResponses(value = { @ApiResponse(code = SUCCESS00, message = "Todo OK"),
			@ApiResponse(code = FAILURE04, message = "No existe perro con esa ID"),
			@ApiResponse(code = ERROR00, message = "Error inexperado en el servidor") })
	public Response getById(@PathParam("id") int idPerro) {

		try {
			this.log.trace("Llamada a servicio: Obteniendo perro con su id");
			PerroServiceImpl service = PerroServiceImpl.getInstance();
			Perro perro = (Perro) service.getById(idPerro);
			if (0 == perro.getId()) {
				this.log.info("No se ha encontrado perros con id" + idPerro);
				return Response.noContent().build();
			} else {
				this.log.info("Devolviendo perro con id" + idPerro);
				return Response.ok().entity(perro).build();
			}

		} catch (Exception e) {
			this.log.error("Error al intentar conectarse al servidor en getById");
			return Response.serverError().build();
		}
	}

	@DELETE()
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Elimina un perro", notes = "Elimina un perro mediante el paso de su ID", response = Perro.class, responseContainer = "FechaHora")
	@ApiResponses(value = { @ApiResponse(code = SUCCESS00, message = "Perro eliminado"),
			@ApiResponse(code = FAILURE04, message = "No existe Perro con ese ID"),
			@ApiResponse(code = ERROR00, message = "Error inexperado en el servidor") })
	public Response delete(@PathParam("id") int idPerro) {

		try {
			this.log.trace("Llamada a servicio: Eliminando perro con id:" + idPerro);
			PerroServiceImpl service = PerroServiceImpl.getInstance();
			boolean perroBorrado = service.delete(idPerro);
			if (perroBorrado) {
				this.log.info("Se ha eliminado el perro con id:" + idPerro);
				return Response.ok().entity(perroBorrado).build();
			} else {
				this.log.info("No se ha podido eliminar el perro con id:" + idPerro);
				return Response.noContent().build();
			}

		} catch (Exception e) {
			this.log.error("Error al intentar conectarse al servidor en delete");
			return Response.serverError().build();
		}
	}

	@POST()
	@Path("/{nombre}/{raza}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Añade un perro", notes = "Crea y persiste un nuevo perro", response = Perro.class, responseContainer = "Perro")
	@ApiResponses(value = { @ApiResponse(code = SUCCESS01, message = "Perro Creado con exito"),
			@ApiResponse(code = FAILURE09, message = "Perro ya Existente"),
			@ApiResponse(code = ERROR00, message = "Error inexperado en el servidor") })
	public Response post(@PathParam("nombre") String nombrePerro, @PathParam("raza") String razaPerro) {
		try {
			this.log.trace(
					"Llamada a servicio: Insertando perro nuevo con nombre:" + nombrePerro + " y raza:" + razaPerro);
			PerroServiceImpl service = PerroServiceImpl.getInstance();
			Perro perroNuevo = new Perro(nombrePerro, razaPerro);
			boolean perroCreado = service.insert(perroNuevo);
			if (perroCreado) {
				this.log.info(
						"Se ha insertado en la tabla el perro con nombre:" + nombrePerro + " y raza:" + razaPerro);
				return Response.status(SUCCESS01).build();
			} else {
				this.log.info("No se ha podido insertar en la tabla el perro con nombre:" + nombrePerro + " y raza:"
						+ razaPerro);
				return Response.status(FAILURE09).build();
			}

		} catch (Exception e) {
			this.log.error("Error al intentar conectarse al servidor en post");
			e.printStackTrace();
			return Response.serverError().build();
		}
	}

	@PUT()
	@Path("/{id}/{nombre}/{raza}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Modifica un perro", notes = "Modifica un perro ya existente mediante su identificador", response = Perro.class, responseContainer = "Perro")
	@ApiResponses(value = { @ApiResponse(code = SUCCESS00, message = "Todo OK"),
			@ApiResponse(code = FAILURE04, message = "No existe perro con ese ID"),
			@ApiResponse(code = FAILURE09, message = "Perro existente, no se puede modificar"),
			@ApiResponse(code = ERROR00, message = "Error inexperado en el servidor") })
	public Response put(@PathParam("id") int idPerro, @PathParam("nombre") String nombrePerro,
			@PathParam("raza") String razaPerro) {
		try {
			this.log.trace("Llamada a servicio: Modificando perro con id:" + idPerro);
			PerroServiceImpl service = PerroServiceImpl.getInstance();
			Perro perro = (Perro) service.getById(idPerro);
			if (0 < perro.getId()) {
				perro.setNombre(nombrePerro);
				perro.setRaza(razaPerro);
				service.update(perro);
				this.log.info("Se ha modificado el perro con id:" + idPerro + " nuevo nombre:" + nombrePerro
						+ " y nueva raza:" + razaPerro);
				return Response.status(SUCCESS00).build();
			} else {
				this.log.info("No se ha podido modificar el perro con id:" + idPerro + " nombre:" + nombrePerro
						+ " y raza:" + razaPerro);
				return Response.status(FAILURE04).build();
			}

		} catch (Exception e) {
			this.log.error("Error al intentar conectarse al servidor en put");
			return Response.status(ERROR00).build();

		}
	}

}
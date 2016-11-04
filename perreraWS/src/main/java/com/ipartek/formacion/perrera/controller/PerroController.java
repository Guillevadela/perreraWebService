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

import com.ipartek.formacion.perrera.dao.PerroDAOImpl;
import com.ipartek.formacion.perrera.pojo.FechaHora;
import com.ipartek.formacion.perrera.pojo.Perro;
import com.ipartek.formacion.perrera.service.PerreraServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/perro")
@Api(value = "/perro")
/**
 * Controlador para operaciones CRUD de perrera
 * 
 * @author ADassoy
 * 
 *
 */
public class PerroController {
	/**
	 * Logger log
	 */
	protected final Logger log = LoggerFactory.getLogger(getClass());
	private static final int OK = 200;
	private static final int CREATED = 201;
	private static final int NO_CONTENT = 204;
	private static final int CONFLICT = 409;
	private static final int INTERNAL_SERVER_ERROR = 500;

	@GET()
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Listado de Perros", notes = "Listado de perros existentes en la perrera, limitado a 1.000", response = Perro.class, responseContainer = "List")

	@ApiResponses(value = { @ApiResponse(code = OK, message = "Todo OK"),
			@ApiResponse(code = INTERNAL_SERVER_ERROR, message = "Error inexperado en el servidor") })
	/**
	 * 
	 * @param orderBy
	 *            [asc,desc]
	 * @param campo
	 *            [id|nombre|raza]
	 * @return Response
	 */
	public Response getAll(
			@ApiParam(name = "orderBy", required = false, value = "Filtro para ordenar los perros de forma ascendente o descendente, posibles valores [asc|desc]") @DefaultValue("asc") @QueryParam("orderBy") final String orderBy,
			@ApiParam(name = "campo", required = false, value = "Filtro para ordenar por 'campo' los perros, posibles valores [id|nombre|raza]") @DefaultValue("id") @QueryParam("campo") final String campo) {
		Response response = null;
		try {
			this.log.info("listando todos los perros");

			final PerreraServiceImpl service = PerreraServiceImpl.getInstance();
			final ArrayList<Perro> perros = (ArrayList<Perro>) service.getAll(orderBy, campo);
			response = Response.ok().entity(perros).build();

		} catch (Exception e) {
			this.log.error("Error listando todos los perros");
			response = Response.serverError().build();
		}
		return response;
	}

	@GET()
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Busca un perro por su ID", notes = "devuelve un perro mediante el paso de su ID", response = Perro.class, responseContainer = "Perro")
	@ApiResponses(value = { @ApiResponse(code = OK, message = "Todo OK"),
			@ApiResponse(code = NO_CONTENT, message = "No existe perro con esa ID"),
			@ApiResponse(code = INTERNAL_SERVER_ERROR, message = "Error inexperado en el servidor") })
	/**
	 * 
	 * @param idPerro
	 *            Variable de tipo long
	 * @return Response
	 */
	public Response getById(@PathParam("id") final long idPerro) {
		this.log.info("Peticion Obtener perro por 'id'" + idPerro);
		Response response = null;
		try {
			final PerroDAOImpl dao = PerroDAOImpl.getInstance();
			Perro perro = null;
			perro = dao.getById(idPerro);

			if (perro == null) {
				this.log.info("El perro con id " + idPerro + " no existe en la bd");
				response = Response.noContent().build();

			}
			this.log.info("Mostrando perro con id " + idPerro);
			response = Response.ok().entity(perro).build();
		} catch (Exception e) {

			response = Response.serverError().build();
		}
		return response;
	}

	@DELETE()
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Elimina un perro", notes = "Elimina un perro mediante el paso de su ID", response = Perro.class, responseContainer = "FechaHora")
	@ApiResponses(value = { @ApiResponse(code = OK, message = "Perro eliminado"),
			@ApiResponse(code = NO_CONTENT, message = "No existe Perro con ese ID"),
			@ApiResponse(code = INTERNAL_SERVER_ERROR, message = "Error inexperado en el servidor") })
	/**
	 * 
	 * @param idPerro
	 *            variable de tipo long
	 * @return Response
	 */
	public Response delete(@PathParam("id") final long idPerro) {
		Response response = null;
		try {
			final PerroDAOImpl dao = PerroDAOImpl.getInstance();
			final boolean pElimnar = dao.delete(idPerro);
			this.log.info("Eliminando perro con id " + idPerro);
			if (!pElimnar) {
				response = Response.noContent().build();
			} else {
				this.log.info("Perro con id " + idPerro + " eliminado");
				response = Response.ok().entity(new FechaHora()).build();
			}
		} catch (Exception e) {
			this.log.error("Imposible conectar con la bd");
			response = Response.serverError().build();
		}
		return response;
	}

	@POST()
	@Path("/{nombre}/{raza}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Añade un perro", notes = "Crea y persiste un nuevo perro", response = Perro.class, responseContainer = "Perro")
	@ApiResponses(value = { @ApiResponse(code = CREATED, message = "Perro Creado con exito"),
			@ApiResponse(code = CONFLICT, message = "Perro ya Existente"),
			@ApiResponse(code = INTERNAL_SERVER_ERROR, message = "Error inexperado en el servidor") })
	/**
	 * 
	 * @param nombrePerro
	 *            String
	 * @param razaPerro
	 *            String
	 * @return Response
	 */
	public Response post(@PathParam("nombre") final String nombrePerro, @PathParam("raza") final String razaPerro) {
		Response response = null;
		try {
			final PerroDAOImpl dao = PerroDAOImpl.getInstance();
			final Perro pCreado = new Perro(nombrePerro, razaPerro);
			final boolean creado = dao.insert(pCreado);

			if (creado) {
				this.log.info("Perro " + pCreado + " creado");
				response = Response.status(CREATED).entity(pCreado).build();
			} else {
				response = Response.status(CONFLICT).build();
			}
		} catch (Exception e) {
			// e.printStackTrace();
			this.log.error("Imposible conectar con la bd");
			response = Response.serverError().build();
		}
		return response;
	}

	@PUT()
	@Path("/{id}/{nombre}/{raza}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Modifica un perro", notes = "Modifica un perro ya existente mediante su identificador", response = Perro.class, responseContainer = "Perro")
	@ApiResponses(value = { @ApiResponse(code = OK, message = "Todo OK"),
			@ApiResponse(code = NO_CONTENT, message = "No existe perro con ese ID"),
			@ApiResponse(code = CONFLICT, message = "Perro existente, no se puede modificar"),
			@ApiResponse(code = INTERNAL_SERVER_ERROR, message = "Error inexperado en el servidor") })
	/**
	 * 
	 * @param idPerro
	 *            tipo long
	 * @param nombrePerro
	 *            String
	 * @param razaPerro
	 *            String
	 * @return Response
	 */
	public Response put(@PathParam("id") final long idPerro, @PathParam("nombre") final String nombrePerro,
			@PathParam("raza") final String razaPerro) {
		Response response = null;
		try {
			final PerroDAOImpl dao = PerroDAOImpl.getInstance();
			Perro pModificar = null;
			pModificar = dao.getById(idPerro);

			if (pModificar == null) {
				response = Response.noContent().build();
			} else {
				pModificar.setNombre(nombrePerro);
				pModificar.setRaza(razaPerro);
				dao.update(pModificar);
				response = Response.ok().entity(pModificar).build();
			}
		} catch (Exception e) {
			this.log.error("Imposible conectar con la bd");
			response = Response.status(INTERNAL_SERVER_ERROR).build();
		}
		return response;
	}

}
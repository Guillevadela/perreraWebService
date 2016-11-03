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

import org.apache.log4j.Logger;

import com.ipartek.formacion.perrera.dao.PerroDAOImpl;
import com.ipartek.formacion.perrera.pojo.Perro;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Perro Controller
 *
 * @author Jon fraile
 *
 */
@Path("/perro")
@Api(value = "/perro")
public class PerroController {

	private static final Logger logger = Logger.getLogger(PerroController.class);

	/**
	 * Devuelve lista de perros limitado a 1000 y codigo 200 si ha ido todo
	 * correcto <br>
	 * codigo 500 si ha existido un eror en el servidor
	 * 
	 * @param orderBy
	 *            asc or desc
	 * @param campo
	 *            id or nombre or raza
	 * @return <b>perros</b> ArrayList &lt;Perro&gt;
	 * @throws Exception
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Listado de Perros", notes = "Listado de perros existentes en la perrera, limitado a 1.000", response = Perro.class, responseContainer = "List")

	@ApiResponses(value = { @ApiResponse(code = 200, message = "Todo OK"),
			@ApiResponse(code = 500, message = "Error inexperado en el servidor") })
	public Response getAll(
			@ApiParam(name = "orderBy", required = false, value = "Filtro para ordenar los perros de forma ascendente o descendente, posibles valores [asc|desc]") @DefaultValue("asc") @QueryParam("orderBy") String orderBy,
			@ApiParam(name = "campo", required = false, value = "Filtro para ordenar por 'campo' los perros, posibles valores [id|nombre|raza]") @DefaultValue("id") @QueryParam("campo") String campo) {

		try {

			final PerroDAOImpl dao = PerroDAOImpl.getInstance();
			final ArrayList<Perro> perros = (ArrayList<Perro>) dao.getAll(orderBy, campo);
			return Response.ok().entity(perros).build();

		} catch (final Exception e) {
			logger.warn("Error al listar perros", e);
			return Response.serverError().build();
		}
	}

	/**
	 * Devuelve perro y codigo 200 si ha ido todo correcto <br>
	 * codigo 204 si el parametro id no es el correcto <br>
	 * codigo 500 si ha existido un eror en el servidor
	 * 
	 * @param idCreado
	 *            &lt;long&gt;
	 * @return <b>perro</b> &lt;Perro&gt;
	 * @throws Exception
	 */
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Busca un perro por su ID", notes = "devuelve un perro mediante el paso de su ID", response = Perro.class, responseContainer = "Perro")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Todo OK"),
			@ApiResponse(code = 204, message = "No existe perro con esa ID"),
			@ApiResponse(code = 500, message = "Error inexperado en el servidor") })
	public Response getById(@PathParam("id") long idCreado) {

		try {
			final PerroDAOImpl dao = PerroDAOImpl.getInstance();
			final Perro perro = dao.getById(idCreado);
			if (perro == null) {
				logger.warn("Id especificada erronea");
				return Response.noContent().build();
			}
			return Response.ok().entity(perro).build();
		} catch (final Exception e) {
			logger.warn("Id especificada erronea", e);
			return Response.serverError().build();
		}
	}

	/**
	 * Crea un perro, devuelve true y codigo 201 si ha ido todo correcto <br>
	 * codigo 409 si el perro ya existe <br>
	 * codigo 500 si ha existido un eror en el servidor
	 * 
	 * @param nombrePerro
	 *            &lt;String&gt;
	 * @param razaPerro
	 *            &lt;String&gt;
	 * @return <b>resul</b> &lt;boolean&gt;
	 * @throws Exception
	 */
	@POST
	@Path("/{nombre}/{raza}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Añade un perro", notes = "Crea y persiste un nuevo perro", response = Perro.class, responseContainer = "Perro")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Perro Creado con exito"),
			@ApiResponse(code = 409, message = "Perro ya Existente"),
			@ApiResponse(code = 500, message = "Error inexperado en el servidor") })
	public Response post(@PathParam("nombre") String nombrePerro, @PathParam("raza") String razaPerro) {
		try {

			final Perro pCreado = new Perro(nombrePerro, razaPerro);
			final PerroDAOImpl dao = PerroDAOImpl.getInstance();
			final boolean resul = dao.insert(pCreado);

			if (resul != false) {
				return Response.status(201).entity(pCreado).build();
			} else {
				return Response.status(409).build();
			}
		} catch (final Exception e) {
			logger.warn("Error al crear un perro", e);
			return Response.serverError().build();
		}
	}

	/**
	 * Modifica un perro ya existente y codigo 200 si ha ido todo correcto <br>
	 * codigo 204 si no existe perro con ese id<br>
	 * codigo 409 si el perro ya existe, no se puede modificar <br>
	 * codigo 500 si ha existido un eror en el servidor
	 * 
	 * @param idPerro
	 * @param nombrePerro
	 * @param razaPerro
	 * @return <b>resul</b> &lt;boolean&gt;
	 * @throws Exception
	 */
	@PUT
	@Path("/{id}/{nombre}/{raza}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Modifica un perro", notes = "Modifica un perro ya existente mediante su identificador", response = Perro.class, responseContainer = "Perro")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Todo OK"),
			@ApiResponse(code = 204, message = "No existe perro con ese ID"),
			@ApiResponse(code = 409, message = "Perro existente, no se puede modificar"),
			@ApiResponse(code = 500, message = "Error inexperado en el servidor") })
	public Response put(@PathParam("id") long idPerro, @PathParam("nombre") String nombrePerro,
			@PathParam("raza") String razaPerro) {
		try {
			final Perro pModificar = new Perro();
			pModificar.setId(idPerro);
			pModificar.setNombre(nombrePerro);
			pModificar.setRaza(razaPerro);
			final PerroDAOImpl dao = PerroDAOImpl.getInstance();
			final boolean resul = dao.update(pModificar);

			if (resul == false) {
				return Response.noContent().build();
			} else {
				return Response.ok().entity(pModificar).build();
			}
		} catch (final Exception e) {
			logger.warn("Error al modificar un perro", e);
			return Response.status(500).build();

		}
	}

	/**
	 * Elimina un perro y codigo 200 si ha ido todo correcto <br>
	 * codigo 204 si no existe perro con ese id<br>
	 * codigo 500 si ha existido un eror en el servidor
	 * 
	 * @param idCreado
	 * @return <b>resul</b> &lt;boolean&gt;
	 * @throws Exception
	 */
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Elimina un perro", notes = "Elimina un perro mediante el paso de su ID", response = Perro.class, responseContainer = "FechaHora")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Perro eliminado"),
			@ApiResponse(code = 204, message = "No existe Perro con ese ID"),
			@ApiResponse(code = 500, message = "Error inexperado en el servidor") })
	public Response delete(@PathParam("id") long idCreado) {

		try {
			final PerroDAOImpl dao = PerroDAOImpl.getInstance();
			final Perro pEliminado = dao.getById(idCreado);
			final boolean resul = dao.delete(idCreado);
			if (resul == false) {
				return Response.noContent().build();
			} else {
				return Response.ok().entity(pEliminado).build();
			}
		} catch (final Exception e) {
			logger.warn("Error al eliminar un perro", e);
			return Response.serverError().build();
		}
	}

}
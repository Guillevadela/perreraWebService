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

import com.ipartek.formacion.perrera.pojo.FechaHora;
import com.ipartek.formacion.perrera.pojo.Perro;
import com.ipartek.formacion.perrera.service.PerreraServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Clase Controller que gestiona
 * 
 * @author JHM
 *
 */
@Path("/perro")
@Api(value = "/perro")
public class PerroController {

	private static final Logger lOGGER = Logger.getLogger(PerroController.class);

	private static final int CODE200 = 200;
	private static final int CODE201 = 201;
	private static final int CODE204 = 204;
	private static final int CODE409 = 409;
	private static final int CODE500 = 500;

	/**
	 * Método GET que llama al servicio para obtener listado de Perros
	 * 
	 * @param orderBy
	 *            Filtro para ordenar los perros de forma ascendente o
	 *            descendente, posibles valores [asc|desc]. Valor por defecto
	 *            "asc"
	 * @param campo
	 *            Filtro para ordenar por 'campo' los perros, posibles valores
	 *            [id|nombre|raza]. Valor por defecto "id"
	 * @return Response Devuelve un listado de perros con los códigos:<br>
	 *         200 - Listado devuelto con exito; <br>
	 *         500 - Error inexperado en el servidor
	 */
	@GET()
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Listado de Perros", notes = "Listado de perros existentes en la perrera, limitado a 1.000", response = Perro.class, responseContainer = "List")
	@ApiResponses(value = { @ApiResponse(code = CODE200, message = "Todo OK"),
			@ApiResponse(code = CODE500, message = "Error inexperado en el servidor") })
	public final Response getAll(
			@ApiParam(name = "orderBy", required = false, value = "Filtro para ordenar los perros de forma ascendente o descendente, posibles valores [asc|desc]") @DefaultValue("asc") @QueryParam("orderBy") String orderBy,
			@ApiParam(name = "campo", required = false, value = "Filtro para ordenar por 'campo' los perros, posibles valores [id|nombre|raza]") @DefaultValue("id") @QueryParam("campo") String campo) {
		try {

			PerreraServiceImpl dao = PerreraServiceImpl.getInstance();
			ArrayList<Perro> perros = (ArrayList<Perro>) dao.getAll(orderBy, campo);
			return Response.ok().entity(perros).build();

		} catch (Exception e) {
			lOGGER.warn("Error al listar perros", e);
			return Response.serverError().build();
		}
	}

	/**
	 * Método GET que llama al servicio para buscar un Perro por su 'id'
	 * 
	 * @param idPerro
	 *            ID del perro a buscar
	 * @return Response Devuelve un Perro o un error en caso de no encontrarlo.
	 *         <br>
	 *         200 - Perro devuelto con exito; <br>
	 *         204 - No existe Perro con ese ID; <br>
	 *         500 - Error inexperado en el servidor
	 * 
	 */
	@GET()
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Busca un perro por su ID", notes = "devuelve un perro mediante el paso de su ID", response = Perro.class, responseContainer = "Perro")
	@ApiResponses(value = { @ApiResponse(code = CODE200, message = "Todo OK"),
			@ApiResponse(code = CODE204, message = "No existe perro con esa ID"),
			@ApiResponse(code = CODE500, message = "Error inexperado en el servidor") })
	public Response getById(@PathParam("id") long idPerro) {

		try {

			PerreraServiceImpl dao = PerreraServiceImpl.getInstance();
			Perro perro = (Perro) dao.getById(idPerro);

			if (perro == null) {
				lOGGER.warn("Perro con id=" + idPerro + "no encontrado");
				return Response.noContent().build();
			}
			lOGGER.info("Perro con id=" + idPerro + " devuelto OK");
			return Response.ok().entity(perro).build();
		} catch (Exception e) {
			lOGGER.warn("Id introducida erronea", e);
			return Response.serverError().build();
		}
	}

	/**
	 * Metodo DELETE que llama al servicio para eliminar un perro de la BBDD
	 * 
	 * @param idPerro
	 *            ID del perro a eliminar
	 * @return Response Devuelve la fecha y hora de eliminacion del perro o un
	 *         error en caso de no poder eliminarlo. <br>
	 *         200 - Perro eliminado con exito; <br>
	 *         204 - No existe Perro con ese ID; <br>
	 *         500 - Error inexperado en el servidor
	 */
	@DELETE()
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Elimina un perro", notes = "Elimina un perro mediante el paso de su ID", response = Perro.class, responseContainer = "FechaHora")
	@ApiResponses(value = { @ApiResponse(code = CODE200, message = "Perro eliminado"),
			@ApiResponse(code = CODE204, message = "No existe Perro con ese ID"),
			@ApiResponse(code = CODE500, message = "Error inexperado en el servidor") })
	public Response delete(@PathParam("id") long idPerro) {

		try {

			PerreraServiceImpl dao = PerreraServiceImpl.getInstance();
			Perro pElimnar = null;

			pElimnar = (Perro) dao.getById(idPerro);

			if (pElimnar == null) {
				lOGGER.warn("Error al eliminar perro. Perro no existe");
				return Response.noContent().build();
			} else {
				dao.delete(pElimnar.getId());
				lOGGER.info("Perro con id=" + pElimnar.getId() + " eliminado OK");
				return Response.ok().entity(new FechaHora()).build();
			}
		} catch (Exception e) {
			lOGGER.warn("Error al eliminar perro", e);
			return Response.serverError().build();
		}
	}

	/**
	 * Método POST que llama al servicio para crear un Perro nuevo en la BBDD
	 * 
	 * @param nombrePerro
	 *            Nombre del perro
	 * @param razaPerro
	 *            Raza del perro
	 * @return Response Devuelve los códigos: <br>
	 *         201 - Perro Creado con exito; <br>
	 *         409 - Perro ya Existente; <br>
	 *         500 - Error inexperado en el servidor
	 */
	@POST()
	@Path("/{nombre}/{raza}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Añade un perro", notes = "Crea y persiste un nuevo perro", response = Perro.class, responseContainer = "Perro")
	@ApiResponses(value = { @ApiResponse(code = CODE201, message = "Perro Creado con exito"),
			@ApiResponse(code = CODE409, message = "Perro ya Existente"),
			@ApiResponse(code = CODE500, message = "Error inexperado en el servidor") })
	public Response post(@PathParam("nombre") String nombrePerro, @PathParam("raza") String razaPerro) {
		try {

			PerreraServiceImpl dao = PerreraServiceImpl.getInstance();
			Perro pCreado = new Perro(nombrePerro, razaPerro);
			boolean creado = dao.insert(pCreado);

			if (creado) { // si creado==true
				lOGGER.info("Perro creado OK");
				return Response.status(CODE201).entity(pCreado).build();
			} else {
				lOGGER.warn("Error al crear perro. Perro ya existente");
				return Response.status(CODE409).build();
			}
		} catch (Exception e) {
			lOGGER.warn("Error al crear perro", e);
			e.printStackTrace();
			return Response.serverError().build();
		}
	}

	/**
	 * Método PUT que llama al servicio para modificar un perro ya existente
	 * mediante su identificador
	 * 
	 * @param idPerro
	 *            ID del perro a modificar
	 * @param nombrePerro
	 *            Nombre del perro modificado
	 * @param razaPerro
	 *            Raza del perro modificado
	 * @return Response Devuelve los códigos: <br>
	 *         200 - Perro modificado OK <br>
	 *         204 - No existe perro con ese ID; <br>
	 *         409 - Perro ya Existente; <br>
	 *         500 - Error inexperado en el servidor
	 */
	@PUT()
	@Path("/{id}/{nombre}/{raza}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Modifica un perro", notes = "Modifica un perro ya existente mediante su identificador", response = Perro.class, responseContainer = "Perro")
	@ApiResponses(value = { @ApiResponse(code = CODE200, message = "Todo OK"),
			@ApiResponse(code = CODE204, message = "No existe perro con ese ID"),
			@ApiResponse(code = CODE409, message = "Perro existente, no se puede modificar"),
			@ApiResponse(code = CODE500, message = "Error inexperado en el servidor") })
	public Response put(@PathParam("id") long idPerro, @PathParam("nombre") String nombrePerro,
			@PathParam("raza") String razaPerro) {
		try {

			PerreraServiceImpl dao = PerreraServiceImpl.getInstance();
			Perro pModificar = null;

			pModificar = (Perro) dao.getById(idPerro);

			if (pModificar == null) {
				lOGGER.warn("Error al modificar perro. Perro no existe");
				return Response.noContent().build();
			} else {
				pModificar.setNombre(nombrePerro);
				pModificar.setRaza(razaPerro);

				dao.update(pModificar);
				lOGGER.info("Perro con id=" + idPerro + " modificado OK");

				return Response.ok().entity(pModificar).build();
			}
		} catch (Exception e) {
			lOGGER.warn("Error inesperado al modificar perro con id=" + idPerro, e);
			return Response.status(CODE500).build();

		}
	}

}
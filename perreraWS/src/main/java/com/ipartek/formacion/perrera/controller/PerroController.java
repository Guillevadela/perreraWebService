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


@Path("/perro")
@Api(value = "/perro")
public class PerroController {

	private static final Logger logger = Logger.getLogger(PerroController.class);
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Listado de Perros", notes = "Listado de perros existentes en la perrera, limitado a 1.000", response = Perro.class, responseContainer = "List")

	@ApiResponses(value = { 
							@ApiResponse(code = 200, message = "Todo OK"),
			                @ApiResponse(code = 500, message = "Error inexperado en el servidor") 
							})
	public Response getAll(
			@ApiParam(name = "orderBy", required = false, value = "Filtro para ordenar los perros de forma ascendente o descendente, posibles valores [asc|desc]") @DefaultValue("asc") @QueryParam("orderBy") String orderBy,
			@ApiParam(name = "campo", required = false, value = "Filtro para ordenar por 'campo' los perros, posibles valores [id|nombre|raza]") @DefaultValue("id")  @QueryParam("campo") String campo) {
		try {
			
			PerreraServiceImpl dao = PerreraServiceImpl.getInstance();
			ArrayList<Perro> perros = (ArrayList<Perro>) dao.getAll(orderBy, campo);			
			return Response.ok().entity(perros).build();
			
		} catch (Exception e) {
			logger.warn("Error al listar perros",e);
			return Response.serverError().build();
		}
	}
	
	
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Busca un perro por su ID", notes = "devuelve un perro mediante el paso de su ID", response = Perro.class, responseContainer = "Perro")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Todo OK"),
			@ApiResponse(code = 204, message = "No existe perro con esa ID"),
			@ApiResponse(code = 500, message = "Error inexperado en el servidor") })
	public Response getById(@PathParam("id") long idPerro) {

		try {

			PerreraServiceImpl dao = PerreraServiceImpl.getInstance();
			Perro perro = (Perro) dao.getById(idPerro);

			if (perro == null) {
				logger.warn("Perro con id=" + idPerro + "no encontrado");
				return Response.noContent().build();
			}
			logger.info("Perro con id=" + idPerro + " devuelto OK");
			return Response.ok().entity(perro).build();
		} catch (Exception e) {
			logger.warn("Id introducida erronea",e);
			return Response.serverError().build();
		}
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Elimina un perro", notes = "Elimina un perro mediante el paso de su ID", response = Perro.class, responseContainer = "FechaHora")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Perro eliminado"),
			@ApiResponse(code = 204, message = "No existe Perro con ese ID"),
			@ApiResponse(code = 500, message = "Error inexperado en el servidor") })
	public Response delete(@PathParam("id") long idPerro) {

		try {
			
			PerreraServiceImpl dao = PerreraServiceImpl.getInstance();
			Perro pElimnar = null;
			
			pElimnar = (Perro) dao.getById(idPerro);
			
			if (pElimnar == null) {
				logger.warn("Error al eliminar perro. Perro no existe");
				return Response.noContent().build();
			} else {
				dao.delete(pElimnar.getId());
				logger.info("Perro con id=" + pElimnar.getId() + " eliminado OK");
				return Response.ok().entity(new FechaHora()).build();
			}
		} catch (Exception e) {
			logger.warn("Error al eliminar perro",e);
			return Response.serverError().build();
		}
	}
	
	
	@POST
	@Path("/{nombre}/{raza}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Añade un perro", notes = "Crea y persiste un nuevo perro", response = Perro.class, responseContainer = "Perro")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Perro Creado con exito"),
			@ApiResponse(code = 409, message = "Perro ya Existente"),
			@ApiResponse(code = 500, message = "Error inexperado en el servidor") })
	public Response post(@PathParam("nombre") String nombrePerro, @PathParam("raza") String razaPerro) {
		try {

			PerreraServiceImpl dao = PerreraServiceImpl.getInstance();
			Perro pCreado = new Perro(nombrePerro, razaPerro);
			boolean creado = dao.insert(pCreado);
						
			
			if (creado==true) {
				logger.info("Perro creado OK");
				return Response.status(201).entity(pCreado).build();
			} else {
				logger.warn("Error al crear perro. Perro ya existente");
				return Response.status(409).build();
			}
		} catch (Exception e) {
			logger.warn("Error al crear perro",e);
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
	
	
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
			
			PerreraServiceImpl dao = PerreraServiceImpl.getInstance();
			Perro pModificar = null;
			
			pModificar = (Perro) dao.getById(idPerro);
			
			if (pModificar == null) {
				logger.warn("Error al modificar perro. Perro no existe");
				return Response.noContent().build();
			} else {
				pModificar.setNombre(nombrePerro);
				pModificar.setRaza(razaPerro);
				
				dao.update(pModificar);
				logger.info("Perro con id=" + idPerro + " modificado OK");
				
				return Response.ok().entity(pModificar).build();
			}
		} catch (Exception e) {
			logger.warn("Error inesperado al modificar perro con id=" + idPerro ,e);
			return Response.status(500).build();

		}
	}
	
}
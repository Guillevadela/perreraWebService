package com.ipartek.formacion.perrera.controller;

import java.util.ArrayList;
import java.util.Date;

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

import org.hibernate.Session;

import com.ipartek.formacion.perrera.dao.PerroDAOImpl;
import com.ipartek.formacion.perrera.pojo.Perro;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * El poryecto hace refencia al proyecto skalada
 *
 * @author Curso
 *
 */
@Path("/perro")
@Api(value = "/perro")
public class PerroController {
	private Session s;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Listado de Perros", notes = "Listado de perros existentes en la perrera, limitado a 1.000", response = Perro.class, responseContainer = "List")

	@ApiResponses(value = { @ApiResponse(code = 200, message = "Todo OK"),
			@ApiResponse(code = 500, message = "Error inexperado en el servidor") })
	public Response getAll(
			@ApiParam(name = "orderBy", required = false, value = "Filtro para ordenar los perros de forma ascendente o descendente, posibles valores [asc|desc]") @DefaultValue("asc") @QueryParam("orderBy") String orderBy,
			@ApiParam(name = "campo", required = false, value = "Filtro para ordenar por 'campo' los perros, posibles valores [id|nombre|raza]") @DefaultValue("id")  @QueryParam("campo") String campo) {
		try {
			PerroDAOImpl dao = PerroDAOImpl.getInstance();
			ArrayList<Perro> perros = (ArrayList<Perro>) dao.getAll(orderBy, campo);
			return Response.ok().entity(perros).build();
		} catch (Exception e) {
			return Response.serverError().build();
		}
	}
	

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Busca un perro por su ID", notes = "devuelve un perro mediante el paso de su ID", response = Perro.class, responseContainer = "Perro")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Todo OK"),
			@ApiResponse(code = 204, message = "No existe perro con esa ID"),
			@ApiResponse(code = 500, message = "Error inexperado en el servidor") })
	public Response getById(@PathParam("id") int idPerro) {

		try {
			Perro perro = null;
			PerroDAOImpl dao = PerroDAOImpl.getInstance();
			perro = dao.getById(idPerro);
			if (perro == null) {
				return Response.noContent().build();
			}
			return Response.ok().entity(perro).build();
		} catch (Exception e) {
			return Response.serverError().build();
		}
	}

	@POST
	@Path("/{nombre}/{raza}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Añade un perro", notes = "Crea y persiste un nuevo perro", response = Perro.class, responseContainer = "Perro")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Perro Creado con exito"),
			@ApiResponse(code = 409, message = "Perro ya Existente"),
			@ApiResponse(code = 500, message = "Error inexperado en el servidor") })
	public Response post(@PathParam("nombre") String nombrePerro,
			@PathParam("raza") String razaPerro) {
		try {
			
			PerroDAOImpl dao = PerroDAOImpl.getInstance();

			Perro pCreado = new Perro(nombrePerro, razaPerro);
			boolean creado = dao.insert(pCreado);
			if (creado) {
				return Response.status(201).entity(pCreado).build();
			} else {
				return Response.status(409).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}

	@PUT
	@Path("/{id}/{nombre}/{raza}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Modifica un perro", notes = "Modifica un perro ya existente mediante su identificador", response = Perro.class, responseContainer = "Perro")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Todo OK"),
			@ApiResponse(code = 204, message = "No existe perro con ese ID"),
			@ApiResponse(code = 409, message = "Perro existente, no se puede modificar"),
			@ApiResponse(code = 500, message = "Error inexperado en el servidor") })
	public Response put(@PathParam("id") int idPerro,
			@PathParam("nombre") String nombrePerro,
			@PathParam("raza") String razaPerro) {
		try {
			PerroDAOImpl dao = PerroDAOImpl.getInstance();
			Perro pModificar = new Perro(nombrePerro, razaPerro);
			pModificar.setId(idPerro);
			
			boolean modificado = dao.update(pModificar);
			
			if (!modificado) {
				return Response.noContent().build();
			} else {
				return Response.ok().entity(pModificar).build();
			}
		} catch (Exception e) {
			return Response.status(500).build();

		}
	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Elimina un perro", notes = "Elimina un perro mediante el paso de su ID", response = Perro.class, responseContainer = "FechaHora")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Perro eliminado"),
			@ApiResponse(code = 204, message = "No existe Perro con ese ID"),
			@ApiResponse(code = 500, message = "Error inexperado en el servidor") })
	public Response delete(@PathParam("id") int idPerro) {

		try {
			Date date = new Date();
			PerroDAOImpl dao = PerroDAOImpl.getInstance();
			dao.delete(idPerro);
			return Response.ok().entity(date).build();
		} catch (Exception e) {
			return Response.serverError().build();
		}
	}
	
	
	
	
	
	
}
package ec.edu.ups.rest;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ec.edu.ups.ejb.UserFacade;
import ec.edu.ups.entities.User;
import ec.edu.ups.utils.MathFunction;

@Path("/cuenta/")
public class AccountResource {
	
	@EJB
	private UserFacade userFacade;
	
	@GET
    @Path("/buscarUsuarios/{cedula}")
    @Produces(MediaType.APPLICATION_JSON)
	public Response getUserByDNI(@PathParam("cedula") String dni) {
		Jsonb jsonb = JsonbBuilder.create();
		try {
			User user = userFacade.findUserByDNI(dni);
			return Response.ok(jsonb.toJson(user))
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
					.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE").build();
		} catch (Exception e) {
			return Response.status(404).entity("No se encuentra el usuario").build();
		}
	}
	
	@GET
    @Path("/usuarios")
    @Produces(MediaType.APPLICATION_JSON)
	public Response getUsers() {
		Jsonb jsonb = JsonbBuilder.create();
		try {
			List<User> users = userFacade.findAll();
			return Response.ok(jsonb.toJson(users))
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
					.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE").build();
		} catch (Exception e) {
			return Response.status(404).entity("No hay usuarios").build();
		}
	}
	
	@POST
    @Path("/loginUsuario")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response LoginUser(
			@FormParam("correo") String key,
			@FormParam("contrase?a") String password) {
		Jsonb jsonb = JsonbBuilder.create();
		try {
			User user = userFacade.loginUser(key, passwordMD5(password));
			if(user== null) {
				
				return Response.ok("Usuario no encontrado").build();
			}else {
				if(user.getRole() == 'C' && user.isDeleted()) {
					return Response.ok("Hola Bienvenido : "+user.getName()).build();
					
				}
			}
			return Response.status(201).entity(jsonb.toJson(user))
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
					.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE").build();
		} catch (Exception e) {
			return Response.status(405).entity("Usuario y contrase?a incorrectos").build();
		}
		
	}
	
	@PUT
    @Path("/crearUsuarios")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public Response CreateUser(
			@FormParam("correo") String email,
			@FormParam("cedula") String dni,
			@FormParam("usuario") String username,
			@FormParam("contrase?a") String password,
			@FormParam("confirmaContrase?a") String confirmPassword,
			@FormParam("nombre") String name,
			@FormParam("apellido") String lastname) throws IOException {
		password = passwordMD5(password);
		confirmPassword = passwordMD5(confirmPassword);
		if (userFacade.findUserByDNI(dni) == null) {
			return Response.status(404).entity("Esta c?dula no esta en el sistema").build();
		} 
		
		if (userFacade.findUserByUsername(username) != null) {
			return Response.status(405).entity("Este nombre de usuario no esta disponible").build();
		} 
		User user = userFacade.findUserByDNI(dni);
		
		if (userFacade.findUserByEmail(email) != null 
				&& !email.equals(user.getEmail())) {
			return Response.status(405).entity("Este correo ya esta en uso").build();
		}
		
		if (!password.equals(confirmPassword)) {
			return Response.status(405).entity("Las contrase?as no coinciden").build();
		}
		
		user.setEmail(email);
		user.setUsername(username);
		user.setPassword(password);
		user.setName(name);
		user.setLastname(lastname);
		userFacade.update(user);
		return Response.status(201).entity("Ususario creado correctamente").build();
		
	}
	
	@PUT
	@Path("/actualizarUsuarios")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public Response updateUser(
			@FormParam("id") int id,
			@FormParam("correo") String email,
			@FormParam("usuario") String username,
			@FormParam("cedula") String cedula,
			@FormParam("oldPassword") String oldPassword,
			@FormParam("newPassword") String newPassword,
			@FormParam("confirmPassword") String confirmPassword,
			@FormParam("nombre") String name,
			@FormParam("apellido") String lastname) throws IOException {
		
		oldPassword = passwordMD5(oldPassword);
		newPassword = passwordMD5(newPassword);
		confirmPassword = passwordMD5(confirmPassword);
		
		User user = userFacade.read(id);
		
		if (!user.getPassword().equals(oldPassword)) {
			return Response.status(401).entity("Contrase?a actual Incorrecta").build();
		}
		
		if (!newPassword.equals(confirmPassword)) {
			return Response.status(405).entity("Las contrase?as no coinciden").build();
		}
		
		if (userFacade.findUserByEmail(email) != null 
				&& !email.equals(user.getEmail())) {
			return Response.status(405).entity("Email ya existe").build();
		} 
		if (userFacade.findUserByUsername(username) != null
				&& !username.equals(user.getUsername())) {
			return Response.status(405).entity("Username ya existe").build();
		}
		
		user.setEmail(email);
		user.setUsername(username);
		user.setPassword(newPassword);
		user.setName(name);
		user.setLastname(lastname);
		userFacade.update(user);
		return Response.status(201).entity("Usuario actualizado correctamente").build();
	}
	
	@DELETE
	@Path("/anular/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteUser(@PathParam("id") int id){
		
		User user = userFacade.read(id);
		user.setDeleted(true);
		userFacade.update(user);
		System.out.println("REST/cuenta:delete--> "+id);
		return Response.status(204).entity("Usuario correctamente eliminado de forma l?gica" + id).build();
	}
	
	private String passwordMD5(String password) {
		return MathFunction.getMd5(password);
	}
}

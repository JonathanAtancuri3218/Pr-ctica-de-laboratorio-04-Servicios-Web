package ec.edu.ups.servicios;

import java.util.List;

import javax.ejb.EJB;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ec.edu.ups.ejb.UsuarioFacade;
import ec.edu.ups.modelo.Usuario;

@Path("/usuario")
public class UsuarioRest {

    @EJB
    private UsuarioFacade usuarioFacade;
    private Jsonb jsonb;

   
    
    
    @POST
    @Path("/loginCliente")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public Response loginCliente(@FormParam("correo") String correo, @FormParam("password") String password) {	
    	
    	System.out.println("Correo: "+correo+ " password: "+password);
    	
    	//Persona persona = ejbPersona.inicioSesion(usuario, password);
    			
    	Usuario persona = usuarioFacade.finByEmailAndPass(correo, password);
    	
    	if(persona==null) {
    		return Response.ok("Persona no encotrada").build();
    	}else {
    		
    		if (persona.getRol().equals("cliente") && persona.isActivo() == true) {
    			return Response.ok("Bienvenido : "+persona.getNombre()+"!!").build();
			}else {
				
				return Response.ok("Cliente no encontrado").build();
			}
    	}    	
    }
    
    @POST
    @Path("/crearCuenta")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public Response crearCuenta(@FormParam("correo") String correo, 
    							@FormParam("password") String password, 
    							@FormParam("cedula") String cedula, 
    							@FormParam("nombre") String nombre, 
    							@FormParam("apellido") String apellido) {		
    	
    	
    	 Usuario per = usuarioFacade.find(cedula);
    	
    	if (per != null) {
    		
        	try {
        		//persona = new Persona(nombre, apellido, cedula, direccion, telefono, correo, password, 'C', "Activo");
        		
        		//per = new Usuario();        		
        		per.setCorreo(correo);
        		per.setPassword(password);
        		per.setNombre(nombre);
        		per.setApellido(apellido);
        		//per.setActivo(true);
        		usuarioFacade.edit(per);
        		return Response.ok("Usuario Creado, gracias por unise a nosotros: "+per.getNombre()+" "+per.getApellido()).build();
        	}catch (Exception e) {
    			return Response.ok("Usuario no creado").build();
    		}
		}else {
			return Response.ok("El usuario han no a sido registrado en la base de datos").build();
			
		}
    	
    	
    	
    }
    
    @POST
    @Path("/modificarCuenta")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public Response modificarCuenta(@FormParam("correo") String correo, @FormParam("password") String password, @FormParam("cedula") String cedula, @FormParam("nombre") String nombre, @FormParam("apellido") String apellido, @FormParam("direccion") String direccion,@FormParam("telefono") String telefono){
    	//Usuario  persona=ejbPersona.buscarPorCedula(cedula);
    	Usuario persona = (Usuario) usuarioFacade.find(cedula);
    	if(persona==null) {
    		return Response.ok("Cliente no encontrada!").build();
    	}else {
    		persona.setCorreo(correo);
    		persona.setPassword(password);
       		persona.setNombre(nombre);
    		persona.setApellido(apellido);    		
    		//persona.setDireccion(direccion);
    		//persona.setTelefono(telefono);
    		usuarioFacade.edit(persona);
    		return Response.ok("Sus datos han sido Modificados!").build();
    	}
    	
    }
    
    @POST
    @Path("/anularCuenta")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public Response eliminarCuenta(@FormParam("cedula") String cedula) {
    	//Persona persona = ejbPersona.buscarPorCedula(cedula);
    	Usuario persona = (Usuario) usuarioFacade.find(cedula);
    	if(persona==null) {
    		return Response.ok("Cliente no encontrado!").build();
    	}else {
    		persona.setActivo(false);
    		usuarioFacade.edit(persona);
    		return Response.ok("Usuario correctamente eliminado de forma lógica").build();
    	}
    	
    }
    
    
    

   

    @DELETE
    @Path("/{usuarioID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteDireccion(@PathParam("usuarioID") String usuarioID) {
        //System.out.println("usuario cedula "+id);
        if (usuarioID != null) {
            jsonb = JsonbBuilder.create();
            Usuario usuario = usuarioFacade.find(usuarioID);

            if (usuario != null) {
                try {
                    usuario.setActivo(false);
                    usuarioFacade.edit(usuario);
                    return Response.ok().entity("Usuario eliminado")
                            .header("Access-Control-Allow-Origin", "*")
                            .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                            .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                            .header("Access-Control-Allow-Credentials", "true")
                            .allow("OPTIONS").build();

                } catch (Exception e) {
                    return Response.status(500).entity("Error al eliminar: " + e)
                            .header("Access-Control-Allow-Origin", "*")
                            .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                            .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                            .header("Access-Control-Allow-Credentials", "true")
                            .allow("OPTIONS").build();
                }
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Usuario no encontrado")
                        .header("Access-Control-Allow-Origin", "*")
                        .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                        .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                        .header("Access-Control-Allow-Credentials", "true")
                        .allow("OPTIONS").build();
            }

        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Datos insuficientes")
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                    .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                    .header("Access-Control-Allow-Credentials", "true")
                    .allow("OPTIONS").build();
        }
    }

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginUsuario(@FormParam("correo") String correo, @FormParam("password") String password) {

        if (correo != null && password != null) {
            jsonb = JsonbBuilder.create();
            Usuario usuario = usuarioFacade.finByEmailAndPass(correo, password);

            //System.out.println("Json Usuario "+jsonb.toJson(usuario));
            if (usuario != null) {

                if (usuario.getRol().equals("cliente")) {
                    try {
                        //HttpSession session = Session.getSession();
                        //session.setAttribute("token", session.getId());
                        //session.setAttribute("usuario", usuario);

                        //System.out.println("Session iniciada con " + session.getId());
                    } catch (Exception e) {
                        System.out.println("Error en la sesion: " + e);
                    }

                    return Response.ok(jsonb.toJson(usuario)).header("Access-Control-Allow-Origin", "*").build();

                } else {
                    return Response.status(Response.Status.NOT_FOUND).entity("Usuario no existe").build();
                }

            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Usuario no existe").build();
            }

        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Datos insuficientes").build();
        }
    }

}
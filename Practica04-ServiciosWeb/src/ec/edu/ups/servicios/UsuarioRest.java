package ec.edu.ups.servicios;

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

    /*
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getUsuario() {
        return "Hola Mundo desde el path Productos";
    }
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUsuario(String jsonUsuario) {
        System.out.println("entrando al metodo de crear usuario.............................. " );
        jsonb = JsonbBuilder.create();
        System.out.println("Usuario en registro " + jsonUsuario);

        try {
            Usuario newUsuaio = jsonb.fromJson(jsonUsuario, Usuario.class);
            newUsuaio.setCambioPassword(true);
            usuarioFacade.create(newUsuaio);

            return Response.ok().entity(newUsuaio)
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                    .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                    .header("Access-Control-Allow-Credentials", "true")
                    .allow("OPTIONS").build();

        } catch (Exception e) {

            return Response.status(500).entity("Usuario no creado: " + e)
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                    .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                    .header("Access-Control-Allow-Credentials", "true")
                    .allow("OPTIONS").build();

        }
    }

    @PUT
    @Path("/{usuarioID}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response editDireccion(@PathParam("usuarioID") String usuarioID, String jsonLocalidad) {
        //System.out.println("usuario cedula "+id);
        if (usuarioID != null) {
            jsonb = JsonbBuilder.create();
            Usuario usuario = usuarioFacade.find(usuarioID);
            if (usuario != null) {
                try {
                    usuarioFacade.edit(jsonb.fromJson(jsonLocalidad, Usuario.class));
                    return Response.ok().entity("Usuario actualizado").header("Access-Control-Allow-Origin", "*").build();
                } catch (Exception e) {
                    return Response.status(500).entity("Error al actualizar: " + e)
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
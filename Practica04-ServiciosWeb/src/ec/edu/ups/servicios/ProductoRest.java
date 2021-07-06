package ec.edu.ups.servicios;

import java.util.List;

import javax.ejb.EJB;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ec.edu.ups.ejb.BodegaFacade;
import ec.edu.ups.ejb.CategoriaFacade;
import ec.edu.ups.ejb.ProductoFacade;
import ec.edu.ups.modelo.Producto;

@Path("/productos")
public class ProductoRest {

    @EJB
    private CategoriaFacade categoriaFacade;
    @EJB
    private BodegaFacade bodegaFacade;
    @EJB
    private ProductoFacade productoFacade;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") Integer id) {
        if (id != null) {
            Jsonb jsonb = JsonbBuilder.create();
            Producto pro = productoFacade.find(id);
            pro.getCategoria();
            System.out.println("CAtegoria ........." + pro.getCategoria());
            
            if (pro != null) {
                return Response.ok(jsonb.toJson(pro)).header("Access-Control-Allow-Origin", "*").build();
            }else {
                return Response.status(Response.Status.NOT_FOUND).entity("Productos no encontrado").build();
            }

        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Datos Insuvicientes").build();

        }

    }

 
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get() {
        System.out.println("Entrando al metodo................................ ");
        Jsonb jsonb = JsonbBuilder.create();
        //Categoria cate = categoriaFacade.find(id);

        List<Producto> pro = productoFacade.findAll();
        //pro.getCategoria();

        System.out.println("CAtegoria ........." + pro);
        return Response.ok(jsonb.toJson(pro)).header("Access-Control-Allow-Origin", "*").build();
    }

}
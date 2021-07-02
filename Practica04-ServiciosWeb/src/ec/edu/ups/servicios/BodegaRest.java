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
import ec.edu.ups.ejb.ProductoFacade;
import ec.edu.ups.modelo.Bodega;
import ec.edu.ups.modelo.Producto;

@Path("/bodegas")
public class BodegaRest {
    
   
    
    @EJB
    private BodegaFacade bodegaFacade;
    
   @EJB
   private ProductoFacade productoFacade;
    
    
   private List<Producto>productos;
    private List<Bodega> bodegas;
    
     @GET 
     @Path("/{id}")
     @Produces(MediaType.TEXT_PLAIN)
     public Response bodegasID(@PathParam("id")Integer id){
         
         if (id !=null) {
             
         System.out.println("ID PRODUCTO"+ id); 
         Jsonb jsonb =JsonbBuilder.create();
         Bodega bodega=bodegaFacade.find(id);
         
             if (bodega !=null) {
                 return Response.ok(jsonb.toJson(bodega)).build();
             }else{
                 return Response.status(Response.Status.NOT_FOUND).entity("Bodegas no encontrado").build();
          
             
             }
         }else {
         return Response.status(Response.Status.NOT_FOUND).entity("Datos Insuvicientes").build();
                 
              
            }
         }
     
     
     
     
     @GET 
     @Produces(MediaType.TEXT_PLAIN)
     public Response getbodegas(){
         Jsonb jsonb=JsonbBuilder.create();
         bodegas=bodegaFacade.findAll();
        return Response.ok(jsonb.toJson(bodegas)).build();
        
     }
     
     
    @GET 
    @Path ("/{id}/productos")
    @Produces(MediaType.APPLICATION_JSON)
      public Response getProducto(@PathParam("id")Integer id){
          
          if (id !=null) {
              Jsonb jsonb=JsonbBuilder.create();
          productos=productoFacade.findByBodega(id);
            
        if (productos !=null) {
            return Response.ok(jsonb.toJson(productos)).build();
        }else{
                 return Response.status(Response.Status.NOT_FOUND).entity("Productos no encontrado").build();
          
             
             }
         }else {
         return Response.status(Response.Status.NOT_FOUND).entity("Datos Insuvicientes").build();
                 
              
            }
         }
     }

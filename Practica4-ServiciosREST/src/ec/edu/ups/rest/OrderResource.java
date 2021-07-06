package ec.edu.ups.rest;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.ejb.EJB;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ec.edu.ups.ejb.OrderHeadFacade;
import ec.edu.ups.ejb.ProductWarehouseFacade;
import ec.edu.ups.ejb.UserFacade;
import ec.edu.ups.entities.BillDetail;
import ec.edu.ups.entities.BillHead;
import ec.edu.ups.entities.OrderDetail;
import ec.edu.ups.entities.OrderHead;
import ec.edu.ups.entities.ProductWarehouse;
import ec.edu.ups.entities.User;

@Path("/pedidos/")
public class OrderResource {
	
	@EJB
	private ProductWarehouseFacade productWarehouseFacade;
	
	@EJB
	private OrderHeadFacade orderHeadFacade;
	
	@EJB
	private UserFacade userFacade;
	
	@GET
    @Path("/buscarProductos/bodega/{bodegaId}")
    @Produces(MediaType.APPLICATION_JSON)
	public Response getProductListByWarehouseId(
			@PathParam("bodegaId") Integer warehouseId) {
		Jsonb jsonb = JsonbBuilder.create();
		List<ProductWarehouse> productWarehouses = productWarehouseFacade.findByWarehouseId(warehouseId, "");
		return Response.status(200).entity(jsonb.toJson(productWarehouses))
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
				.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE").build();
	}
	
	@GET
    @Path("/buscarProductos/categoria/{categoryId}")
    @Produces(MediaType.APPLICATION_JSON)
	public Response getProductListByCategoryId(
			@PathParam("categoryId") Integer categoryId) {
		Jsonb jsonb = JsonbBuilder.create();
		List<ProductWarehouse> productWarehouses = productWarehouseFacade.findByCategoryId(categoryId, "");
		return Response.status(200).entity(jsonb.toJson(productWarehouses))
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
				.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE").build();
	}
	
	@GET
    @Path("/buscarProductos/bodega-categoria/{bodegaId}/{categoriaId}")
    @Produces(MediaType.APPLICATION_JSON)
	public Response getProductListByWarehouseAndCategoryId(
			@PathParam("bodegaId") Integer warehouseId, 
			@PathParam("categoriaId") Integer categoryId) {
		Jsonb jsonb = JsonbBuilder.create();
		List<ProductWarehouse> productWarehouses = productWarehouseFacade.findByWarehouseAndCategoryId(warehouseId, categoryId, "");
		return Response.status(200).entity(jsonb.toJson(productWarehouses))
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
				.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE").build();
	}
	
	@POST
	@Path("/crear")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
	public Response postOrder(@FormParam("usuarioId") Integer userId,
			@FormParam("direccion") String address,
			@FormParam("productoId") List<Integer> productId, 
			@FormParam("monto") List<Integer> amounts) {
		
		try {
			User user = userFacade.read(userId);
			
			if (user == null || user.getId() == 0 || user.isDeleted()) {
				return Response.status(404).entity("El usuario no existe").build();
			}
			
			if(productId.size() != amounts.size() ) {
				return Response.status(405).entity("La cantidad de productos "
						+ "y cantidades debe ser la misma").build();
			}
			int n = productId.size();
			OrderHead orderHead = new OrderHead(address, "Enviado".toUpperCase(), 
					new GregorianCalendar());
			for (int i = 0; i < n; i++) {
				ProductWarehouse productWarehouse = productWarehouseFacade
						.read(productId.get(i));
				if (productWarehouse == null || productWarehouse.getId() == 0) {
					return Response.status(404).entity("Producto no encontrado: " 
							+ productId.get(i)).build();
				}
				if (amounts.get(i) > productWarehouse.getStock()) {
					return Response.status(405).entity(productWarehouse.getProduct().getName() 
							+ " no cuenta con suficiente Stock").build();
				}
				orderHead.createOrderDetail(amounts.get(i), productWarehouse);
			}
			
			if (orderHead.getOrders() == null || orderHead.getOrders().isEmpty()) {
				return Response.status(405).entity("No se puede crear un pedido con productos "
						+ "vacíos").build();
			}
			orderHead.setUser(user);
			orderHeadFacade.create(orderHead);
			return Response.status(201).entity("Se creó el pedido correctamente").build();
		} catch (Exception e) {
			return Response.status(500).entity("Error interno").build();
		}
	}
	
	@PUT
	@Path("/updateStatus")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
	public Response updateStatus(@FormParam("ordenId") Integer orderHeadId, 
			@FormParam("status") String status) {
		try {
			OrderHead orderHead = orderHeadFacade.read(orderHeadId);
			orderHead.setStatus(status.toUpperCase());
			if (!validStatus(orderHead)) {
				return Response.status(405).entity("El estado ingresado no es válido").build();
			}
			orderHeadFacade.update(orderHead);
			Jsonb jsonb = JsonbBuilder.create();
			return Response.status(201).entity(jsonb.toJson(orderHead)).build();
		} catch (Exception e) {
			return Response.status(500).entity("Error al realizar la solicitud").build();
		}
	}
	
	@GET
    @Path("/buscar/usuario/{usuarioId}")
    @Produces(MediaType.APPLICATION_JSON)
	public Response getOrderListByUserId(@PathParam("usuarioId") Integer userId) {
		List<OrderHead> orderHeads = orderHeadFacade.findByUserId(userId);
		Jsonb jsonb = JsonbBuilder.create();
		return Response.ok(jsonb.toJson(orderHeads))
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
				.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE").build();
	}
	
	private boolean validStatus(OrderHead orderHead) {
		switch (orderHead.getStatus().toUpperCase()) {
		case "ENVIADO":
		case "EN PROCESO":
		case "EN CAMINO":
		case "FINALIZADO":
			break;
		case "RECEPTADO":
			orderReceived(orderHead);
			break;
		default:
			return false;
		}
		return true;
	}
	
	private boolean orderReceived(OrderHead orderHead) {
		if (orderHead.getBillHead() != null) {
			return true;
		}
		BillHead billHead = new BillHead();
		billHead.setDate(new GregorianCalendar());
		billHead.setStatus('A');
		for (OrderDetail orderDetail : orderHead.getOrders()) {
			billHead.createBillDetail(orderDetail.getAmount(), 
					orderDetail.getProductWarehouse().getPrice(), 
					orderDetail.getProductWarehouse(), billHead);
		}
		billHead.setUser(orderHead.getUser());
		billHead.calculateTotal();
		orderHead.setBillHead(billHead);
		return true;
	}
	
	@POST
    @Path("/facturarPedido")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response facturarPedido(@FormParam("pedidoCabID") String pedidoID) throws Exception {
        
    	OrderHead pedido = null;
    	BillDetail billDetails=null;
    	User user = null;
        
    	try {
			pedido = orderHeadFacade.find(Integer.parseInt(pedidoID));
		} catch (Exception e) {
			System.out.println("Error al obtener informacion");
		}
        
        if (pedido!= null) {
        	pedido.setStatus("En camino");
        	
        	//pedidoCabeceraFacade.edit(pedido);
        	pedido = orderHeadFacade.find(Integer.parseInt(pedidoID));
            
            BillHead facturaCabecera = new BillHead();
            //facturaCabecera = new FacturaCabecera(0, new Date(), (float)0.0, (float)0.0, (float)14.0, 'N', pedido.getPersona());
            //facturaCabecera = new BillHead(0, 0.0, 0.0, new Date(), 'N', 0.0, true, billDetails, user, pedido);
            BillDetail detalleFactura;

            List<OrderDetail> detallesPedido  = pedido.getOrders();
            /*
            for (PedidoDetalle pedidoDetalle : detallesPedido) {
            	detalleFactura = new FacturaDetalle(0, pedidoDetalle.getCantidad(), pedidoDetalle.getTotal(), facturaCabecera, pedidoDetalle.getProducto());
            	facturaCabecera.addFacturasDetalle(detalleFactura);
            }
            */
            
            /*
            facturaCabecera.setStatus('A');
            facturaCabecera.setSubtotal(pedido.getSubtotal());
            facturaCabecera.setTotal(pedido.getTotal());
            
            facturaCabeceraFacade.create(facturaCabecera);
            pedido.setEstado("En proceso");
            pedidoCabeceraFacade.edit(pedido);

        	
        	*/
        	
        	return Response.ok("Se logro facturar el pedido, se encuentra en estado de En Proceso de revision")
                    .header("Access-Control-Allow-Origins", "*")
                    .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                    .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE")
                    .build();
        
        }else	
        	return Response.ok("No se logro facturar el pedido" )
	                .header("Access-Control-Allow-Origins", "*")
	                .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
	                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE")
	                .build();
    }

}

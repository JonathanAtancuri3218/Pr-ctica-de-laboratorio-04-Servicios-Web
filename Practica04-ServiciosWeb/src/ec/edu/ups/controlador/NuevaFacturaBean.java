package ec.edu.ups.controlador;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.FacesConfig;
import javax.inject.Named;

import ec.edu.ups.ejb.FacturaCabeceraFacade;
import ec.edu.ups.ejb.InventarioFacade;
import ec.edu.ups.ejb.ProductoFacade;
import ec.edu.ups.ejb.UsuarioFacade;
import ec.edu.ups.modelo.FacturaCabecera;
import ec.edu.ups.modelo.FacturaDetalle;
import ec.edu.ups.modelo.Inventario;
import ec.edu.ups.modelo.Localidad;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.modelo.Usuario;

@FacesConfig(version = FacesConfig.Version.JSF_2_3)
@Named(value = "nuevaFacturaBean")
@SessionScoped
public class NuevaFacturaBean implements Serializable {

    private static final long serialVersionUID = 1;

    @EJB
    private UsuarioFacade usuarioFacade;
    @EJB
    private ProductoFacade productoFacade;
    @EJB
    private FacturaCabeceraFacade facturaCabeceraFacade;
    @EJB
    private InventarioFacade inventarioFacade;

    private String cedula;
    private String productoNombre;
    private Usuario usuario;
    private Localidad localidad;
    private List<FacturaDetalle> facturaDetalles;
    private List<Producto> productos;
    private Producto producto;
    private FacturaCabecera facturaCabecera;
    private List<Inventario> inventarios;
    private Inventario inventario;

    /**
     * Creates a new instance of NuevaFacturaBean
     */
    public NuevaFacturaBean() {
    }

    @PostConstruct
    public void init() {
        facturaDetalles = new ArrayList<>();
        facturaCabecera = new FacturaCabecera();

    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public String getProductoNombre() {
        return productoNombre;
    }

    public void setProductoNombre(String productoNombre) {
        this.productoNombre = productoNombre;
    }

    public List<FacturaDetalle> getFacturaDetalles() {
        return facturaDetalles;
    }

    public void setFacturaDetalles(List<FacturaDetalle> facturaDetalles) {
        this.facturaDetalles = facturaDetalles;
    }

    public FacturaCabecera getFacturaCabecera() {
        return facturaCabecera;
    }

    public void setFacturaCabecera(FacturaCabecera facturaCabecera) {
        this.facturaCabecera = facturaCabecera;
    }

    public Localidad getLocalidad() {
        return localidad;
    }

    public void setLocalidad(Localidad localidad) {
        this.localidad = localidad;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public List<Inventario> getInventarios() {
        return inventarios;
    }

    public void setInventarios(List<Inventario> inventarios) {
        this.inventarios = inventarios;
    }

    public void buscarUsuario() {
        //System.out.println("usuario a buscar.."+this.cedula);
        try {
            this.usuario = usuarioFacade.find(this.cedula);
        } catch (Exception e) {
        }
    }

    public void buscarProducto() {
        this.productos = productoFacade.findByName(this.productoNombre);
    }

    public void limpiarProductos() {
        this.producto = null;
        this.productos = new ArrayList<>();
    }

    public int contarProductos(int codigo) {
        return productoFacade.countAllProducts(codigo);
    }

    public void listarInventarios(Producto producto) {
        this.producto = producto;
        this.inventarios = producto.getInventarios();
    }

    public String addProducto(Inventario inventario) {
        this.inventario = inventario;
        FacturaDetalle fd = new FacturaDetalle();
        fd.setCantidad(1);
        fd.setProducto(producto);

        if (this.facturaDetalles.contains(fd)) {
            System.out.println("Es el mismo producto");
            this.addCantidad(facturaDetalles.get(facturaDetalles.indexOf(fd)));
            //System.out.println("Detalles mass: "+this.facturaDetalles);
        } else {
            //System.out.println("Es un nuevo producto");
            this.facturaDetalles.add(fd);
            //System.out.println("Detalles agregado: "+this.facturaDetalles);
        }
        this.facturaCabecera.setFacturaDetalles(this.facturaDetalles);
        this.facturaCabecera.calcularSubTotal();
        this.facturaCabecera.calcularTotal();
        return null;
    }

    public void addCantidad(FacturaDetalle fd) {
        System.out.println("Cantidad add cantidad.." + fd.getCantidad());
        if (this.inventario.getCantidad() > fd.getCantidad()) {
            fd.setCantidad(fd.getCantidad() + 1);
            this.facturaCabecera.setFacturaDetalles(this.facturaDetalles);
            this.facturaCabecera.calcularSubTotal();
            this.facturaCabecera.calcularTotal();
        }
    }

    public void deleteCantidad(FacturaDetalle fd) {
        System.out.println("Cantidad delete cantidad.." + fd.getCantidad());
        if (fd.getCantidad() - 1 == 0) {
            this.facturaDetalles.remove(fd);
        } else {
            fd.setCantidad(fd.getCantidad() - 1);
        }

        this.facturaCabecera.setFacturaDetalles(this.facturaDetalles);
        this.facturaCabecera.calcularSubTotal();
        this.facturaCabecera.calcularTotal();
    }

    public void deleteProducto(FacturaDetalle fd) {
        if (facturaDetalles.contains(fd)) {
            facturaDetalles.remove(fd);
        }

        this.facturaCabecera.setFacturaDetalles(this.facturaDetalles);
        this.facturaCabecera.calcularSubTotal();
        this.facturaCabecera.calcularTotal();
    }

    public String generarFactura() {

        //System.out.println("Locaidad en guardar factura.." + this.localidad);
        if (usuario != null && localidad != null && facturaDetalles.size() > 0) {

            this.facturaCabecera.setUsuario(this.usuario);
            this.facturaCabecera.setLocalidad(this.localidad);
            this.facturaCabecera.setFecha(LocalDate.now());
            this.facturaCabecera.calcularSubTotal();
            this.facturaCabecera.calcularTotal();
            try {
                for (FacturaDetalle fd : this.facturaCabecera.getFacturaDetalles()) {
                    fd.setFacturaCabecera(facturaCabecera);
                    for (Inventario inv : fd.getProducto().getInventarios()) {
                        inv.setCantidad(inv.getCantidad() - fd.getCantidad());
                        inventarioFacade.edit(inv);
                    }
                    facturaCabeceraFacade.create(this.facturaCabecera);
                }
            } catch (Exception e) {
            }

            facturaCabecera = new FacturaCabecera();
            usuario = null;
            localidad = null;
            facturaDetalles = new ArrayList<>();
        }

        return null;
    }

    public void cancelarFactura() {
        facturaCabecera = new FacturaCabecera();
        usuario = null;
        localidad = null;
        facturaDetalles = new ArrayList<>();
        productos = null;
    }

}

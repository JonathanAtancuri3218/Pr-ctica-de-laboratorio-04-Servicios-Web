package ec.edu.ups.controlador;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.FacesConfig;
import javax.inject.Named;

import ec.edu.ups.ejb.BodegaFacade;
import ec.edu.ups.ejb.InventarioFacade;
import ec.edu.ups.modelo.Bodega;
import ec.edu.ups.modelo.Inventario;
import ec.edu.ups.modelo.Producto;

@FacesConfig(version = FacesConfig.Version.JSF_2_3)
@Named(value = "inventarioBean")
@SessionScoped
public class InventarioBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private InventarioFacade inventarioFacade;
    @EJB
    private BodegaFacade bodegaFacade;

    private List<Inventario> inventarios;
    private Inventario inventario;
    private Bodega bodega;
    private Producto producto;
    private int cantidad;
    private List<Bodega> bodegas;

    /**
     * Creates a new instance of InventarioBean
     */
    public InventarioBean() {
    }

    public List<Inventario> getInventarios() {
        return inventarios;
    }

    public void setInventarios(List<Inventario> inventarios) {
        this.inventarios = inventarios;
    }

    public Inventario getInventario() {
        return inventario;
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }

    public void inventarioProducto(Producto producto) {

        this.producto = producto;
        this.inventarios = producto.getInventarios();
    }

    public Bodega getBodega() {
        return bodega;
    }

    public void setBodega(Bodega bodega) {
        this.bodega = bodega;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public List<Bodega> getBodegas() {
        return bodegaFacade.findAll();
    }

    public void setBodegas(List<Bodega> bodegas) {
        this.bodegas = bodegas;
    }
    

    public String add() {
        try {
            Inventario newInventario = new Inventario(cantidad, bodega, producto);
            Inventario findInventario = inventarioFacade.findInventario(newInventario);

            if (findInventario != null) {
                findInventario.setCantidad(findInventario.getCantidad() + cantidad);
                inventarioFacade.edit(findInventario);
                this.inventarios = this.inventarioFacade.findByProducto(producto);
            } else {
                inventarioFacade.create(newInventario);
                this.inventarios.add(newInventario);
            }
            this.cantidad = 0;
        } catch (Exception e) {
        }

        return null;
    }

    public String delete(Inventario inventario) {
        System.out.println("producto en metodo eliminar "+producto);
        this.inventarioFacade.remove(inventario);
        this.inventarios = this.inventarioFacade.findByProducto(producto);
        
        return null;
    }

    public String edit(Inventario inventario) {
        inventario.setEditable(true);
        return null;
    }

    public String save(Inventario inventario) {
        inventarioFacade.edit(inventario);
        inventario.setEditable(false);
        this.inventarios = this.inventarioFacade.findByProducto(producto);
        return null;
    }

}

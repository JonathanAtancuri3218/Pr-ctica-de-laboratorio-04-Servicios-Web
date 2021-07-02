package ec.edu.ups.controlador;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.FacesConfig;
import javax.inject.Named;

import ec.edu.ups.ejb.BodegaFacade;
import ec.edu.ups.ejb.CategoriaFacade;
import ec.edu.ups.ejb.ProductoFacade;
import ec.edu.ups.modelo.Bodega;
import ec.edu.ups.modelo.Categoria;
import ec.edu.ups.modelo.Producto;

@FacesConfig(version = FacesConfig.Version.JSF_2_3)
@Named(value = "indexBean")
@SessionScoped
public class IndexBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private CategoriaFacade categoriaFacade;
    @EJB
    private BodegaFacade bodegaFacade;
    @EJB
    private ProductoFacade productoFacade;

    private List<Categoria> categorias;
    private Categoria categoria;
    private List<Bodega> bodegas;
    private Bodega bodega;
    private List<Producto> productos;
    private Producto producto;
    private String nombre;


    /**
     * Creates a new instance of ProductoBeanIndex
     */
    public IndexBean() {
    }

    @PostConstruct
    public void init() {

        try {
            this.categorias = this.categoriaFacade.findAll();
            this.productos = this.productoFacade.findAll();
            System.out.println("Productos.. " + productos);
            this.bodegas = this.bodegaFacade.findAll();

        } catch (Exception e) {
            System.out.println("Error --- " + e);
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public List<Bodega> getBodegas() {
        return bodegas;
    }

    public void setBodegas(List<Bodega> bodegas) {
        this.bodegas = bodegas;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Bodega getBodega() {
        return bodega;
    }

    public void setBodega(Bodega bodega) {
        this.bodega = bodega;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }


}
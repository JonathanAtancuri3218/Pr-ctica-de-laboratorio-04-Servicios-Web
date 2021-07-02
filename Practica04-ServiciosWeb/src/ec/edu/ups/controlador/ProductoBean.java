package ec.edu.ups.controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.FacesConfig;
import javax.faces.application.FacesMessage;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;

import ec.edu.ups.ejb.BodegaFacade;
import ec.edu.ups.ejb.CategoriaFacade;
import ec.edu.ups.ejb.ProductoFacade;
import ec.edu.ups.modelo.Bodega;
import ec.edu.ups.modelo.Categoria;
import ec.edu.ups.modelo.Producto;

@FacesConfig(version = FacesConfig.Version.JSF_2_3)
@Named(value = "productoBean")
@SessionScoped
public class ProductoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private CategoriaFacade categoriaFacade;
    @EJB
    private BodegaFacade bodegaFacade;
    @EJB
    private ProductoFacade productoFacade;

    private String nombre;
    private String medida;
    private List<Categoria> categorias;
    private Categoria categoria;
    private double precio;
    private List<Bodega> bodegas;
    private Bodega bodega;
    
    private List<Producto> productos;
    private List<String> unidadMedidas;


    /**
     * Creates a new instance of ProductoBean
     */
    public ProductoBean() {

    }

    @PostConstruct
    public void init() {
        this.unidadMedidas = new ArrayList<>();
        this.unidadMedidas = Arrays.asList(
                "unidad",
                "lb",
                "kg",
                "l"
        );

        try {
            this.categorias = this.categoriaFacade.findAll();
            //System.out.println("lista de categorias en el init.."+this.categorias);
            
            this.bodegas = this.bodegaFacade.findAll();
            this.productos = this.productoFacade.findAll();
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
        this.categorias = this.categoriaFacade.findAll();
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }


    public List<Bodega> getBodegas() {
        this.bodegas = this.bodegaFacade.findAll();
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

    public String getMedida() {
        return medida;
    }

    public void setMedida(String medida) {
        this.medida = medida;
    }

    public List<String> getUnidadMedidas() {
        return unidadMedidas;
    }

    public void newProducto() {
        this.nombre = null;
        this.medida = null;
    }

    public String add() {

        if (this.categoria != null) {
            //Inventario inventario = new Inventario(this.cantidad);
            //inventario.setBodega(this.bodega);

            Producto producto = new Producto();
            producto.setNombre(this.nombre);
            producto.setPrecio(this.precio);
            producto.setImagen("imagen.gpg");
            producto.setUnidadMedida(this.medida);
            producto.setCategoria(this.categoria);
            //producto.addInventario(inventario);

            productoFacade.create(producto);
        } else {
            FacesMessage message = new FacesMessage("Debe seleccionar una categoria y una bodega");
            throw new ValidatorException(message);
        }

        this.productos = this.productoFacade.findAll();
        return null;

    }

    public String delete(Producto producto) {
        this.productoFacade.remove(producto);
        this.productos = this.productoFacade.findAll();
        return null;
    }

    public void buscarPorBodega() {
        if (bodega != null) {
            //System.out.println("Cambio de item em bodega..." +bodegaItem.toString());
            this.productos = productoFacade.findByBodega(this.bodega.getCodigo());
        } else {
            //System.out.println("Es nulo... ");
            this.productos = this.productoFacade.findAll();
        }
    }

    public int cantidadProductos(int codigo) {
        return productoFacade.countAllProducts(codigo);
    }

    public void buscarPorNombre() {
        
        if (nombre != null && !nombre.equals("")) {
            //System.out.println("Cambio de item em bodega..." + nombre);
            this.productos = productoFacade.findByName(this.nombre);
        } else {
            //System.out.println("Es nulo... ");
            this.productos = this.productoFacade.findAll();
        }
        System.out.println("Nombre a buscar ..." +this.productos);
        nombre = null;
    }

    public String edit(Producto producto) {
        producto.setEditable(true);
        return null;
    }

    public String save(Producto producto) {
       //System.out.println("Medida a editar..." + medida);
        producto.setUnidadMedida(medida);
        producto.setCategoria(this.categoria);
        productoFacade.edit(producto);
        producto.setEditable(false);
        this.productos = this.productoFacade.findAll();
        return null;
    }

}

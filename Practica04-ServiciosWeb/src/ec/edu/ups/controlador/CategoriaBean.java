package ec.edu.ups.controlador;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.FacesConfig;
import javax.inject.Named;

import ec.edu.ups.ejb.CategoriaFacade;
import ec.edu.ups.modelo.Categoria;

@FacesConfig(version = FacesConfig.Version.JSF_2_3)
@Named(value = "categoriaBean")
@SessionScoped
public class CategoriaBean implements Serializable {

    private static final long serialVersionUID = 1;

    @EJB
    private CategoriaFacade categoriaFacade;

    private String nombre;
    private String decripcion;
    private List<Categoria> categorias;
    private String nombreCategoria;
    
    

    @PostConstruct
    public void init() {
        try {
            this.categorias = categoriaFacade.findAll();
            System.out.println("Lista categorias" + this.categorias);

        } catch (Exception e) {
            System.out.println("Error---" + e);
        }
    }

    public CategoriaBean() {
    }

    public CategoriaFacade getCategoriaFacade() {
        return categoriaFacade;
    }

    public void setCategoriaFacade(CategoriaFacade categoriaFacade) {
        this.categoriaFacade = categoriaFacade;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDecripcion() {
        return decripcion;
    }

    public void setDecripcion(String decripcion) {
        this.decripcion = decripcion;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }
    

    public String guardarDatos() {
        Categoria categoria = new Categoria();
        categoria.setNombre(this.nombre);
        categoria.setDescripcion(this.decripcion);

        System.out.println("Categoria: " + categoria.toString());

        categoriaFacade.create(categoria);

        this.categorias = categoriaFacade.findAll();
        return null;

    }

    public String delete(Categoria categoria) {
        this.categoriaFacade.remove(categoria);
        this.categorias = this.categoriaFacade.findAll();
        return null;
    }

    public String edit(Categoria categoria) {
        categoria.setEditable(true);

        return null;
    }

    public String save(Categoria categoria) {
        //System.out.println("Categoria a editar..."+categoria);

        categoriaFacade.edit(categoria);
        categoria.setEditable(false);
        this.categorias = this.categoriaFacade.findAll();
        return null;
    }
    public void newProducto() {
        this.nombre="";
        this.decripcion="";
        
   }
    public void buscarPorNombre() {
        if (nombreCategoria != null) {
            //System.out.println("Cambio de item em bodega..." +bodegaItem.toString());
            this.categorias = categoriaFacade.findByName(this.nombreCategoria);    
        } else {
            //System.out.println("Es nulo... ");
            this.categorias = this.categoriaFacade.findAll();    
        } 
    }
    
    public void listar() {
       
         try {
            this.categorias = categoriaFacade.findAll();
            System.out.println("Lista categorias" + this.categorias);

        } catch (Exception e) {
            System.out.println("Error---" + e);
        }
        nombreCategoria= null;
    }
}

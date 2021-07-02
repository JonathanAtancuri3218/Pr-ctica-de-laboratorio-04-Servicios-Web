package ec.edu.ups.modelo;

import java.io.Serializable;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Inventario
 *
 */
@Entity

public class Inventario implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codigo;
    private int cantidad;
    
    @ManyToOne
    private Bodega bodega;
    
    @ManyToOne
    private Producto producto;
    
    @Transient
    private boolean editable;

    public Inventario() {
    }

    public Inventario(int cantidad, Bodega bodega, Producto producto) {
        this.cantidad = cantidad;
        this.bodega = bodega;
        this.producto = producto;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Bodega getBodega() {
        return bodega;
    }

    public void setBodega(Bodega bodega) {
        this.bodega = bodega;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }
        
    
    /*
    public void addInventario(Inventario inventario){
        if(!this.inventarios.contains(inventario)){
            this.inventarios.add(inventario);
            inventario.setProducto(this);
        }
    }
    
     public void deleteInventario(Inventario inventario) {
        if (this.inventarios.contains(inventario)) {
            this.inventarios.remove(inventario);
            inventario.setProducto(null);
        }
    }
    */

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.codigo;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Inventario other = (Inventario) obj;
        if (this.codigo != other.codigo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Inventario{" + "codigo=" + codigo + ", cantidad=" + cantidad + ", editable=" + editable + '}';
    }
    
    
    
  
}
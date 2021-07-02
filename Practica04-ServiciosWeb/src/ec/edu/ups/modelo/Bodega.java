package ec.edu.ups.modelo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Bodega
 *
 */
@Entity

public class Bodega implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codigo;
    private String nombre;
    
    @OneToOne(mappedBy = "bodega", cascade = CascadeType.ALL, orphanRemoval = true)
    private Localidad localidad;
    
    
    @OneToMany(mappedBy = "bodega", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Inventario> inventarios;

    @Transient
    private boolean editable;

    public Bodega() {
    }

    public Bodega(String nombre) {
        this.nombre = nombre;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Localidad getLocalidad() {
        return localidad;
    }

    public void setLocalidad(Localidad localidad) {
        this.localidad = localidad;
    }

  
    public List<Inventario> getInventarios() {
        return inventarios;
    }

    public void setInventarios(List<Inventario> inventarios) {
        this.inventarios = inventarios;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + this.codigo;
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
        final Bodega other = (Bodega) obj;
        if (this.codigo != other.codigo) {
            return false;
        }
        return true;
    }

    public void addInventario(Inventario inventario) {
        if (!this.inventarios.contains(inventario)) {
            this.inventarios.add(inventario);
            inventario.setBodega(this);
        }
    }

    public void deleteInventario(Inventario inventario) {
        if (this.inventarios.contains(inventario)) {
            this.inventarios.remove(inventario);
            inventario.setBodega(null);
        }
    }

    @Override
    public String toString() {
        return "Bodega{" + "codigo=" + codigo + ", nombre=" + nombre + '}';
    }

}

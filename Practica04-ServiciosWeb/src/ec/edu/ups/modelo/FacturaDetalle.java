package ec.edu.ups.modelo;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: FacturaDetalle
 *
 */
@Entity

public class FacturaDetalle implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codigo;
    private double subtotal;
    private int cantidad;
    
    @ManyToOne
    private FacturaCabecera facturaCabecera;
    
    @ManyToOne
    private Producto producto;
    

    public FacturaDetalle() {
    }

    public FacturaDetalle(int cantidad, Producto producto) {
        this.cantidad = cantidad;
        this.producto = producto;
    }

    

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public double getSubtotal() {
        this.subtotal = Math.round((this.cantidad*this.producto.getPrecio()) * 100.0) / 100.0;
        return this.subtotal;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public FacturaCabecera getFacturaCabecera() {
        return facturaCabecera;
    }

    public void setFacturaCabecera(FacturaCabecera facturaCabecera) {
        this.facturaCabecera = facturaCabecera;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + this.codigo;
        hash = 43 * hash + Objects.hashCode(this.facturaCabecera);
        hash = 43 * hash + Objects.hashCode(this.producto);
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
        final FacturaDetalle other = (FacturaDetalle) obj;
        if (this.codigo != other.codigo) {
            return false;
        }
        if (!Objects.equals(this.facturaCabecera, other.facturaCabecera)) {
            return false;
        }
        if (!Objects.equals(this.producto, other.producto)) {
            return false;
        }
        return true;
    }

    

    

    @Override
    public String toString() {
        return "FacturaDetalle{" + "codigo=" + codigo + ", subtotal=" + subtotal + ", cantidad=" + cantidad +'}';
    }

    

   
    
    
}
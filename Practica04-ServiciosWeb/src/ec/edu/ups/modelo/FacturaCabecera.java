package ec.edu.ups.modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: FacturaCabecera
 *
 */
@Entity

public class FacturaCabecera implements  Serializable{
    private static final long serialVersionUID = 1;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codigo;
    private LocalDate fecha;
    private double iva;
    private double subTotal;
    private double total;
    private String estado;
    
    @ManyToOne
    private Usuario usuario;
    
    @OneToMany(mappedBy = "facturaCabecera", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FacturaDetalle> facturaDetalles;
    
    @OneToOne
    private Localidad localidad;

    public FacturaCabecera() {
        this.facturaDetalles = new ArrayList<>();
        this.iva = 0.12;
        this.estado = "pendiente";
    }

    public FacturaCabecera(LocalDate fecha,double total, Usuario usuario, List<FacturaDetalle> facturaDetalles) {
        this.fecha = fecha;
        this.total = total;
        this.usuario = usuario;
        this.facturaDetalles = facturaDetalles;
        this.iva = 0.12;
        this.estado = "pendiente";
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public double getIva() {
        return iva;
    }

    public double getTotal() {   
        return this.total;
    }


    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<FacturaDetalle> getFacturaDetalles() {
        return facturaDetalles;
    }

    public void setFacturaDetalles(List<FacturaDetalle> facturaDetalles) {
        this.facturaDetalles = facturaDetalles;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Localidad getLocalidad() {
        return localidad;
    }

    public void setLocalidad(Localidad localidad) {
        this.localidad = localidad;
    }  
    
    public double getSubTotal() {
        return this.subTotal;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.codigo;
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
        final FacturaCabecera other = (FacturaCabecera) obj;
        if (this.codigo != other.codigo) {
            return false;
        }
      
        return true;
    }
    
    public void addFacturaDetalle(FacturaDetalle facturaDetalle) {
        if (!this.facturaDetalles.contains(facturaDetalle)) {
            this.facturaDetalles.add(facturaDetalle);
            facturaDetalle.setFacturaCabecera(this);
        }
    }

    public void deleteFacturaDetalle(FacturaDetalle facturaDetalle) {
        if (this.facturaDetalles.contains(facturaDetalle)) {
            this.facturaDetalles.remove(facturaDetalle);
            facturaDetalle.setFacturaCabecera(null);
        }
    }
    
    public void calcularSubTotal(){
        double sum = 0;
        for(FacturaDetalle fd : this.getFacturaDetalles())
                sum += fd.getSubtotal();
        this.subTotal = sum;
    }
    
    public void calcularTotal(){
        this.total = Math.round(((this.subTotal*this.iva)+this.subTotal) * 100.0) / 100.0;
    }

    @Override
    public String toString() {
        return "FacturaCabecera{" + "codigo=" + codigo + ", fecha=" + fecha + ", iva=" + iva + ", subTotal=" + subTotal + ", total=" + total + ", estado=" + estado + ", facturaDetalles=" + facturaDetalles + '}';
    }

    
    
    
       
}

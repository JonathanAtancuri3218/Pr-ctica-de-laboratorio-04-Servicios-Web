package ec.edu.ups.modelo;

import java.io.Serializable;


import javax.persistence.*;

/**
 * Entity implementation class for Entity: Localidad
 *
 */
@Entity

public class Localidad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codigo;
    private String pais;
    private String provincia;
    private String ciudad;
    private String direccion;
    private String telefono;
    
    @OneToOne
    private Bodega bodega;
    
    @ManyToOne
    private Usuario usuario;
    
    @OneToOne(mappedBy = "localidad", cascade = CascadeType.ALL, orphanRemoval = true)
    private FacturaCabecera facturaCabecera;
    
    @Transient
    private boolean editable;

    public Localidad() {

    }

    public Localidad(String pais, String provincia, String ciudad, String direccion, String telefono) {
        this.pais = pais;
        this.provincia = provincia;
        this.ciudad = ciudad;
        this.direccion = direccion;
        this.telefono = telefono;

    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Bodega getBodega() {
        return bodega;
    }

    public void setBodega(Bodega bodega) {
        this.bodega = bodega;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public FacturaCabecera getFacturaCabecera() {
        return facturaCabecera;
    }

    public void setFacturaCabecera(FacturaCabecera facturaCabecera) {
        this.facturaCabecera = facturaCabecera;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.codigo;
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
        final Localidad other = (Localidad) obj;
        if (this.codigo != other.codigo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Localidad{" + "pais=" + pais + ", provincia=" + provincia + ", ciudad=" + ciudad + ", direccion=" + direccion + '}';
    }

}

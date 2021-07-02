package ec.edu.ups.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Usuario
 *
 */
@Entity

public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String cedula;
    private String nombre;
    private String apellido;
    @Column(unique = true)
    private String correo;
    
    
    private String password;
    
    
    private String rol;

    
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FacturaCabecera> facturas;

    
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Localidad> localidades;
    private boolean cambioPassword;
    private boolean activo;
    
    
    @Transient
    private boolean editable;


    public Usuario() {
        this.rol = "cliente";
        this.password = "12345";
        this.cambioPassword = false;
        this.activo = true;
        this.localidades = new ArrayList<>();
    }

    public Usuario(String cedula, String nombre, String apellido, String correo, String password) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.password = password;
        this.rol = "cliente";
        this.activo = true;
        this.localidades = new ArrayList<>();
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<FacturaCabecera> getFacturas() {
        return facturas;
    }

    public void setFacturas(List<FacturaCabecera> facturas) {
        this.facturas = facturas;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public List<Localidad> getLocalidades() {
        return localidades;
    }

    public void setLocalidades(List<Localidad> localidades) {
        this.localidades = localidades;
    }

    public boolean isCambioPassword() {
        return cambioPassword;
    }

    public void setCambioPassword(boolean cambioPassword) {
        this.cambioPassword = cambioPassword;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.cedula);
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
        final Usuario other = (Usuario) obj;
        if (!Objects.equals(this.cedula, other.cedula)) {
            return false;
        }
        return true;
    }

    public void addLocalidad(Localidad localidad) {
        if (!this.localidades.contains(localidad)) {
            this.localidades.add(localidad);
            localidad.setUsuario(this);
        }
    }

    public void deleteLocalidad(Localidad localidad) {
        if (this.localidades.contains(localidad)) {
            this.localidades.remove(localidad);
            localidad.setUsuario(null);
        }
    }

    @Override
    public String toString() {
        return "Usuario{" + "cedula=" + cedula + ", nombre=" + nombre + ", apellido=" + apellido + ", correo=" + correo + ", password=" + password + ", rol=" + rol + ", editable=" + editable + ", cambioPassword=" + cambioPassword + ", activo=" + activo + '}';
    }
}
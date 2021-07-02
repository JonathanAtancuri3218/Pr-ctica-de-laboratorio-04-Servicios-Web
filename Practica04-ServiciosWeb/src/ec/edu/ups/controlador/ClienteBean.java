package ec.edu.ups.controlador;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.FacesConfig;
import javax.inject.Named;

import ec.edu.ups.ejb.UsuarioFacade;
import ec.edu.ups.modelo.Localidad;
import ec.edu.ups.modelo.Usuario;

@FacesConfig(version = FacesConfig.Version.JSF_2_3)
@Named(value = "clienteBean")
@SessionScoped
public class ClienteBean implements Serializable {

    private static final long serialVersionUID = 1;

    @EJB
    private UsuarioFacade usuarioFacade;

    private String nombre;
    private String apellido;
    private String cedula;
    private String telefono;
    private String correo;
    private String ciudad;
    private String direccion;
    private String pais;
    private String provincia;
    private List<Usuario> usuarios;
    private Usuario usuario;
    private String clienteCedula;
    
    private Localidad localidad;

    public ClienteBean() {

    }

    @PostConstruct
    public void init() {
        try {
            //System.out.println("Lista usuarios" + this.usuarios);
            this.usuarios = usuarioFacade.findClientes();
            clienteCedula = null;
            
        } catch (Exception e) {
            System.out.println("Error---" + e);
        }
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public String getClienteCedula() {
        return clienteCedula;
    }

    public void setClienteCedula(String clienteCedula) {
        this.clienteCedula = clienteCedula;
    }

    public Localidad getLocalidad() {
        return localidad;
    }

    public void setLocalidad(Localidad localidad) {
        this.localidad = localidad;
    }

    
    public String guardarDatos() {
        Usuario usuario = new Usuario();
        usuario.setNombre(this.nombre);
        usuario.setApellido(this.apellido);
        usuario.setCedula(this.cedula);
        usuario.setCorreo(this.correo);

        Localidad localidad = new Localidad(this.pais, this.provincia, this.ciudad, this.direccion, this.telefono);
        //localidad.setTelefono(this.telefono);
        usuario.addLocalidad(localidad);
        localidad.setUsuario(usuario);

       // System.out.println("Usuario: " + usuario.toString());

        usuarioFacade.create(usuario);

        this.usuarios = usuarioFacade.findClientes();
        
        resetValues();
        return null;

    }

    public String delete(Usuario usuario) {
        this.usuarioFacade.remove(usuario);
        this.usuarios = usuarioFacade.findClientes();
        return null;
    }

    public String edit(Usuario usuario) {
        usuario.setEditable(true);
        return null;
    }

    public String save(Usuario usuario) {
        //bodegaFacade.edit(b);
        usuarioFacade.edit(usuario);
        // System.out.println("Guardar Usuario: "+b.getLocalidad().toString());
        usuario.setEditable(false);
        this.usuarios = usuarioFacade.findClientes();
        return null;
    }

    public int totalFactura(Usuario u) {
        return usuarioFacade.contarFacturas(u);

    }

    public void buscarPorCedula() {
        System.out.println("Cedula de cliente  "+clienteCedula);
        if (clienteCedula != null) {
            usuarios = usuarioFacade.findByCedula(clienteCedula);
            System.out.println("Lista de clientes "+usuarios);
        } else {

            usuarios = usuarioFacade.findClientes();
        }
    }
    
    private void resetValues(){
        nombre = "";
        apellido = "";
        cedula = "";
        ciudad = "";
        pais = "";
        provincia = "";
        telefono = "";
        direccion = "";
    }
    
    public void listarClientes(){
        this.usuarios = usuarioFacade.findClientes();
        clienteCedula = null;
    }

}


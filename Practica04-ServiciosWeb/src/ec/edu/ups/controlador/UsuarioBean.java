package ec.edu.ups.controlador;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.FacesConfig;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import ec.edu.ups.ejb.UsuarioFacade;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.utilities.Session;

@FacesConfig(version = FacesConfig.Version.JSF_2_3)
@Named(value = "usuarioBean")
@SessionScoped
public class UsuarioBean implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @EJB
    private UsuarioFacade usuarioFacade;
    private Usuario usuario;
    private String oldPass;
    private String newPass;
    
    
    /**
     * Creates a new instance of UsuarioBean
     */
    public UsuarioBean() {
    }
    
    @PostConstruct
    public void init() {
        try {
            
            usuario = (Usuario) Session.getSessionMap().get("usuario");
            
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

    public String getOldPass() {
        return oldPass;
    }

    public void setOldPass(String oldPass) {
        this.oldPass = oldPass;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }
    
    public void edit(){
        usuarioFacade.edit(usuario);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Datos actualizados", "Se actualizaron sus datos correctamente"));
    }
    
    public void cambiarPass(){
        if(usuario.getPassword().equals(oldPass)){
            usuario.setPassword(newPass);
            usuario.setCambioPassword(true);
            usuarioFacade.edit(usuario);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Datos actualizados", "Se actualizaron sus datos correctamente"));
        }else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La contraseña anterior no es correcta"));
        }
    }
    
}

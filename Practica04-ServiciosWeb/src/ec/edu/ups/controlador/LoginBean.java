package ec.edu.ups.controlador;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.FacesConfig;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import ec.edu.ups.ejb.UsuarioFacade;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.utilities.Session;

@FacesConfig(version = FacesConfig.Version.JSF_2_3)
@Named(value = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private UsuarioFacade usuarioFacade;
    private Usuario usuario;
    private String correo;
    private String password;

    /**
     * Creates a new instance of LoginBean
     */
    public LoginBean() {
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

    public String login() {
    	
        try {
            if (!correo.equals("") && !password.equals("")) {
                usuario = usuarioFacade.finByEmailAndPass(correo, password);
                if (usuario != null) {

                    if (usuario.isActivo()) {
                        //System.out.println("Usuario... " + usuario);

                        HttpSession session = Session.getSession();
                        session.setAttribute("usuario", usuario);

                        switch (usuario.getRol()) {
                            case "admin":
                                //System.out.println("admin");
                                return "/admin/index.xhtml?faces-redirect=true";
                            case "empleado":
                                //ystem.out.println("empleado");
                                return "/empleado/index.xhtml?faces-redirect=true";
                        }
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", "Su cuenta ah sido desactivada contacte con un administrador"));
                    }

                } else{
                    //System.out.println("Usuario no es correcto");
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Credenciales incorrectas"));
                }

                //System.out.println("Pass... " + password);
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Todos los campos son obligatorios"));
            }

        } catch (Exception e) {
            System.out.println("Error: " + e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error Interno", "Error! interno intente de nuevo"));
        }
        return "/public/login.xhtml";
    }

    public String logout() {
        HttpSession session = Session.getSession();
        session.invalidate();
        return "/public/login.xhtml?faces-redirect=true";
    }

    public void checkLogin(String page) {
        FacesContext context = FacesContext.getCurrentInstance();
        Usuario usuarioLogin = (Usuario) Session.getSessionMap().get("usuario");
        try {
            if (usuarioLogin == null && page.equals("index")) {
                context.getExternalContext().redirect("../public/login.xhtml");
            } else if (usuarioLogin != null && page.equals("login")) {
                context.getExternalContext().redirect("../index.xhtml");
            }
        } catch (Exception e) {
        }
    }

    public boolean isLogin() {
        Usuario usuarioLogin = (Usuario) Session.getSessionMap().get("usuario");
        if (usuarioLogin != null) {
            return true;
        } else {
            return false;
        }
    }

    public void isAdmin() {
        FacesContext context = FacesContext.getCurrentInstance();
        Usuario usuarioLogin = (Usuario) Session.getSessionMap().get("usuario");
        try {
            if (usuarioLogin == null) {
                context.getExternalContext().redirect("../public/login.xhtml");
            } else if (usuarioLogin.getRol().equals("empleado")) {
                context.getExternalContext().redirect("../empleado/index.xhtml");
            }
        } catch (Exception e) {
        }
    }

    public void isEmpleado() {
        FacesContext context = FacesContext.getCurrentInstance();
        Usuario usuarioLogin = (Usuario) Session.getSessionMap().get("usuario");
        try {
            if (usuarioLogin == null) {
                context.getExternalContext().redirect("../public/login.xhtml");
            } else if (usuarioLogin.getRol().equals("admin")) {
                context.getExternalContext().redirect("../admin/index.xhtml");
            }
        } catch (Exception e) {
        }
    }
    
    public Usuario userLogin(){
        Usuario userLogin = (Usuario) Session.getSessionMap().get("usuario");
        return usuarioFacade.find(userLogin.getCedula());
    }

}

package ec.edu.ups.controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.FacesConfig;
import javax.inject.Named;

import ec.edu.ups.ejb.FacturaCabeceraFacade;
import ec.edu.ups.ejb.UsuarioFacade;
import ec.edu.ups.modelo.FacturaCabecera;
import ec.edu.ups.modelo.FacturaDetalle;
import ec.edu.ups.modelo.Usuario;

@FacesConfig(version = FacesConfig.Version.JSF_2_3)
@Named(value = "facturasBean")
@SessionScoped
public class FacturasBean implements Serializable {

    private static final long serialVersionUID = 1;

    @EJB
    private UsuarioFacade usuarioFacade;

    @EJB
    private FacturaCabeceraFacade facturaCabeceraFacade;

    private String cedula;
    private List<FacturaCabecera> facturasCabeceras;
    private FacturaCabecera facturaCabecera;
    private List<FacturaDetalle> detalles;
    private Usuario usuario;

    public FacturasBean() {
    }

    @PostConstruct
    public void init() {
        this.facturasCabeceras = facturaCabeceraFacade.findByStatuNotPendiente();
        System.out.println("facturas  .." + this.facturasCabeceras);
        this.detalles = new ArrayList<>();
    }

    public List<FacturaDetalle> getDetalles() {
        this.facturasCabeceras = facturaCabeceraFacade.findByStatuNotPendiente();
        return detalles;
    }

    public void setDetalles(List<FacturaDetalle> detalles) {
        this.detalles = detalles;
    }

    public List<FacturaCabecera> getFacturasCabeceras() {
        
        /*Collections.sort(facturasCabeceras, new Comparator<FacturaCabecera>() {
            @Override
            public int compare(FacturaCabecera o1, FacturaCabecera o2) {
                return o1.getFecha().compareTo(o2.getFecha());
            }
        });*/
        //Collections.sort(facturasCabeceras, Collections.reverseOrder());
        //facturasCabeceras.
        return facturasCabeceras;
    }

    public void setFacturasCabeceras(List<FacturaCabecera> facturasCabeceras) {
        this.facturasCabeceras = facturasCabeceras;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public FacturaCabecera getFacturaCabecera() {
        return facturaCabecera;
    }

    public void setFacturaCabecera(FacturaCabecera facturaCabecera) {
        this.facturaCabecera = facturaCabecera;
    }

    public void buscarUsuario() {
        try {
            
            if (cedula != null || !cedula.equals("")) {
                usuario = usuarioFacade.find(cedula);
                
                this.facturasCabeceras = this.usuario.getFacturas();
                System.out.println("Cedula usuario "+facturasCabeceras);
            } else {
                this.facturasCabeceras = facturaCabeceraFacade.findAll();
            }
        } catch (Exception e) {
        }
    }

    public void listarDetalles(FacturaCabecera factura) {
        this.facturaCabecera = factura;
        this.detalles = factura.getFacturaDetalles();
        System.out.println("Detalles de factura " + this.detalles);
    }

    public void enviarPedido(){
        facturaCabecera.setEstado("enviado");
        facturaCabeceraFacade.edit(facturaCabecera);
    }


}


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
import ec.edu.ups.modelo.FacturaCabecera;
import ec.edu.ups.modelo.FacturaDetalle;

@FacesConfig(version = FacesConfig.Version.JSF_2_3)
@Named(value = "pedidoBean")
@SessionScoped
public class PedidoBean implements Serializable {

    private static final long serialVersionUID = 1;
    @EJB
    private FacturaCabeceraFacade facturaCabeceraFacade;
    private List<FacturaCabecera> facturasCabeceras;
    private FacturaCabecera facturaCabecera;
    private List<FacturaDetalle> detalles;

    /**
     * Creates a new instance of PedidoBean
     */
    public PedidoBean() {
    }

    @PostConstruct
    public void init() {
        facturasCabeceras = facturaCabeceraFacade.findByStatuPendiente();
        this.detalles = new ArrayList<>();
    }

    public List<FacturaCabecera> getFacturasCabeceras() {
        facturasCabeceras = facturaCabeceraFacade.findByStatuPendiente();
        return facturasCabeceras;
    }

    public void setFacturasCabeceras(List<FacturaCabecera> facturasCabeceras) {
        this.facturasCabeceras = facturasCabeceras;
    }

    public FacturaCabecera getFacturaCabecera() {
        return facturaCabecera;
    }

    public void setFacturaCabecera(FacturaCabecera facturaCabecera) {
        this.facturaCabecera = facturaCabecera;
    }

    public List<FacturaDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<FacturaDetalle> detalles) {
        this.detalles = detalles;
    }
    
    public void anularFactura() {
        System.out.println("factura a anular "+facturaCabecera);
        facturaCabecera.setEstado("anulado");
        facturaCabeceraFacade.edit(facturaCabecera);
    }

    public void facturar() {
        facturaCabecera.setEstado("facturado");
        facturaCabeceraFacade.edit(facturaCabecera);
    }
    
    public void listarDetalles(FacturaCabecera factura) {
        System.out.println("Detalles de factura !!!!!!!!!!!!!!!1" + factura);
        this.facturaCabecera = factura;
        this.detalles = factura.getFacturaDetalles();
        
    }

}

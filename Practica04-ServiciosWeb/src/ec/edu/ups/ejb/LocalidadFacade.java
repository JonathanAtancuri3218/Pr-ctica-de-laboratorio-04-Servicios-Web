package ec.edu.ups.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import ec.edu.ups.modelo.Localidad;
import ec.edu.ups.modelo.Usuario;

@Stateless
public class LocalidadFacade extends AbstractFacade<Localidad>{

    @PersistenceContext(unitName = "persistencia")
    private EntityManager em;

    public LocalidadFacade() {
        super(Localidad.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
     public List<Localidad> findByCliente(Usuario usuario){
        String jpql = "FROM l Localidad l INNER JOIN Usuario u ON l.usuario.cedula = u.cedula WHERE u.cedula= "+usuario.getCedula();
        return (List<Localidad>) em.createQuery(jpql).getResultList();
    }
     
       public Localidad findLocalidad(Localidad localidad){
        String jpql = "SELECT l FROM Inventario l"
                + " INNER JOIN Bodega b ON b.codigo = "+localidad.getBodega().getCodigo()
                + " INNER JOIN Usuario u ON u.cedula = "+localidad.getUsuario().getCedula()
                + " WHERE l.bodega.codigo = "+localidad.getBodega().getCodigo()
                + " AND l.usuario.cedula = "+localidad.getUsuario().getCedula();
        
        try {
            return (Localidad) em.createQuery(jpql).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
        
        
             
    }
    
}

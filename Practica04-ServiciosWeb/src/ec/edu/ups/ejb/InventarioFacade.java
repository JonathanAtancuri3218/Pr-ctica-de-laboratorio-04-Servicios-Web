package ec.edu.ups.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import ec.edu.ups.modelo.Inventario;
import ec.edu.ups.modelo.Producto;


@Stateless
public class InventarioFacade extends AbstractFacade<Inventario>{
    
    @PersistenceContext(unitName = "persistencia")
    private EntityManager em;

    public InventarioFacade() {
        super(Inventario.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public List<Inventario> findByProducto(Producto producto){
        String jpql = "FROM Inventario i INNER JOIN Producto p ON i.producto.codigo = p.codigo WHERE p.codigo = "+producto.getCodigo();
        return (List<Inventario>) em.createQuery(jpql).getResultList();
    }
    
    public Inventario findInventario(Inventario inventario){
        String jpql = "SELECT i FROM Inventario i"
                + " INNER JOIN Bodega b ON b.codigo = "+inventario.getBodega().getCodigo()
                + " INNER JOIN Producto p ON p.codigo = "+inventario.getProducto().getCodigo()
                + " WHERE i.bodega.codigo = "+inventario.getBodega().getCodigo()
                + " AND i.producto.codigo = "+inventario.getProducto().getCodigo();
        
        try {
            return (Inventario) em.createQuery(jpql).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
        
        
             
    }
    
}

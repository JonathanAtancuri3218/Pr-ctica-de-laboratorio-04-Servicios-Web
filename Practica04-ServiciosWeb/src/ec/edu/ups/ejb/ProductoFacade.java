package ec.edu.ups.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ec.edu.ups.modelo.Producto;


@Stateless
public class ProductoFacade extends AbstractFacade<Producto> {

    @PersistenceContext(unitName = "persistencia")
    private EntityManager em;

    public ProductoFacade() {
        super(Producto.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public int countAllProducts(int codigo){
        String jpql = "SELECT SUM(i.cantidad) FROM Producto p INNER JOIN Inventario i ON i.producto.codigo = p.codigo WHERE p.codigo = "+codigo;
        Object obj = em.createQuery(jpql).getSingleResult();
        if(obj != null){
            return Integer.valueOf(String.valueOf(obj));
        }else{
            return 0;
        }
    }
    
    public List<Producto> findByBodega(int codigo){
        String jpql = "SELECT p FROM Producto p, Inventario i, Bodega b WHERE i.producto.codigo = p.codigo AND b.codigo = i.bodega.codigo AND b.codigo = "+codigo;
        return (List<Producto>) em.createQuery(jpql).getResultList();
        
    }
    
    public List<Producto> findByName(String name){
        //String jpql = "FROM PRODUCTO p INNER JOIN INVENTARIO i ON i.PRODUCTO_CODIGO = p.CODIGO WHERE p.nombre LIKE '"+name+"%' AND i.CANTIDAD > 0";
        String jpql = "FROM Producto p WHERE LOWER(p.nombre) LIKE LOWER('%" +name+ "%')";
        return (List<Producto>) em.createQuery(jpql).getResultList();
    }

}

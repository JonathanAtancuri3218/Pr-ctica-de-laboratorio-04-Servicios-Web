package ec.edu.ups.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ec.edu.ups.modelo.Bodega;


@Stateless
public class BodegaFacade extends AbstractFacade<Bodega> {

    @PersistenceContext(unitName = "persistencia")
    private EntityManager em;

    public BodegaFacade() {
        super(Bodega.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public int totalProductos(int codigo) {
        String jpql = "SELECT SUM(i.cantidad) FROM Inventario i INNER JOIN Bodega b ON b.codigo = i.bodega.codigo WHERE b.codigo = "+codigo;
        
        //System.out.println("Dato de base... " + em.createQuery(jpql).getSingleResult());
        Object obj = em.createQuery(jpql).getSingleResult();
        if(obj != null){
            return Integer.valueOf(String.valueOf(obj));
        }else{
            return 0;
        }
    }
    
    public List<Bodega> findByName(String name) {
        //System.out.println("llego al metodo de buscar...............................");
        
        //System.out.println("nombre....... " + name.toString());
        String jpql = "FROM Bodega b WHERE b.nombre LIKE '" + name + "%'";
        
        //System.out.println("Lista================================== " +  em.createQuery(jpql).getResultList());
        return (List<Bodega>) em.createQuery(jpql).getResultList();
    }

}
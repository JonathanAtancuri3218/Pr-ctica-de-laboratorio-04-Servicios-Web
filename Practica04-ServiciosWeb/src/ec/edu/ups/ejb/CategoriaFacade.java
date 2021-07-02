package ec.edu.ups.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ec.edu.ups.modelo.Categoria;



@Stateless
public class CategoriaFacade extends AbstractFacade<Categoria> {

    @PersistenceContext(unitName = "persistencia")
    private EntityManager em;

    public CategoriaFacade() {
        super(Categoria.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<Categoria> findByName(String name) {
        System.out.println("llego al metodo de buscar...............................");
        
        System.out.println("nombre....... " + name.toString());
        String jpql = "FROM Categoria c WHERE c.nombre LIKE '" + name + "%'";
        
        System.out.println("Lista================================== " +  em.createQuery(jpql).getResultList());
        return (List<Categoria>) em.createQuery(jpql).getResultList();
    }

}
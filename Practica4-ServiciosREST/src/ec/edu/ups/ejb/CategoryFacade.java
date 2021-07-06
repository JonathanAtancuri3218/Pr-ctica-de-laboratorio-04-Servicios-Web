package ec.edu.ups.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ec.edu.ups.entities.Category;

@Stateless
public class CategoryFacade extends AbstractFacade<Category> {

	@PersistenceContext(unitName = "Practica04-ServiciosREST")
    private EntityManager em;
	
    public CategoryFacade() {
    	super(Category.class);
    }

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

}

package ec.edu.ups.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ec.edu.ups.entities.City;

@Stateless
public class CityFacade extends AbstractFacade<City> {

	@PersistenceContext(unitName = "Practica04-ServiciosREST")
    private EntityManager em;
	
    public CityFacade() {
    	super(City.class);
    }

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

}

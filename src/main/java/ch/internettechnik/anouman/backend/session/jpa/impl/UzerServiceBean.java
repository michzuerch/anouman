package ch.internettechnik.anouman.backend.session.jpa.impl;

import ch.internettechnik.anouman.backend.entity.Uzer;
import ch.internettechnik.anouman.backend.session.jpa.api.UzerService;
import org.jboss.logging.Logger;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.List;

/**
 * Created by michzuerch on 20.03.15.
 */
@Stateful
public class UzerServiceBean implements UzerService {
    private static final Logger LOGGER = Logger.getLogger(UzerServiceBean.class);

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    EntityManager em;

    @Override
    public void saveOrPersist(Uzer uzer) {
        if (uzer.getId() == null) {
            em.persist(uzer);
        } else {
            em.merge(uzer);
        }
    }

    @Override
    public void delete(Uzer uzer) {
        em.remove(em.merge(uzer));
    }

    @Override
    public List<Uzer> findAll() {
        return em.createNamedQuery("Uzer.findAll", Uzer.class).getResultList();
    }

    @Override
    public Uzer findById(Long id) {
        return em.createNamedQuery("Uzer.findById", Uzer.class).setParameter("id", id).getSingleResult();
    }

    @Override
    public Uzer findByName(String name) {
        Uzer u = em.createNamedQuery("Uzer.findByName", Uzer.class).setParameter("name", name).getSingleResult();
        return u;
    }

    /*
    @GET
    @Path("/ping/{hello}")
    public String ping(@PathParam("hello") String hello) {
        System.err.println("Ping " + hello);
        return "ping " + hello;
    }
    */

}

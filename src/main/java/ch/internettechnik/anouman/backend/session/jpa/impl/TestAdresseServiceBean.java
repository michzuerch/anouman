package ch.internettechnik.anouman.backend.session.jpa.impl;

import ch.internettechnik.anouman.backend.entity.TestAdresse;
import ch.internettechnik.anouman.backend.session.jpa.api.TestAdresseService;
import org.jboss.logging.Logger;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.List;

@Stateful
public class TestAdresseServiceBean implements TestAdresseService {
    private static final Logger LOGGER = Logger.getLogger(TestAdresseServiceBean.class);

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    EntityManager em;

    @Override
    public void saveOrPersist(TestAdresse adresse) {
        if (adresse.getId() == null) {
            em.persist(adresse);
        } else {
            em.merge(adresse);
        }
    }

    @Override
    public void delete(TestAdresse adresse) {
        em.remove(em.merge(adresse));
    }

    @Override
    public List<TestAdresse> findAll() {
        return em.createNamedQuery("TestAdresse.findAll", TestAdresse.class).getResultList();
    }

    @Override
    public TestAdresse findById(Long id) {
        return em.createNamedQuery("TestAdresse.findById", TestAdresse.class).setParameter("id", id).getSingleResult();
    }

}

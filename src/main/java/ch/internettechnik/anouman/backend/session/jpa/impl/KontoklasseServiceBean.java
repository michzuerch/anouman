package ch.internettechnik.anouman.backend.session.jpa.impl;

import ch.internettechnik.anouman.backend.entity.Buchhaltung;
import ch.internettechnik.anouman.backend.entity.Kontoklasse;
import ch.internettechnik.anouman.backend.session.jpa.api.KontoklasseService;
import org.jboss.logging.Logger;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.List;

/**
 * Created by michzuerch on 29.08.15.
 */
@Stateful
public class KontoklasseServiceBean implements KontoklasseService {
    private static final Logger LOGGER = Logger.getLogger(KontoklasseServiceBean.class);

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    EntityManager em;


    @Override
    public void saveOrPersist(Kontoklasse kontoklasse) {
        if (kontoklasse.getId() == null) {
            em.persist(kontoklasse);
        } else {
            em.merge(kontoklasse);
        }

    }

    @Override
    public void delete(Kontoklasse kontoklasse) {
        em.remove(em.merge(kontoklasse));

    }

    @Override
    public List<Kontoklasse> findAll() {
        return em.createNamedQuery("Kontoklasse.findAll", Kontoklasse.class).getResultList();
    }

    @Override
    public Kontoklasse findById(Long id) {
        return em.createNamedQuery("Kontoklasse.findById", Kontoklasse.class).setParameter("id", id).getSingleResult();

    }

    @Override
    public Kontoklasse findByBuchhaltung(Buchhaltung value) {
        return em.createNamedQuery("Kontoklasse.findByBuchhaltung", Kontoklasse.class).setParameter("buchhaltungId", value.getId()).getSingleResult();
    }
}

package ch.internettechnik.anouman.backend.session.jpa.impl;

import ch.internettechnik.anouman.backend.entity.Kontogruppe;
import ch.internettechnik.anouman.backend.entity.Kontoklasse;
import ch.internettechnik.anouman.backend.session.jpa.api.KontogruppeService;
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
public class KontogruppeServiceBean implements KontogruppeService {
    private static final Logger LOGGER = Logger.getLogger(KontogruppeServiceBean.class);

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    EntityManager em;

    @Override
    public void saveOrPersist(Kontogruppe kontogruppe) {
        if (kontogruppe.getId() == null) {
            em.persist(kontogruppe);
        } else {
            em.merge(kontogruppe);
        }
    }

    @Override
    public void delete(Kontogruppe kontogruppe) {
        em.remove(em.merge(kontogruppe));

    }

    @Override
    public List<Kontogruppe> findAll() {
        return em.createNamedQuery("Kontogruppe.findAll", Kontogruppe.class).getResultList();
    }

    @Override
    public List<Kontogruppe> findByKontoklasse(Kontoklasse kontoklasse) {
        return em.createNamedQuery("Kontogruppe.findByKontoklasse", Kontogruppe.class).setParameter("kontoklasseId", kontoklasse.getId()).getResultList();

    }

    @Override
    public Kontogruppe findById(Long id) {
        return em.createNamedQuery("Kontogruppe.findById", Kontogruppe.class).setParameter("id", id).getSingleResult();

    }
}

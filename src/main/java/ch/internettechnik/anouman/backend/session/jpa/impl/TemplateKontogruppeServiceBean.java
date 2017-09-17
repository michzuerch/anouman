package ch.internettechnik.anouman.backend.session.jpa.impl;

import ch.internettechnik.anouman.backend.entity.TemplateKontogruppe;
import ch.internettechnik.anouman.backend.entity.TemplateKontoklasse;
import ch.internettechnik.anouman.backend.session.jpa.api.TemplateKontogruppeService;
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
public class TemplateKontogruppeServiceBean implements TemplateKontogruppeService {
    private static final Logger LOGGER = Logger.getLogger(TemplateKontogruppeServiceBean.class);

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    EntityManager em;

    @Override
    public TemplateKontogruppe saveOrPersist(TemplateKontogruppe val) {
        if (val.getId() == null) {
            em.persist(val);
        } else {
            em.merge(val);
        }
        return em.find(TemplateKontogruppe.class, val.getId());
    }

    @Override
    public void delete(TemplateKontogruppe val) {
        em.remove(em.merge(val));

    }

    @Override
    public List<TemplateKontogruppe> findAll() {
        return em.createNamedQuery("TemplateKontogruppe.findAll", TemplateKontogruppe.class).getResultList();
    }

    @Override
    public List<TemplateKontogruppe> findByTemplateKontoklasse(TemplateKontoklasse templateKontoklasse) {
        return em.createNamedQuery("TemplateKontogruppe.findByKontoklasse", TemplateKontogruppe.class).setParameter("kontoklasseId", templateKontoklasse.getId()).getResultList();

    }

    @Override
    public TemplateKontogruppe findById(Long id) {
        return em.createNamedQuery("TemplateKontogruppe.findById", TemplateKontogruppe.class).setParameter("id", id).getSingleResult();

    }
}

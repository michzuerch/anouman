package ch.internettechnik.anouman.backend.session.jpa.impl;

import ch.internettechnik.anouman.backend.entity.TemplateKontoart;
import ch.internettechnik.anouman.backend.entity.TemplateKontogruppe;
import ch.internettechnik.anouman.backend.session.jpa.api.TemplateKontoartService;
import org.jboss.logging.Logger;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by michzuerch on 29.08.15.
 */
@Stateful
public class TemplateKontoartServiceBean implements TemplateKontoartService {
    private static final Logger LOGGER = Logger.getLogger(TemplateKontoartServiceBean.class);

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    EntityManager em;

    @Override
    public TemplateKontoart saveOrPersist(TemplateKontoart val) {
        if (val.getId() == null) {
            em.persist(val);
        } else {
            em.merge(val);
        }
        return em.find(TemplateKontoart.class, val.getId());
    }

    @Override
    public void delete(TemplateKontoart val) {
        em.remove(em.merge(val));

    }

    @Override
    public List<TemplateKontoart> findAll() {
        return em.createNamedQuery("TemplateKontoart.findAll", TemplateKontoart.class).getResultList();
    }

    @Override
    public List<TemplateKontoart> findByCriteria(TemplateKontogruppe kontogruppe) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<TemplateKontoart> query = cb.createQuery(TemplateKontoart.class);
        Root<TemplateKontoart> from = query.from(TemplateKontoart.class);

        query.select(from);

        List<Predicate> predicateList = new ArrayList<Predicate>();

        if (kontogruppe != null) {
            predicateList.add(cb.equal(from.get("templateKontogruppe"), kontogruppe));
        }
        Predicate[] predicates = new Predicate[predicateList.size()];
        predicateList.toArray(predicates);
        query.where(predicates);
        query.distinct(true);

        List<TemplateKontoart> result = em.createQuery(query).getResultList();
        return result;
    }

    @Override
    public TemplateKontoart findById(Long id) {
        return em.createNamedQuery("TemplateKontoart.findById", TemplateKontoart.class).setParameter("id", id).getSingleResult();
    }
}

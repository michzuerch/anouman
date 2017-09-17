package ch.internettechnik.anouman.backend.session.jpa.impl;

import ch.internettechnik.anouman.backend.entity.Kontoart;
import ch.internettechnik.anouman.backend.entity.Kontogruppe;
import ch.internettechnik.anouman.backend.session.jpa.api.KontoartService;
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
public class KontoartServiceBean implements KontoartService {
    private static final Logger LOGGER = Logger.getLogger(KontoartServiceBean.class);

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    EntityManager em;


    @Override
    public void saveOrPersist(Kontoart kontoart) {
        if (kontoart.getId() == null) {
            em.persist(kontoart);
        } else {
            em.merge(kontoart);
        }

    }

    @Override
    public void delete(Kontoart kontoart) {
        em.remove(em.merge(kontoart));

    }

    @Override
    public List<Kontoart> findAll() {
        return em.createNamedQuery("Kontoart.findAll", Kontoart.class).getResultList();
    }

    @Override
    public List<Kontoart> findByCriteria(Kontogruppe kontogruppe) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Kontoart> query = cb.createQuery(Kontoart.class);
        Root<Kontoart> from = query.from(Kontoart.class);

        query.select(from);

        List<Predicate> predicateList = new ArrayList<Predicate>();

        if (kontogruppe != null) {
            predicateList.add(cb.equal(from.get("kontogruppe"), kontogruppe));
        }
        Predicate[] predicates = new Predicate[predicateList.size()];
        predicateList.toArray(predicates);
        query.where(predicates);
        query.distinct(true);

        List<Kontoart> result = em.createQuery(query).getResultList();
        return result;
    }

    @Override
    public Kontoart findById(Long id) {
        return em.createNamedQuery("Kontoart.findById", Kontoart.class).setParameter("id", id).getSingleResult();


    }
}

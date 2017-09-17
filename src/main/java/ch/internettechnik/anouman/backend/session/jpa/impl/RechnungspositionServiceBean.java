package ch.internettechnik.anouman.backend.session.jpa.impl;

import ch.internettechnik.anouman.backend.entity.Rechnung;
import ch.internettechnik.anouman.backend.entity.Rechnungsposition;
import ch.internettechnik.anouman.backend.session.jpa.api.RechnungspositionService;
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
 * Created by michzuerch on 20.03.15.
 */
@Stateful
public class RechnungspositionServiceBean implements RechnungspositionService {
    private static final Logger LOGGER = Logger.getLogger(RechnungspositionServiceBean.class);

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    EntityManager em;

    @Override
    public void saveOrPersist(Rechnungsposition rechnungsposition) {
        if (rechnungsposition.getId() == null) {
            em.persist(rechnungsposition);
        } else {
            em.merge(rechnungsposition);
        }

    }

    @Override
    public void delete(Rechnungsposition rechnungsposition) {
        em.remove(em.merge(rechnungsposition));
    }

    @Override
    public List<Rechnungsposition> findAll() {
        CriteriaQuery<Rechnungsposition> criteriaQuery = em.getCriteriaBuilder().createQuery(Rechnungsposition.class);
        criteriaQuery.select(criteriaQuery.from(Rechnungsposition.class));
        //LOGGER.debug("findAll() size: " + em.createQuery(criteriaQuery).getResultList().size());
        return em.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public Rechnungsposition findById(Long id) {
        return em.createNamedQuery("Rechnungsposition.findById", Rechnungsposition.class).setParameter("id", id).getSingleResult();
    }

    @Override
    public List<Rechnungsposition> findByCriteria(String id, Rechnung rechnung) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Rechnungsposition> query = cb.createQuery(Rechnungsposition.class);
        Root<Rechnungsposition> from = query.from(Rechnungsposition.class);

        query.select(from);

        List<Predicate> predicateList = new ArrayList<Predicate>();

        if (!id.isEmpty()) {
            predicateList.add(cb.equal(from.get("id"), id));
        }

        if (rechnung != null) {
            predicateList.add(cb.equal(from.get("rechnung"), rechnung));
        }
        Predicate[] predicates = new Predicate[predicateList.size()];
        predicateList.toArray(predicates);
        query.where(predicates);
        query.distinct(true);

        List<Rechnungsposition> result = em.createQuery(query).getResultList();
        return result;
    }

    @Override
    public List<Rechnungsposition> findByCriteria(Rechnung rechnung) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Rechnungsposition> query = cb.createQuery(Rechnungsposition.class);
        Root<Rechnungsposition> from = query.from(Rechnungsposition.class);

        query.select(from);

        List<Predicate> predicateList = new ArrayList<Predicate>();

        if (rechnung != null) {
            predicateList.add(cb.equal(from.get("rechnung"), rechnung));
        }
        Predicate[] predicates = new Predicate[predicateList.size()];
        predicateList.toArray(predicates);
        query.where(predicates);
        query.distinct(true);

        List<Rechnungsposition> result = em.createQuery(query).getResultList();
        return result;
    }

    @Override
    public List<Rechnungsposition> findByBezeichnung(String bezeichnung) {
        return em.createNamedQuery("Rechnungsposition.findByBezeichnung", Rechnungsposition.class).setParameter("bezeichnung", bezeichnung + "%").getResultList();
    }


}

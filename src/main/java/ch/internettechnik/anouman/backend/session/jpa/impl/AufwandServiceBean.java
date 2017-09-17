package ch.internettechnik.anouman.backend.session.jpa.impl;

import ch.internettechnik.anouman.backend.entity.Aufwand;
import ch.internettechnik.anouman.backend.entity.Rechnung;
import ch.internettechnik.anouman.backend.session.jpa.api.AufwandService;
import org.jboss.logging.Logger;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Stateful
public class AufwandServiceBean implements AufwandService {
    private static final Logger LOGGER = Logger.getLogger(AufwandServiceBean.class);

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    EntityManager em;

    @Override
    public void saveOrPersist(Aufwand aufwand) {
        if (aufwand.getId() == null) {
            em.persist(aufwand);
        } else {
            em.merge(aufwand);
        }
    }

    @Override
    public void delete(Aufwand aufwand) {
        em.remove(em.merge(aufwand));
    }

    @Override
    public List<Aufwand> findAll() {
        List<Aufwand> list = em.createNamedQuery("Aufwand.findAll", Aufwand.class).getResultList();

        return list;
    }


    @Override
    public List<Aufwand> findByCriteria(String id, Rechnung rechnung) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Aufwand> query = cb.createQuery(Aufwand.class);
        Root<Aufwand> from = query.from(Aufwand.class);

        query.select(from);

        List<Predicate> predicateList = new ArrayList<Predicate>();
        if (!id.isEmpty()) predicateList.add(cb.equal(from.get("id"), id));

        if (rechnung != null) {
            predicateList.add(cb.equal(from.get("rechnung"), rechnung));
        }

        Predicate[] predicates = new Predicate[predicateList.size()];
        predicateList.toArray(predicates);
        query.where(predicates);
        query.distinct(true);

        List<Aufwand> result = em.createQuery(query).getResultList();
        return result;
    }

    @Override
    public List<Aufwand> findByCriteria(Rechnung rechnung) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Aufwand> query = cb.createQuery(Aufwand.class);
        Root<Aufwand> from = query.from(Aufwand.class);

        query.select(from);

        List<Predicate> predicateList = new ArrayList<Predicate>();
        if (rechnung != null) {
            predicateList.add(cb.equal(from.get("rechnung"), rechnung));
        }

        Predicate[] predicates = new Predicate[predicateList.size()];
        predicateList.toArray(predicates);
        query.where(predicates);
        query.distinct(true);

        List<Aufwand> result = em.createQuery(query).getResultList();
        return result;
    }


    @Override
    public List<Aufwand> findByCriteria(Date start, Date ende) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Aufwand> query = cb.createQuery(Aufwand.class);
        Root<Aufwand> from = query.from(Aufwand.class);

        query.select(from);
        query.orderBy(cb.asc(from.get("start")));

        Predicate startPredicate = cb.greaterThanOrEqualTo(from.get("start"), start);
        Path<Date> outServiceDate = from.get("ende");
        Predicate endPredicate = cb.or(cb.isNull(outServiceDate), cb.lessThanOrEqualTo(outServiceDate, ende));
        Predicate finalCondition = cb.and(startPredicate, endPredicate);

        List<Predicate> predicateList = new ArrayList<Predicate>();

        predicateList.add(cb.greaterThanOrEqualTo(from.get("start"), start));


        predicateList.add(startPredicate);
        predicateList.add(endPredicate);
        predicateList.add(finalCondition);
        Predicate[] predicates = new Predicate[predicateList.size()];
        predicateList.toArray(predicates);
        query.where(predicates);
        query.distinct(true);

        List<Aufwand> result = em.createQuery(query).getResultList();
        return result;

    }

    @Override
    public List<Aufwand> findByStartEndDate(Date start, Date ende) {
        Query query = em.createQuery("select a from Aufwand a where a.start >= :start and a.ende <= :ende");
        query.setParameter("start", start);
        query.setParameter("ende", ende);

        return query.getResultList();
    }

    @Override
    public Aufwand findById(Long id) {
        return em.createNamedQuery("Aufwand.findById", Aufwand.class).setParameter("id", id).getSingleResult();
    }

    @Override
    public List<Aufwand> findByTitelLike(String titel) {
        return em.createNamedQuery("Aufwand.findByTitel", Aufwand.class).setParameter("titel", titel + "%").getResultList();
    }
}

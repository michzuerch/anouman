package ch.internettechnik.anouman.backend.session.jpa.impl;

import ch.internettechnik.anouman.backend.entity.Adresse;
import ch.internettechnik.anouman.backend.entity.Rechnung;
import ch.internettechnik.anouman.backend.session.jpa.api.RechnungService;
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
import java.util.Date;
import java.util.List;

/**
 * Created by michzuerch on 22.02.15.
 */
@Stateful
public class RechnungServiceBean implements RechnungService {
    private static final Logger LOGGER = Logger.getLogger(RechnungServiceBean.class);

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    EntityManager em;

    @Override
    public void saveOrPersist(Rechnung rechnung) {
        if (rechnung.getId() == null) {
            em.persist(rechnung);
        } else {
            em.merge(rechnung);
        }

    }

    @Override
    public void delete(Rechnung rechnung) {
        em.remove(em.merge(rechnung));
    }

    @Override
    public List<Rechnung> findAll() {
        return em.createNamedQuery("Rechnung.findAll", Rechnung.class).getResultList();
    }

    @Override
    public List<Rechnung> findByCriteria(String id, String bezeichnung, Adresse adresse, Date startdatum, Date enddatum) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Rechnung> query = cb.createQuery(Rechnung.class);
        Root<Rechnung> from = query.from(Rechnung.class);

        query.select(from);

        List<Predicate> predicateList = new ArrayList<Predicate>();

        if (!id.isEmpty()) {
            predicateList.add(cb.equal(from.get("id"), id));
        }

        if (adresse != null) {
            predicateList.add(cb.equal(from.get("adresse"), adresse));
        }
        /*
        predicateList.add(cb.equal(from.get("bezahlt"), bezahlt));
        predicateList.add(cb.equal(from.get("verschickt"), verschickt));
        */

        if (startdatum != null) {
            predicateList.add(cb.greaterThanOrEqualTo(from.get("rechnungsdatum"), startdatum));
        }

        if (enddatum != null) {
            predicateList.add(cb.lessThanOrEqualTo(from.get("rechnungsdatum"), enddatum));
        }

        Predicate[] predicates = new Predicate[predicateList.size()];
        predicateList.toArray(predicates);
        query.where(predicates);
        query.distinct(true);

        List<Rechnung> result = em.createQuery(query).getResultList();
        return result;
    }

    @Override
    public List<Rechnung> findByCriteriaAdresse(Adresse adresse) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Rechnung> query = cb.createQuery(Rechnung.class);
        Root<Rechnung> from = query.from(Rechnung.class);

        query.select(from);

        List<Predicate> predicateList = new ArrayList<Predicate>();

        if (adresse != null) {
            predicateList.add(cb.equal(from.get("adresse"), adresse));
        }
        Predicate[] predicates = new Predicate[predicateList.size()];
        predicateList.toArray(predicates);
        query.where(predicates);
        query.distinct(true);

        List<Rechnung> result = em.createQuery(query).getResultList();
        return result;
    }

    @Override
    public List<Rechnung> findByCriteriaBezeichnungAdresse(String bezeichnung, Adresse adresse) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Rechnung> query = cb.createQuery(Rechnung.class);
        Root<Rechnung> from = query.from(Rechnung.class);

        query.select(from);

        List<Predicate> predicateList = new ArrayList<Predicate>();

        if (adresse != null) {
            predicateList.add(cb.equal(from.get("adresse"), adresse));
        }

        if (bezeichnung != null) {
            predicateList.add(cb.equal(from.get("bezeichnung"), bezeichnung));
        }

        Predicate[] predicates = new Predicate[predicateList.size()];
        predicateList.toArray(predicates);
        query.where(predicates);
        query.distinct(true);

        List<Rechnung> result = em.createQuery(query).getResultList();
        return result;
    }

    @Override
    public List<Rechnung> findByBezeichnung(String bezeichnung) {
        return em.createNamedQuery("Rechnung.findByBezeichnungLike", Rechnung.class).setParameter("bezeichnung", bezeichnung + "%").getResultList();
    }

    @Override
    public Rechnung findById(Long id) {
        return em.createNamedQuery("Rechnung.findById", Rechnung.class).setParameter("id", id).getSingleResult();
    }

}

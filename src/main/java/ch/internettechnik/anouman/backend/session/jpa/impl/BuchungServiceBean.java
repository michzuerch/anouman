package ch.internettechnik.anouman.backend.session.jpa.impl;

import ch.internettechnik.anouman.backend.entity.Buchhaltung;
import ch.internettechnik.anouman.backend.entity.Buchung;
import ch.internettechnik.anouman.backend.session.jpa.api.BuchungService;
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
 * Created by michzuerch on 07.08.15.
 */
@Stateful
public class BuchungServiceBean implements BuchungService {
    private static final Logger LOGGER = Logger.getLogger(BuchungServiceBean.class);

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    EntityManager em;

    @Override
    public void saveOrPersist(Buchung buchung) {
        if (buchung.getId() == null) {
            em.persist(buchung);
        } else {
            em.merge(buchung);
        }
    }

    @Override
    public void delete(Buchung buchung) {
        em.remove(em.merge(buchung));
    }

    @Override
    public List<Buchung> findAll() {
        return em.createNamedQuery("Buchung.findAll", Buchung.class).getResultList();
    }

    @Override
    public Buchung findById(Long id) {
        return em.createNamedQuery("Buchung.findById", Buchung.class).setParameter("id", id).getSingleResult();
    }

    @Override
    public List<Buchung> findByCriteria(Buchhaltung buchhaltung) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Buchung> query = cb.createQuery(Buchung.class);
        Root<Buchung> from = query.from(Buchung.class);

        query.select(from);

        List<Predicate> predicateList = new ArrayList<Predicate>();

        if (buchhaltung != null) {
            predicateList.add(cb.equal(from.get("buchhaltung"), buchhaltung));
        }
        Predicate[] predicates = new Predicate[predicateList.size()];
        predicateList.toArray(predicates);
        query.where(predicates);
        query.distinct(true);

        List<Buchung> result = em.createQuery(query).getResultList();
        return result;
    }
}

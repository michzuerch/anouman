package ch.internettechnik.anouman.backend.session.jpa.impl;

import ch.internettechnik.anouman.backend.entity.Konto;
import ch.internettechnik.anouman.backend.entity.Kontoart;
import ch.internettechnik.anouman.backend.session.jpa.api.KontoService;
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
public class KontoServiceBean implements KontoService {
    private static final Logger LOGGER = Logger.getLogger(KontoServiceBean.class);

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    EntityManager em;

    @Override
    public void saveOrPersist(Konto konto) {
        if (konto.getId() == null) {
            em.persist(konto);
        } else {
            em.merge(konto);
        }

    }

    @Override
    public void delete(Konto konto) {
        em.remove(em.merge(konto));
    }

    @Override
    public List<Konto> findAll() {
        return em.createNamedQuery("Konto.findAll", Konto.class).getResultList();
    }

    @Override
    public List<Konto> findByCriteria(Kontoart kontoart) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Konto> query = cb.createQuery(Konto.class);
        Root<Konto> from = query.from(Konto.class);

        query.select(from);

        List<Predicate> predicateList = new ArrayList<Predicate>();

        if (kontoart != null) {
            predicateList.add(cb.equal(from.get("kontoart"), kontoart));
        }
        Predicate[] predicates = new Predicate[predicateList.size()];
        predicateList.toArray(predicates);
        query.where(predicates);
        query.distinct(true);

        List<Konto> result = em.createQuery(query).getResultList();
        return result;
    }

    @Override
    public Konto findById(Long id) {
        return em.createNamedQuery("Konto.findById", Konto.class).setParameter("id", id).getSingleResult();
    }


}

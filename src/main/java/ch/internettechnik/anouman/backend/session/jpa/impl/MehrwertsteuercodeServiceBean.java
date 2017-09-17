package ch.internettechnik.anouman.backend.session.jpa.impl;

import ch.internettechnik.anouman.backend.entity.Buchhaltung;
import ch.internettechnik.anouman.backend.entity.Mehrwertsteuercode;
import ch.internettechnik.anouman.backend.session.jpa.api.MehrwertsteuercodeService;
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
public class MehrwertsteuercodeServiceBean implements MehrwertsteuercodeService {
    private static final Logger LOGGER = Logger.getLogger(MehrwertsteuercodeServiceBean.class);

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    EntityManager em;

    @Override
    public void saveOrPersist(Mehrwertsteuercode mehrwertsteuercode) {
        if (mehrwertsteuercode.getId() == null) {
            em.persist(mehrwertsteuercode);
        } else {
            em.merge(mehrwertsteuercode);
        }

    }

    @Override
    public void delete(Mehrwertsteuercode mehrwertsteuercode) {
        em.remove(em.merge(mehrwertsteuercode));
    }

    @Override
    public List<Mehrwertsteuercode> findAll() {
        return em.createNamedQuery("Mehrwertsteuercode.findAll", Mehrwertsteuercode.class).getResultList();
    }

    @Override
    public Mehrwertsteuercode findById(Long id) {
        return em.createNamedQuery("Mehrwertsteuercode.findById", Mehrwertsteuercode.class).setParameter("id", id).getSingleResult();
    }

    @Override
    public List<Mehrwertsteuercode> findByCriteria(Buchhaltung buchhaltung) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Mehrwertsteuercode> query = cb.createQuery(Mehrwertsteuercode.class);
        Root<Mehrwertsteuercode> from = query.from(Mehrwertsteuercode.class);

        query.select(from);

        List<Predicate> predicateList = new ArrayList<Predicate>();

        if (buchhaltung != null) {
            predicateList.add(cb.equal(from.get("buchhaltung"), buchhaltung));
        }
        Predicate[] predicates = new Predicate[predicateList.size()];
        predicateList.toArray(predicates);
        query.where(predicates);
        query.distinct(true);

        List<Mehrwertsteuercode> result = em.createQuery(query).getResultList();
        return result;
    }
}

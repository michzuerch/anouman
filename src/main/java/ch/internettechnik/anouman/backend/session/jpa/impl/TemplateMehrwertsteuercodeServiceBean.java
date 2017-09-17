package ch.internettechnik.anouman.backend.session.jpa.impl;

import ch.internettechnik.anouman.backend.entity.TemplateMehrwertsteuercode;
import ch.internettechnik.anouman.backend.session.jpa.api.TemplateMehrwertsteuercodeService;
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
public class TemplateMehrwertsteuercodeServiceBean implements TemplateMehrwertsteuercodeService {
    private static final Logger LOGGER = Logger.getLogger(TemplateMehrwertsteuercodeServiceBean.class);

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    EntityManager em;

    @Override
    public void saveOrPersist(TemplateMehrwertsteuercode val) {
        if (val.getId() == null) {
            em.persist(val);
        } else {
            em.merge(val);
        }

    }

    @Override
    public void delete(TemplateMehrwertsteuercode val) {
        em.remove(em.merge(val));

    }

    @Override
    public List<TemplateMehrwertsteuercode> findAll() {
        return em.createNamedQuery("TemplateMehrwertsteuercode.findAll", TemplateMehrwertsteuercode.class).getResultList();
    }

    /*
    @Override
    public List<TemplateMehrwertsteuercode> findByCriteriaAdresse(TemplateMehrwertsteuercode val) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<TemplateMehrwertsteuercode> query = cb.createQuery(TemplateMehrwertsteuercode.class);
        Root<TemplateMehrwertsteuercode> from = query.from(TemplateMehrwertsteuercode.class);

        query.select(from);

        List<Predicate> predicateList = new ArrayList<Predicate>();

        if (val != null) {
            predicateList.add(cb.equal(from.get("templateMehrwertsteuercode"), val));
        }
        Predicate[] predicates = new Predicate[predicateList.size()];
        predicateList.toArray(predicates);
        query.where(predicates);
        query.distinct(true);

        List<TemplateMehrwertsteuercode > result = em.createQuery(query).getResultList();
        return result;
    }
    */

    @Override
    public TemplateMehrwertsteuercode findById(Long id) {
        return em.createNamedQuery("TemplateMehrwertsteuercode.findById", TemplateMehrwertsteuercode.class).setParameter("id", id).getSingleResult();
    }
}

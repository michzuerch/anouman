package ch.internettechnik.anouman.backend.session.jpa.impl;

import ch.internettechnik.anouman.backend.entity.TemplateBuchhaltung;
import ch.internettechnik.anouman.backend.session.jpa.api.TemplateBuchhaltungService;
import org.jboss.logging.Logger;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.List;

/**
 * Created by michzuerch on 07.08.15.
 */
@Stateful
public class TemplateBuchhaltungServiceBean implements TemplateBuchhaltungService {
    private static final Logger LOGGER = Logger.getLogger(TemplateBuchhaltungServiceBean.class);

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    EntityManager em;

    @Override
    public void saveOrPersist(TemplateBuchhaltung templateBuchhaltung) {
        if (templateBuchhaltung.getId() == null) {
            em.persist(templateBuchhaltung);
        } else {
            em.merge(templateBuchhaltung);
        }
    }

    @Override
    public void delete(TemplateBuchhaltung templateBuchhaltung) {
        em.remove(em.merge(templateBuchhaltung));
    }

    @Override
    public List<TemplateBuchhaltung> findAll() {
        return em.createNamedQuery("TemplateBuchhaltung.findAll", TemplateBuchhaltung.class).getResultList();
    }

    @Override
    public TemplateBuchhaltung findById(Long id) {
        return em.createNamedQuery("TemplateBuchhaltung.findById", TemplateBuchhaltung.class).setParameter("id", id).getSingleResult();
    }

    /*
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
    */

}

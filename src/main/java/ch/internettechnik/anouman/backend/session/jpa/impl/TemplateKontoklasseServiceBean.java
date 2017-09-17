package ch.internettechnik.anouman.backend.session.jpa.impl;

import ch.internettechnik.anouman.backend.entity.TemplateKontoklasse;
import ch.internettechnik.anouman.backend.session.jpa.api.TemplateKontoklasseService;
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
public class TemplateKontoklasseServiceBean implements TemplateKontoklasseService {
    private static final Logger LOGGER = Logger.getLogger(TemplateKontoklasseServiceBean.class);

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    EntityManager em;

    @Override
    public TemplateKontoklasse saveOrPersist(TemplateKontoklasse val) {
        if (val.getId() == null) {
            em.persist(val);
        } else {
            em.merge(val);
        }
        return em.find(TemplateKontoklasse.class, val.getId());
    }

    @Override
    public void delete(TemplateKontoklasse val) {
        em.remove(em.merge(val));

    }

    @Override
    public List<TemplateKontoklasse> findAll() {
        return em.createNamedQuery("TemplateKontoklasse.findAll", TemplateKontoklasse.class).getResultList();
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
    public TemplateKontoklasse findById(Long id) {
        return em.createNamedQuery("TemplateKontoklasse.findById", TemplateKontoklasse.class).setParameter("id", id).getSingleResult();
    }
}

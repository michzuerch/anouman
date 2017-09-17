package ch.internettechnik.anouman.backend.session.jpa.impl;

import ch.internettechnik.anouman.backend.entity.TemplateKonto;
import ch.internettechnik.anouman.backend.entity.TemplateKontoart;
import ch.internettechnik.anouman.backend.session.jpa.api.TemplateKontoService;
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
public class TemplateKontoServiceBean implements TemplateKontoService {
    private static final Logger LOGGER = Logger.getLogger(TemplateKontoServiceBean.class);

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    EntityManager em;

    @Override
    public TemplateKonto saveOrPersist(TemplateKonto val) {
        if (val.getId() == null) {
            em.persist(val);
        } else {
            em.merge(val);
        }
        return em.find(TemplateKonto.class, val.getId());
    }

    @Override
    public void delete(TemplateKonto val) {
        em.remove(em.merge(val));

    }

    @Override
    public List<TemplateKonto> findAll() {
        return em.createNamedQuery("TemplateKonto.findAll", TemplateKonto.class).getResultList();
    }

    @Override
    public List<TemplateKonto> findByCriteria(TemplateKontoart val) {
        return null;
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
    public TemplateKonto findById(Long id) {
        return em.createNamedQuery("TemplateKonto.findById", TemplateKonto.class).setParameter("id", id).getSingleResult();
    }
}

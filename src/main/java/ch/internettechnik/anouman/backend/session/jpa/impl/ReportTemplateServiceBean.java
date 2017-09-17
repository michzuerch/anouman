package ch.internettechnik.anouman.backend.session.jpa.impl;

import ch.internettechnik.anouman.backend.entity.ReportTemplate;
import ch.internettechnik.anouman.backend.session.jpa.api.ReportTemplateService;
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

@Stateful
public class ReportTemplateServiceBean implements ReportTemplateService {
    private static final Logger LOGGER = Logger.getLogger(ReportTemplateServiceBean.class);

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    EntityManager em;

    @Override
    public void saveOrPersist(ReportTemplate reportTemplate) {
        if (reportTemplate.getId() == null) {
            em.persist(reportTemplate);
        } else {
            em.merge(reportTemplate);
        }

    }

    @Override
    public void delete(ReportTemplate reportTemplate) {
        em.remove(em.merge(reportTemplate));
    }

    @Override
    public List<ReportTemplate> findAll() {
        return em.createNamedQuery("ReportTemplate.findAll", ReportTemplate.class).getResultList();
    }

    @Override
    public List<ReportTemplate> findByCriteria(String id) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ReportTemplate> query = cb.createQuery(ReportTemplate.class);
        Root<ReportTemplate> from = query.from(ReportTemplate.class);

        query.select(from);

        List<Predicate> predicateList = new ArrayList<Predicate>();

        if (!id.isEmpty()) {
            predicateList.add(cb.equal(from.get("id"), id));
        }

        Predicate[] predicates = new Predicate[predicateList.size()];
        predicateList.toArray(predicates);
        query.where(predicates);
        query.distinct(true);

        List<ReportTemplate> result = em.createQuery(query).getResultList();
        return result;
    }

    @Override
    public ReportTemplate findById(Long id) {
        return em.createNamedQuery("ReportTemplate.findById", ReportTemplate.class).setParameter("id", id).getSingleResult();
    }

}

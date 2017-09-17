package ch.internettechnik.anouman.backend.session.jpa.impl;

import ch.internettechnik.anouman.backend.entity.Buchhaltung;
import ch.internettechnik.anouman.backend.entity.Kontoklasse;
import ch.internettechnik.anouman.backend.session.jpa.api.BuchhaltungService;
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
public class BuchhaltungServiceBean implements BuchhaltungService {
    private static final Logger LOGGER = Logger.getLogger(BuchhaltungServiceBean.class);

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    EntityManager em;

    @Override
    public void saveOrPersist(Buchhaltung buchhaltung) {
        if (buchhaltung.getId() == null) {
            em.persist(buchhaltung);
        } else {
            em.merge(buchhaltung);
        }

    }

    @Override
    public void delete(Buchhaltung buchhaltung) {
        em.remove(em.merge(buchhaltung));
    }

    @Override
    public List<Buchhaltung> findAll() {
        return em.createNamedQuery("Buchhaltung.findAll", Buchhaltung.class).getResultList();
    }

    @Override
    public List<Buchhaltung> findByKontoklasse(Kontoklasse val) {
        return null;
    }

    @Override
    public Buchhaltung findById(Long id) {
        return em.createNamedQuery("Buchhaltung.findById", Buchhaltung.class).setParameter("id", id).getSingleResult();
    }


    /*
    @Override
    public List<BackupTemplateBuchhaltung> findByCriteriaAdresse(String id) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<BackupTemplateBuchhaltung> query = cb.createQuery(BackupTemplateBuchhaltung.class);
        Root<BackupTemplateBuchhaltung> from = query.from(BackupTemplateBuchhaltung.class);

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
    */

}

package ch.internettechnik.anouman.backend.session.jpa.impl;

import ch.internettechnik.anouman.backend.entity.Adresse;
import ch.internettechnik.anouman.backend.session.jpa.api.AdresseService;
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
 * Created by michzuerch on 20.03.15.
 */
@Stateful
public class AdresseServiceBean implements AdresseService {
    private static final Logger LOGGER = Logger.getLogger(AdresseServiceBean.class);

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    EntityManager em;

    @Override
    public void saveOrPersist(Adresse adresse) {
        if (adresse.getId() == null) {
            em.persist(adresse);
        } else {
            em.merge(adresse);
        }
    }

    @Override
    public void delete(Adresse adresse) {
        em.remove(em.merge(adresse));
    }

    @Override
    public List<Adresse> findAll() {
        return em.createNamedQuery("Adresse.findAll", Adresse.class).getResultList();
    }

    @Override
    public List<Adresse> findByFirma(String firma) {
        return em.createNamedQuery("Adresse.findByFirma", Adresse.class).setParameter("firma", firma).getResultList();
    }

    @Override
    public List<Adresse> findByFirmaNachname(String firma, String nachname) {
        return em.createNamedQuery("Adresse.findByFirmaNachname", Adresse.class).setParameter("firma", firma).setParameter("nachname", nachname).getResultList();
    }

    @Override
    public List<Adresse> findByFirmaNachnameOrt(String firma, String nachname, String ort) {
        return em.createNamedQuery("Adresse.findByFirmaNachnameOrt", Adresse.class).setParameter("firma", firma).setParameter("nachname", nachname).setParameter("ort", ort).getResultList();
    }

    @Override
    public List<Adresse> findByNachnameOrt(String nachname, String ort) {
        return em.createNamedQuery("Adresse.findByNachnameOrt", Adresse.class).setParameter("nachname", nachname).setParameter("ort", ort).getResultList();
    }

    @Override
    public List<Adresse> findByFirmaOrt(String firma, String ort) {
        return em.createNamedQuery("Adresse.findByFirmaOrt", Adresse.class).setParameter("firma", firma).setParameter("ort", ort).getResultList();
    }

    @Override
    public List<Adresse> findByNachname(String nachname) {
        return em.createNamedQuery("Adresse.findByNachname", Adresse.class).setParameter("nachname", nachname).getResultList();
    }

    @Override
    public List<Adresse> findByOrt(String ort) {
        return em.createNamedQuery("Adresse.findByOrt", Adresse.class).setParameter("ort", ort).getResultList();
    }

    @Override
    public List<Adresse> findByCriteria(String id, String firma, String nachname, String ort) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Adresse> query = cb.createQuery(Adresse.class);
        Root<Adresse> from = query.from(Adresse.class);

        query.select(from);

        List<Predicate> predicateList = new ArrayList<Predicate>();

        if (!id.isEmpty()) {
            predicateList.add(cb.equal(from.get("id"), id));
        }

        if (!firma.isEmpty()) {
            predicateList.add(cb.like(from.get("firma"), firma + "%"));
        }
        if (!nachname.isEmpty()) {
            predicateList.add(cb.like(from.get("nachname"), nachname + "%"));
        }
        if (!ort.isEmpty()) {
            predicateList.add(cb.like(from.get("ort"), ort + "%"));
        }

        Predicate[] predicates = new Predicate[predicateList.size()];
        predicateList.toArray(predicates);
        query.where(predicates);
        query.distinct(true);

        List<Adresse> result = em.createQuery(query).getResultList();
        return result;
    }

    @Override
    public Adresse findById(Long id) {
        return em.createNamedQuery("Adresse.findById", Adresse.class).setParameter("id", id).getSingleResult();
    }

}

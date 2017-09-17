package ch.internettechnik.anouman.backend.session.jpa.impl;

import ch.internettechnik.anouman.backend.entity.EditorTest;
import ch.internettechnik.anouman.backend.session.jpa.api.EditorTestService;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by michzuerch on 09.05.17.
 */

@Stateless
public class EditorTestServiceBean implements EditorTestService {

    @PersistenceContext
    EntityManager em;


    @Override
    public void saveOrPersist(EditorTest editorTest) {
        if (editorTest.getId() == null) {
            em.persist(editorTest);
        } else {
            em.merge(editorTest);
        }

    }

    @Override
    public void delete(EditorTest editorTest) {
        em.remove(em.merge(editorTest));

    }

    @Override
    public List<EditorTest> findAll() {
        return em.createNamedQuery("EditorTest.findAll", EditorTest.class).getResultList();
    }

    @Override
    public EditorTest findById(Long id) {
        return em.createNamedQuery("EditorTest.findById", EditorTest.class).setParameter("id", id).getSingleResult();
    }

    @Override
    public List<EditorTest> findByErsterLike(String erster) {
        return em.createNamedQuery("EditorTest.findByErsterLike", EditorTest.class).setParameter("erster", erster + "%").getResultList();
    }
}

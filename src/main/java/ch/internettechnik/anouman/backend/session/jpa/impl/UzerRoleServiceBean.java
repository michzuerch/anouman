package ch.internettechnik.anouman.backend.session.jpa.impl;

import ch.internettechnik.anouman.backend.entity.UzerRole;
import ch.internettechnik.anouman.backend.session.jpa.api.UzerRoleService;
import org.jboss.logging.Logger;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.List;

/**
 * Created by michzuerch on 20.03.15.
 */
@Stateful
public class UzerRoleServiceBean implements UzerRoleService {
    private static final Logger LOGGER = Logger.getLogger(UzerRoleServiceBean.class);

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    EntityManager em;

    @Override
    public void saveOrPersist(UzerRole uzerRole) {
        if (uzerRole.getId() == null) {
            em.persist(uzerRole);
        } else {
            em.merge(uzerRole);
        }
    }

    @Override
    public void delete(UzerRole uzerRole) {
        em.remove(em.merge(uzerRole));
    }

    @Override
    public List<UzerRole> findAll() {

        return em.createNamedQuery("UzerRole.findAll", UzerRole.class).getResultList();
    }

    @Override
    public UzerRole findById(Long id) {
        return em.createNamedQuery("UzerRole.findById", UzerRole.class).setParameter("id", id).getSingleResult();
    }

    @Override
    public List<UzerRole> findByRole(String role) {
        return em.createNamedQuery("UzerRole.findByRole", UzerRole.class).setParameter("role", role).getResultList();
    }

}

package ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade;

import ch.internettechnik.anouman.backend.entity.UzerRole;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.repository.UzerRoleRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class UzerRoleFacade {
    @Inject
    UzerRoleRepository repo;

    public List<UzerRole> findAll() {
        return repo.findAll();
    }

    public UzerRole findBy(Long id) {
        return repo.findBy(id);
    }

    public void delete(UzerRole val) {
        repo.attachAndRemove(val);
    }

    public UzerRole save(UzerRole val) {
        return repo.save(val);
    }

}

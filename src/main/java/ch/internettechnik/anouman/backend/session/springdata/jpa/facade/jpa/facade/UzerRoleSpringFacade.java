package ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.facade;

import ch.internettechnik.anouman.backend.entity.UzerRole;
import ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.repository.UzerRoleSpringRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class UzerRoleSpringFacade {
    @Inject
    UzerRoleSpringRepository repo;

    public List<UzerRole> findAll() {
        return repo.findAll();
    }

    public UzerRole findBy(Long id) {
        return repo.getOne(id);
    }

    public void delete(UzerRole val) {
        repo.delete(val);
    }

    public UzerRole save(UzerRole val) {
        return repo.save(val);
    }

}

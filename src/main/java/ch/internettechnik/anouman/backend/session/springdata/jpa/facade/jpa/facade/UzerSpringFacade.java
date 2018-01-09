package ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.facade;

import ch.internettechnik.anouman.backend.entity.Uzer;
import ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.repository.UzerSpringRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class UzerSpringFacade {
    @Inject
    UzerSpringRepository repo;

    public List<Uzer> findAll() {
        return repo.findAll();
    }

    public Uzer findBy(Long id) {
        return repo.getOne(id);
    }

    public void delete(Uzer val) {
        repo.delete(val);
    }

    public Uzer save(Uzer val) {
        return repo.save(val);
    }

}

package ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade;

import ch.internettechnik.anouman.backend.entity.Uzer;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.repository.UzerDeltaspikeRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class UzerDeltaspikeFacade {
    @Inject
    UzerDeltaspikeRepository repo;

    public List<Uzer> findAll() {
        return repo.findAll();
    }

    public Uzer findBy(Long id) {
        return repo.findBy(id);
    }

    public void delete(Uzer val) {
        repo.attachAndRemove(val);
    }

    public Uzer save(Uzer val) {
        return repo.save(val);
    }

}

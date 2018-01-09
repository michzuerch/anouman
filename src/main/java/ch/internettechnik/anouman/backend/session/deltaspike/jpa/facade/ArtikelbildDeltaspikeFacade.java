package ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade;

import ch.internettechnik.anouman.backend.entity.Artikelbild;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.repository.ArtikelbildDeltaspikeRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class ArtikelbildDeltaspikeFacade {
    @Inject
    ArtikelbildDeltaspikeRepository repo;

    public Artikelbild findBy(Long id) {
        return repo.findBy(id);
    }

    public void delete(Artikelbild val) {
        repo.attachAndRemove(val);
    }

    public Artikelbild save(Artikelbild val) {
        return repo.save(val);
    }

    public List<Artikelbild> findAll() {
        return repo.findAll();
    }

    public List<Artikelbild> findByTitelLikeIgnoreCase(String titel) {
        return repo.findByTitelLikeIgnoreCase(titel);
    }

}

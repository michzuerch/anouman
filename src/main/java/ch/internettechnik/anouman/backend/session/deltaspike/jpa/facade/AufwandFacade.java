package ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade;

import ch.internettechnik.anouman.backend.entity.Aufwand;
import ch.internettechnik.anouman.backend.entity.Rechnung;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.repository.AufwandRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class AufwandFacade {
    @Inject
    AufwandRepository repo;

    public Aufwand findBy(Long id) {
        return repo.findBy(id);
    }

    public void delete(Aufwand val) {
        repo.attachAndRemove(val);
    }

    public Aufwand save(Aufwand val) {
        return repo.save(val);
    }

    public List<Aufwand> findAll() {
        return repo.findAll();
    }

    public List<Aufwand> findByRechnung(Rechnung rechnung) {
        return repo.findByRechnung(rechnung);
    }

    public List<Aufwand> findByTitelLikeIgnoreCase(String titel) {
        return repo.findByTitelLikeIgnoreCase(titel);
    }

    public List<Aufwand> findByRechnungAndTitelLikeIgnoreCase(Rechnung rechnung, String titel) {
        return repo.findByRechnungAndTitelLikeIgnoreCase(rechnung, titel);
    }
}

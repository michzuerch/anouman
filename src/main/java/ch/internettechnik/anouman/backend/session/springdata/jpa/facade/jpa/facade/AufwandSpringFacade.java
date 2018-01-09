package ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.facade;

import ch.internettechnik.anouman.backend.entity.Aufwand;
import ch.internettechnik.anouman.backend.entity.Rechnung;
import ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.repository.AufwandSpringRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class AufwandSpringFacade {
    @Inject
    AufwandSpringRepository repo;

    public Aufwand findBy(Long id) {
        return repo.getOne(id);
    }

    public void delete(Aufwand val) {
        repo.delete(val);
    }

    public Aufwand save(Aufwand val) {
        return repo.save(val);
    }

    public List<Aufwand> findAll() {
        return repo.findAll();
    }

    public List<Aufwand> findByRechnung(Rechnung rechnung) {
        return repo.getOneRechnung(rechnung);
    }

    public List<Aufwand> findByTitelLikeIgnoreCase(String titel) {
        return repo.getOneTitelLikeIgnoreCase(titel);
    }

    public List<Aufwand> findByRechnungAndTitelLikeIgnoreCase(Rechnung rechnung, String titel) {
        return repo.getOneRechnungAndTitelLikeIgnoreCase(rechnung, titel);
    }
}

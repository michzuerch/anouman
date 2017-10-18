package ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade;

import ch.internettechnik.anouman.backend.entity.Kontohauptgruppe;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.repository.KontohauptgruppeRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class KontohauptgruppeFacade {
    @Inject
    KontohauptgruppeRepository repo;

    public Kontohauptgruppe findBy(Long id) {
        return repo.findBy(id);
    }

    public void delete(Kontohauptgruppe val) {
        repo.attachAndRemove(val);
    }

    public Kontohauptgruppe save(Kontohauptgruppe val) {
        return repo.save(val);
    }

    public List<Kontohauptgruppe> findAll() {
        return repo.findAll();
    }

    public List<Kontohauptgruppe> findByBezeichnungLikeIgnoreCase(String bezeichnung) {
        return repo.findByBezeichnungLikeIgnoreCase(bezeichnung);
    }

}

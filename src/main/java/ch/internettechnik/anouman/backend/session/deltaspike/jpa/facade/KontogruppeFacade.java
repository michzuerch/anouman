package ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade;

import ch.internettechnik.anouman.backend.entity.Kontogruppe;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.repository.KontogruppeRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class KontogruppeFacade {
    @Inject
    KontogruppeRepository repo;

    public Kontogruppe findBy(Long id) {
        return repo.findBy(id);
    }

    public void delete(Kontogruppe val) {
        repo.attachAndRemove(val);
    }

    public Kontogruppe save(Kontogruppe val) {
        return repo.save(val);
    }

    public List<Kontogruppe> findAll() {
        return repo.findAll();
    }

    public List<Kontogruppe> findByBezeichnungLikeIgnoreCase(String bezeichnung) {
        return repo.findByBezeichnungLikeIgnoreCase(bezeichnung);
    }

}

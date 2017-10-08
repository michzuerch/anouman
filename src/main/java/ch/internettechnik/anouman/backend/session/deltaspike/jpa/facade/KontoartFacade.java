package ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade;

import ch.internettechnik.anouman.backend.entity.Kontoart;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.repository.KontoartRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class KontoartFacade {
    @Inject
    KontoartRepository repo;

    public Kontoart findBy(Long id) {
        return repo.findBy(id);
    }

    public void delete(Kontoart val) {
        repo.attachAndRemove(val);
    }

    public Kontoart save(Kontoart val) {
        return repo.save(val);
    }

    public List<Kontoart> findAll() {
        return repo.findAll();
    }

    public List<Kontoart> findByBezeichnungLikeIgnoreCase(String bezeichnung) {
        return repo.findByBezeichnungLikeIgnoreCase(bezeichnung);
    }

}

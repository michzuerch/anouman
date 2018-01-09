package ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade;

import ch.internettechnik.anouman.backend.entity.Rechnung;
import ch.internettechnik.anouman.backend.entity.Rechnungsposition;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.repository.RechnungspositionDeltaspikeRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class RechnungspositionDeltaspikeFacade {
    @Inject
    RechnungspositionDeltaspikeRepository repo;

    public Rechnungsposition findBy(Long id) {
        return repo.findBy(id);
    }

    public void delete(Rechnungsposition val) {
        repo.attachAndRemove(val);
    }

    public Rechnungsposition save(Rechnungsposition val) {
        return repo.save(val);
    }

    public List<Rechnungsposition> findAll() {
        return repo.findAll();
    }

    public List<Rechnungsposition> findByRechnungAndBezeichnungLikeIgnoreCase(Rechnung rechnung, String bezeichnung) {
        return repo.findByRechnungAndBezeichnungLikeIgnoreCase(rechnung, bezeichnung);
    }

    public List<Rechnungsposition> findByRechnung(Rechnung rechnung) {
        return repo.findByRechnung(rechnung);
    }

    public List<Rechnungsposition> findByBezeichnungLikeIgnoreCase(String bezeichnung) {
        return repo.findByBezeichnungLikeIgnoreCase(bezeichnung);
    }
}

package ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.facade;

import ch.internettechnik.anouman.backend.entity.Rechnung;
import ch.internettechnik.anouman.backend.entity.Rechnungsposition;
import ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.repository.RechnungspositionSpringRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class RechnungspositionSpringFacade {
    @Inject
    RechnungspositionSpringRepository repo;

    public Rechnungsposition findBy(Long id) {
        return repo.getOne(id);
    }

    public void delete(Rechnungsposition val) {
        repo.delete(val);
    }

    public Rechnungsposition save(Rechnungsposition val) {
        return repo.save(val);
    }

    public List<Rechnungsposition> findAll() {
        return repo.findAll();
    }

    public List<Rechnungsposition> findByRechnungAndBezeichnungLikeIgnoreCase(Rechnung rechnung, String bezeichnung) {
        return repo.getOneRechnungAndBezeichnungLikeIgnoreCase(rechnung, bezeichnung);
    }

    public List<Rechnungsposition> findByRechnung(Rechnung rechnung) {
        return repo.getOneRechnung(rechnung);
    }

    public List<Rechnungsposition> findByBezeichnungLikeIgnoreCase(String bezeichnung) {
        return repo.getOneBezeichnungLikeIgnoreCase(bezeichnung);
    }
}

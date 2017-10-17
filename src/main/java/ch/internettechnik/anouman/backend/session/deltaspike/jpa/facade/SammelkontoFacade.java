package ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade;

import ch.internettechnik.anouman.backend.entity.Sammelkonto;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.repository.SammelkontoRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class SammelkontoFacade {
    @Inject
    SammelkontoRepository repo;

    public Sammelkonto findBy(Long id) {
        return repo.findBy(id);
    }

    public void delete(Sammelkonto val) {
        repo.attachAndRemove(val);
    }

    public Sammelkonto save(Sammelkonto val) {
        return repo.save(val);
    }

    public List<Sammelkonto> findAll() {
        return repo.findAll();
    }

    public List<Sammelkonto> findByBezeichnungLikeIgnoreCase(String bezeichnung) {
        return repo.findByBezeichnungLikeIgnoreCase(bezeichnung);
    }

}

package ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade;

import ch.internettechnik.anouman.backend.entity.TemplateSammelkonto;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.repository.TemplateSammelkontoRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class TemplateSammelkontoFacade {
    @Inject
    TemplateSammelkontoRepository repo;

    public TemplateSammelkonto findBy(Long id) {
        return repo.findBy(id);
    }

    public void delete(TemplateSammelkonto val) {
        repo.attachAndRemove(val);
    }

    public TemplateSammelkonto save(TemplateSammelkonto val) {
        return repo.save(val);
    }

    public List<TemplateSammelkonto> findAll() {
        return repo.findAll();
    }

    public List<TemplateSammelkonto> findByBezeichnungLikeIgnoreCase(String bezeichnung) {
        return repo.findByBezeichnungLikeIgnoreCase(bezeichnung);
    }

}

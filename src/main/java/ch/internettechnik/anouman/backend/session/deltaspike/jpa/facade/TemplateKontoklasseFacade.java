package ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade;

import ch.internettechnik.anouman.backend.entity.TemplateKontoklasse;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.repository.TemplateKontoklasseRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class TemplateKontoklasseFacade {
    @Inject
    TemplateKontoklasseRepository repo;

    public TemplateKontoklasse findBy(Long id) {
        return repo.findBy(id);
    }

    public void delete(TemplateKontoklasse val) {
        repo.attachAndRemove(val);
    }

    public TemplateKontoklasse save(TemplateKontoklasse val) {
        return repo.save(val);
    }

    public List<TemplateKontoklasse> findAll() {
        return repo.findAll();
    }

    public List<TemplateKontoklasse> findByBezeichnungLikeIgnoreCase(String bezeichnung) {
        return repo.findByBezeichnungLikeIgnoreCase(bezeichnung);
    }

}

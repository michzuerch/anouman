package ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade;

import ch.internettechnik.anouman.backend.entity.TemplateKontoart;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.repository.TemplateKontoartRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class TemplateKontoartFacade {
    @Inject
    TemplateKontoartRepository repo;

    public TemplateKontoart findBy(Long id) {
        return repo.findBy(id);
    }

    public void delete(TemplateKontoart val) {
        repo.attachAndRemove(val);
    }

    public TemplateKontoart save(TemplateKontoart val) {
        return repo.save(val);
    }

    public List<TemplateKontoart> findAll() {
        return repo.findAll();
    }

    public List<TemplateKontoart> findByBezeichnungLikeIgnoreCase(String bezeichnung) {
        return repo.findByBezeichnungLikeIgnoreCase(bezeichnung);
    }

}

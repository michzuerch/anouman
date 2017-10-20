package ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade;

import ch.internettechnik.anouman.backend.entity.TemplateKontogruppe;
import ch.internettechnik.anouman.backend.entity.TemplateKontohauptgruppe;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.repository.TemplateKontogruppeRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class TemplateKontogruppeFacade {
    @Inject
    TemplateKontogruppeRepository repo;

    public TemplateKontogruppe findBy(Long id) {
        return repo.findBy(id);
    }

    public void delete(TemplateKontogruppe val) {
        repo.attachAndRemove(val);
    }

    public TemplateKontogruppe save(TemplateKontogruppe val) {
        return repo.save(val);
    }

    public List<TemplateKontogruppe> findAll() {
        return repo.findAll();
    }

    public List<TemplateKontogruppe> findByBezeichnungLikeIgnoreCase(String bezeichnung) {
        return repo.findByBezeichnungLikeIgnoreCase(bezeichnung);
    }

    public List<TemplateKontogruppe> findByTemplateKontohauptgruppe(TemplateKontohauptgruppe templateKontohauptgruppe) {
        return repo.findByTemplateKontohauptgruppe(templateKontohauptgruppe);
    }

}

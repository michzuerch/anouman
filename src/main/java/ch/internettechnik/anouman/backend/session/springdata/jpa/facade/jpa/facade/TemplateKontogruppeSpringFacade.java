package ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.facade;

import ch.internettechnik.anouman.backend.entity.TemplateKontogruppe;
import ch.internettechnik.anouman.backend.entity.TemplateKontohauptgruppe;
import ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.repository.TemplateKontogruppeSpringRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class TemplateKontogruppeSpringFacade {
    @Inject
    TemplateKontogruppeSpringRepository repo;

    public TemplateKontogruppe findBy(Long id) {
        return repo.getOne(id);
    }

    public void delete(TemplateKontogruppe val) {
        repo.delete(val);
    }

    public TemplateKontogruppe save(TemplateKontogruppe val) {
        return repo.save(val);
    }

    public List<TemplateKontogruppe> findAll() {
        return repo.findAll();
    }

    public List<TemplateKontogruppe> findByBezeichnungLikeIgnoreCase(String bezeichnung) {
        return repo.getOneBezeichnungLikeIgnoreCase(bezeichnung);
    }

    public List<TemplateKontogruppe> findByTemplateKontohauptgruppe(TemplateKontohauptgruppe templateKontohauptgruppe) {
        return repo.getOneTemplateKontohauptgruppe(templateKontohauptgruppe);
    }

}

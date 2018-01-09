package ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.facade;

import ch.internettechnik.anouman.backend.entity.TemplateKontohauptgruppe;
import ch.internettechnik.anouman.backend.entity.TemplateKontoklasse;
import ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.repository.TemplateKontohauptgruppeSpringRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class TemplateKontohauptgruppeSpringFacade {
    @Inject
    TemplateKontohauptgruppeSpringRepository repo;

    public TemplateKontohauptgruppe findBy(Long id) {
        return repo.getOne(id);
    }

    public void delete(TemplateKontohauptgruppe val) {
        repo.delete(val);
    }

    public TemplateKontohauptgruppe save(TemplateKontohauptgruppe val) {
        return repo.save(val);
    }

    public List<TemplateKontohauptgruppe> findAll() {
        return repo.findAll();
    }

    public List<TemplateKontohauptgruppe> findByBezeichnungLikeIgnoreCase(String bezeichnung) {
        return repo.getOneBezeichnungLikeIgnoreCase(bezeichnung);
    }

    public List<TemplateKontohauptgruppe> findByTemplateKontoklasse(TemplateKontoklasse templateKontoklasse) {
        return repo.getOneTemplateKontoklasse(templateKontoklasse);
    }

}

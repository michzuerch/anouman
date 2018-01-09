package ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.facade;

import ch.internettechnik.anouman.backend.entity.TemplateBuchhaltung;
import ch.internettechnik.anouman.backend.entity.TemplateKontoklasse;
import ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.repository.TemplateKontoklasseSpringRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class TemplateKontoklasseSpringFacade {
    @Inject
    TemplateKontoklasseSpringRepository repo;

    public TemplateKontoklasse findBy(Long id) {
        return repo.getOne(id);
    }

    public void delete(TemplateKontoklasse val) {
        repo.delete(val);
    }

    public TemplateKontoklasse save(TemplateKontoklasse val) {
        return repo.save(val);
    }

    public List<TemplateKontoklasse> findAll() {
        return repo.findAll();
    }

    public List<TemplateKontoklasse> findByBezeichnungLikeIgnoreCase(String bezeichnung) {
        return repo.getOneBezeichnungLikeIgnoreCase(bezeichnung);
    }

    public List<TemplateKontoklasse> findByTemplateBuchhaltung(TemplateBuchhaltung templateBuchhaltung) {
        return repo.getOneTemplateBuchhaltung(templateBuchhaltung);
    }
}

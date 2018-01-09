package ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade;

import ch.internettechnik.anouman.backend.entity.TemplateBuchhaltung;
import ch.internettechnik.anouman.backend.entity.TemplateKontoklasse;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.repository.TemplateKontoklasseDeltaspikeRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class TemplateKontoklasseDeltaspikeFacade {
    @Inject
    TemplateKontoklasseDeltaspikeRepository repo;

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

    public List<TemplateKontoklasse> findByTemplateBuchhaltung(TemplateBuchhaltung templateBuchhaltung) {
        return repo.findByTemplateBuchhaltung(templateBuchhaltung);
    }
}

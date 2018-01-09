package ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade;

import ch.internettechnik.anouman.backend.entity.TemplateKonto;
import ch.internettechnik.anouman.backend.entity.TemplateKontogruppe;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.repository.TemplateKontoDeltaspikeRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class TemplateKontoDeltaspikeFacade {
    @Inject
    TemplateKontoDeltaspikeRepository repo;

    public TemplateKonto findBy(Long id) {
        return repo.findBy(id);
    }

    public void delete(TemplateKonto val) {
        repo.attachAndRemove(val);
    }

    public TemplateKonto save(TemplateKonto val) {
        return repo.save(val);
    }

    public List<TemplateKonto> findAll() {
        return repo.findAll();
    }

    public List<TemplateKonto> findByBezeichnungLikeIgnoreCase(String bezeichnung) {
        return repo.findByBezeichnungLikeIgnoreCase(bezeichnung);
    }

    public List<TemplateKonto> findByTemplateKontogruppe(TemplateKontogruppe templateKontogruppe) {
        return repo.findByTemplateKontogruppe(templateKontogruppe);
    }
}

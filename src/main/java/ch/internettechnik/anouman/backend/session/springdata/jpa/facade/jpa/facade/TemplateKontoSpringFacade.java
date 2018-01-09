package ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.facade;

import ch.internettechnik.anouman.backend.entity.TemplateKonto;
import ch.internettechnik.anouman.backend.entity.TemplateKontogruppe;
import ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.repository.TemplateKontoSpringRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class TemplateKontoSpringFacade {
    @Inject
    TemplateKontoSpringRepository repo;

    public TemplateKonto findBy(Long id) {
        return repo.getOne(id);
    }

    public void delete(TemplateKonto val) {
        repo.delete(val);
    }

    public TemplateKonto save(TemplateKonto val) {
        return repo.save(val);
    }

    public List<TemplateKonto> findAll() {
        return repo.findAll();
    }

    public List<TemplateKonto> findByBezeichnungLikeIgnoreCase(String bezeichnung) {
        return repo.getOneBezeichnungLikeIgnoreCase(bezeichnung);
    }

    public List<TemplateKonto> findByTemplateKontogruppe(TemplateKontogruppe templateKontogruppe) {
        return repo.getOneTemplateKontogruppe(templateKontogruppe);
    }
}

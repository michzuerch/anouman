package ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.facade;

import ch.internettechnik.anouman.backend.entity.TemplateBuchhaltung;
import ch.internettechnik.anouman.backend.entity.TemplateKonto;
import ch.internettechnik.anouman.backend.entity.TemplateMehrwertsteuercode;
import ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.repository.TemplateMehrwertsteuercodeSpringRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class TemplateMehrwertsteuercodeSpringFacade {
    @Inject
    TemplateMehrwertsteuercodeSpringRepository repo;

    public TemplateMehrwertsteuercode findBy(Long id) {
        return repo.getOne(id);
    }

    public void delete(TemplateMehrwertsteuercode val) {
        repo.delete(val);
    }

    public TemplateMehrwertsteuercode save(TemplateMehrwertsteuercode val) {
        return repo.save(val);
    }

    public List<TemplateMehrwertsteuercode> findAll() {
        return repo.findAll();
    }

    public List<TemplateMehrwertsteuercode> findByTemplateBuchhaltung(TemplateBuchhaltung templateBuchhaltung) {
        return repo.getOneTemplateBuchhaltung(templateBuchhaltung);
    }

    public List<TemplateMehrwertsteuercode> findByBezeichnungLikeIgnoreCase(String bezeichnung) {
        return repo.getOneBezeichnungLikeIgnoreCase(bezeichnung);
    }

    public TemplateMehrwertsteuercode findByTemplateKonto(TemplateKonto templateKonto) {
        return repo.getOneTemplateKonto(templateKonto);
    }


}

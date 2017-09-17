package ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade;

import ch.internettechnik.anouman.backend.entity.ReportTemplate;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.repository.ReportTemplateRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class ReportTemplateFacade {
    @Inject
    ReportTemplateRepository repo;

    public List<ReportTemplate> findAll() {
        return repo.findAll();
    }

    public ReportTemplate findBy(Long id) {
        return repo.findBy(id);
    }

    public void delete(ReportTemplate val) {
        repo.attachAndRemove(val);
    }

    public ReportTemplate save(ReportTemplate val) {
        return repo.save(val);
    }

    public List<ReportTemplate> findByBezeichnungLikeIgnoreCase(String bezeichnung) {
        return repo.findByBezeichnungLikeIgnoreCase(bezeichnung);
    }
}

package ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade;

import ch.internettechnik.anouman.backend.entity.report.fop.ReportFOPXsl;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.repository.ReportFOPXslRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class ReportFOPXslFacade {
    @Inject
    ReportFOPXslRepository repo;

    public ReportFOPXsl findBy(Long id) {
        return repo.findBy(id);
    }

    public void delete(ReportFOPXsl val) {
        repo.attachAndRemove(val);
    }

    public ReportFOPXsl save(ReportFOPXsl val) {
        return repo.save(val);
    }

    public List<ReportFOPXsl> findAll() {
        return repo.findAll();
    }

    public List<ReportFOPXsl> findByBezeichnungLikeIgnoreCase(String bezeichnung) {
        return repo.findByBezeichnungLikeIgnoreCase(bezeichnung);
    }

}

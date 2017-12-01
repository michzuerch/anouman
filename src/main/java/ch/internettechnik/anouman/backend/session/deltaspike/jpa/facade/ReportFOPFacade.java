package ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade;

import ch.internettechnik.anouman.backend.entity.report.fop.ReportFOP;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.repository.ReportFOPRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class ReportFOPFacade {
    @Inject
    ReportFOPRepository repo;

    public ReportFOP findBy(Long id) {
        return repo.findBy(id);
    }

    public void delete(ReportFOP val) {
        repo.attachAndRemove(val);
    }

    public ReportFOP save(ReportFOP val) {
        return repo.save(val);
    }

    public List<ReportFOP> findAll() {
        return repo.findAll();
    }

    public List<ReportFOP> findByBezeichnungLikeIgnoreCase(String bezeichnung) {
        return repo.findByBezeichnungLikeIgnoreCase(bezeichnung);
    }

}

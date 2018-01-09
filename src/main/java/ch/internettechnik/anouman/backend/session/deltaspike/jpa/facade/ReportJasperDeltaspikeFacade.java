package ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade;

import ch.internettechnik.anouman.backend.entity.report.jasper.ReportJasper;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.repository.ReportJasperDeltaspikeRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class ReportJasperDeltaspikeFacade {
    @Inject
    ReportJasperDeltaspikeRepository repo;

    public List<ReportJasper> findAll() {
        return repo.findAll();
    }

    public ReportJasper findBy(Long id) {
        return repo.findBy(id);
    }

    public void delete(ReportJasper val) {
        repo.attachAndRemove(val);
    }

    public ReportJasper save(ReportJasper val) {
        return repo.save(val);
    }

    public List<ReportJasper> findByBezeichnungLikeIgnoreCase(String bezeichnung) {
        return repo.findByBezeichnungLikeIgnoreCase(bezeichnung);
    }
}

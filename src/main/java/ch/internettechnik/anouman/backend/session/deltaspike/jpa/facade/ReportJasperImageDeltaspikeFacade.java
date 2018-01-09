package ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade;

import ch.internettechnik.anouman.backend.entity.report.jasper.ReportJasperImage;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.repository.ReportJasperImageDeltaspikeRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class ReportJasperImageDeltaspikeFacade {
    @Inject
    ReportJasperImageDeltaspikeRepository repo;

    public List<ReportJasperImage> findAll() {
        return repo.findAll();
    }

    public ReportJasperImage findBy(Long id) {
        return repo.findBy(id);
    }

    public void delete(ReportJasperImage val) {
        repo.attachAndRemove(val);
    }

    public ReportJasperImage save(ReportJasperImage val) {
        return repo.save(val);
    }

    public List<ReportJasperImage> findByBezeichnungLikeIgnoreCase(String bezeichnung) {
        return repo.findByBezeichnungLikeIgnoreCase(bezeichnung);
    }
}

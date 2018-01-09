package ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.facade;

import ch.internettechnik.anouman.backend.entity.report.jasper.ReportJasperImage;
import ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.repository.ReportJasperImageSpringRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class ReportJasperImageSpringFacade {
    @Inject
    ReportJasperImageSpringRepository repo;

    public List<ReportJasperImage> findAll() {
        return repo.findAll();
    }

    public ReportJasperImage findBy(Long id) {
        return repo.getOne(id);
    }

    public void delete(ReportJasperImage val) {
        repo.delete(val);
    }

    public ReportJasperImage save(ReportJasperImage val) {
        return repo.save(val);
    }

    public List<ReportJasperImage> findByBezeichnungLikeIgnoreCase(String bezeichnung) {
        return repo.getOneBezeichnungLikeIgnoreCase(bezeichnung);
    }
}

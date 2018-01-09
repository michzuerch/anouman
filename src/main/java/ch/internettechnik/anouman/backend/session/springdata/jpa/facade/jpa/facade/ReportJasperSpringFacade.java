package ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.facade;

import ch.internettechnik.anouman.backend.entity.report.jasper.ReportJasper;
import ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.repository.ReportJasperSpringRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class ReportJasperSpringFacade {
    @Inject
    ReportJasperSpringRepository repo;

    public List<ReportJasper> findAll() {
        return repo.findAll();
    }

    public ReportJasper findBy(Long id) {
        return repo.getOne(id);
    }

    public void delete(ReportJasper val) {
        repo.delete(val);
    }

    public ReportJasper save(ReportJasper val) {
        return repo.save(val);
    }

    public List<ReportJasper> findByBezeichnungLikeIgnoreCase(String bezeichnung) {
        return repo.getOneBezeichnungLikeIgnoreCase(bezeichnung);
    }
}

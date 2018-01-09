package ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.facade;

import ch.internettechnik.anouman.backend.entity.report.fop.ReportFOP;
import ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.repository.ReportFOPSpringRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class ReportFOPSpringFacade {
    @Inject
    ReportFOPSpringRepository repo;

    public ReportFOP findBy(Long id) {
        return repo.getOne(id);
    }

    public void delete(ReportFOP val) {
        repo.delete(val);
    }

    public ReportFOP save(ReportFOP val) {
        return repo.save(val);
    }

    public List<ReportFOP> findAll() {
        return repo.findAll();
    }

    public List<ReportFOP> findByBezeichnungLikeIgnoreCase(String bezeichnung) {
        return repo.getOneBezeichnungLikeIgnoreCase(bezeichnung);
    }

}

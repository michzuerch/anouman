package ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.facade;

import ch.internettechnik.anouman.backend.entity.report.fop.ReportFOPImage;
import ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.repository.ReportFOPImageSpringRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class ReportFOPImageSpringFacade {
    @Inject
    ReportFOPImageSpringRepository repo;

    public List<ReportFOPImage> findAll() {
        return repo.findAll();
    }

    public ReportFOPImage findBy(Long id) {
        return repo.getOne(id);
    }

    public void delete(ReportFOPImage val) {
        repo.delete(val);
    }

    public ReportFOPImage save(ReportFOPImage val) {
        return repo.save(val);
    }

    public List<ReportFOPImage> findByBezeichnungLikeIgnoreCase(String bezeichnung) {
        return repo.getOneBezeichnungLikeIgnoreCase(bezeichnung);
    }
}

package ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade;

import ch.internettechnik.anouman.backend.entity.report.fop.ReportFOPImage;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.repository.ReportFOPImageRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class ReportFOPImageFacade {
    @Inject
    ReportFOPImageRepository repo;

    public List<ReportFOPImage> findAll() {
        return repo.findAll();
    }

    public ReportFOPImage findBy(Long id) {
        return repo.findBy(id);
    }

    public void delete(ReportFOPImage val) {
        repo.attachAndRemove(val);
    }

    public ReportFOPImage save(ReportFOPImage val) {
        return repo.save(val);
    }

    public List<ReportFOPImage> findByBezeichnungLikeIgnoreCase(String bezeichnung) {
        return repo.findByBezeichnungLikeIgnoreCase(bezeichnung);
    }
}

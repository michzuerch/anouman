package ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.facade;

import ch.internettechnik.anouman.backend.entity.report.css.ReportCSS;
import ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.repository.ReportCSSSpringRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class ReportCSSSpringFacade {
    @Inject
    ReportCSSSpringRepository repo;

    public ReportCSS findBy(Long id) {
        return repo.getOne(id);
    }

    public void delete(ReportCSS val) {
        repo.delete(val);
    }

    public ReportCSS save(ReportCSS val) {
        return repo.save(val);
    }

    public List<ReportCSS> findAll() {
        return repo.findAll();
    }

    public List<ReportCSS> findByBezeichnungLikeIgnoreCase(String bezeichnung) {
        return repo.getOneBezeichnungLikeIgnoreCase(bezeichnung);
    }

}

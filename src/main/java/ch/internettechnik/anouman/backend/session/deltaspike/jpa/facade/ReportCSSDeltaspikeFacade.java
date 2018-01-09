package ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade;

import ch.internettechnik.anouman.backend.entity.report.css.ReportCSS;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.repository.ReportCSSDeltaspikeRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class ReportCSSDeltaspikeFacade {
    @Inject
    ReportCSSDeltaspikeRepository repo;

    public ReportCSS findBy(Long id) {
        return repo.findBy(id);
    }

    public void delete(ReportCSS val) {
        repo.attachAndRemove(val);
    }

    public ReportCSS save(ReportCSS val) {
        return repo.save(val);
    }

    public List<ReportCSS> findAll() {
        return repo.findAll();
    }

    public List<ReportCSS> findByBezeichnungLikeIgnoreCase(String bezeichnung) {
        return repo.findByBezeichnungLikeIgnoreCase(bezeichnung);
    }

}

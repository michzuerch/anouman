package com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.facade;


import com.gmail.michzuerch.anouman.backend.entity.report.css.ReportCSSImage;
import com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.repository.ReportCSSImageDeltaspikeRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class ReportCSSImageDeltaspikeFacade {
    @Inject
    ReportCSSImageDeltaspikeRepository repo;

    public List<ReportCSSImage> findAll() {
        return repo.findAll();
    }

    public ReportCSSImage findBy(Long id) {
        return repo.findBy(id);
    }

    public void delete(ReportCSSImage val) {
        repo.attachAndRemove(val);
    }

    public ReportCSSImage save(ReportCSSImage val) {
        return repo.save(val);
    }

    public List<ReportCSSImage> findByBezeichnungLikeIgnoreCase(String bezeichnung) {
        return repo.findByBezeichnungLikeIgnoreCase(bezeichnung);
    }
}

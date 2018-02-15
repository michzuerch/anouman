package com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.repository;

import com.gmail.michzuerch.anouman.backend.entity.report.css.ReportCSS;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@Repository(forEntity = ReportCSS.class)
public interface ReportCSSDeltaspikeRepository extends EntityRepository<ReportCSS, Long> {
    List<ReportCSS> findByBezeichnungLikeIgnoreCase(String bezeichnung);

}

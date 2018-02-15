package com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.repository;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@Repository(forEntity = com.gmail.michzuerch.anouman.backend.entity.report.fop.ReportFOP.class)
public interface ReportFOPDeltaspikeRepository extends EntityRepository<com.gmail.michzuerch.anouman.backend.entity.report.fop.ReportFOP, Long> {
    List<com.gmail.michzuerch.anouman.backend.entity.report.fop.ReportFOP> findByBezeichnungLikeIgnoreCase(String bezeichnung);
}

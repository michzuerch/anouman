package com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.repository;

import com.gmail.michzuerch.anouman.backend.entity.report.jasper.ReportJasperImage;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@Repository(forEntity = ReportJasperImage.class)
public interface ReportJasperImageDeltaspikeRepository extends EntityRepository<ReportJasperImage, Long> {
    List<ReportJasperImage> findByBezeichnungLikeIgnoreCase(String bezeichnung);
}

package ch.internettechnik.anouman.backend.session.deltaspike.jpa.repository;

import ch.internettechnik.anouman.backend.entity.report.jasper.ReportJasper;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@Repository(forEntity = ReportJasper.class)
public interface ReportJasperDeltaspikeRepository extends EntityRepository<ReportJasper, Long> {
    List<ReportJasper> findByBezeichnungLikeIgnoreCase(String bezeichnung);
}

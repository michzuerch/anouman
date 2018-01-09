package ch.internettechnik.anouman.backend.session.deltaspike.jpa.repository;

import ch.internettechnik.anouman.backend.entity.report.fop.ReportFOP;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@Repository(forEntity = ReportFOP.class)
public interface ReportFOPDeltaspikeRepository extends EntityRepository<ReportFOP, Long> {
    List<ReportFOP> findByBezeichnungLikeIgnoreCase(String bezeichnung);
}

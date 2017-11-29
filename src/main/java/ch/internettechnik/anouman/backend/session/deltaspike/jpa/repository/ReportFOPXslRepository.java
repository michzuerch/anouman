package ch.internettechnik.anouman.backend.session.deltaspike.jpa.repository;

import ch.internettechnik.anouman.backend.entity.report.fop.ReportFOPXsl;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@Repository(forEntity = ReportFOPXsl.class)
public interface ReportFOPXslRepository extends EntityRepository<ReportFOPXsl, Long> {
    List<ReportFOPXsl> findByBezeichnungLikeIgnoreCase(String bezeichnung);
}

package ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.repository;

import ch.internettechnik.anouman.backend.entity.report.fop.ReportFOP;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportFOPSpringRepository extends JpaRepository<ReportFOP, Long> {
    List<ReportFOP> findByBezeichnungLikeIgnoreCase(String bezeichnung);
}

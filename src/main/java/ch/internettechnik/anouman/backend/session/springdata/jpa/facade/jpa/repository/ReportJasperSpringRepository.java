package ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.repository;

import ch.internettechnik.anouman.backend.entity.report.jasper.ReportJasper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportJasperSpringRepository extends JpaRepository<ReportJasper, Long> {
    List<ReportJasper> findByBezeichnungLikeIgnoreCase(String bezeichnung);
}

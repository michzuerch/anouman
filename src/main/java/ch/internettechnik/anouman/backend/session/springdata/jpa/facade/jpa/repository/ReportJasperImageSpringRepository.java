package ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.repository;

import ch.internettechnik.anouman.backend.entity.report.jasper.ReportJasperImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportJasperImageSpringRepository extends JpaRepository<ReportJasperImage, Long> {
    List<ReportJasperImage> findByBezeichnungLikeIgnoreCase(String bezeichnung);
}

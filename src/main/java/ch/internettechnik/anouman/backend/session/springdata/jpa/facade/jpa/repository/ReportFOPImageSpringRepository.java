package ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.repository;

import ch.internettechnik.anouman.backend.entity.report.fop.ReportFOPImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportFOPImageSpringRepository extends JpaRepository<ReportFOPImage, Long> {
    List<ReportFOPImage> findByBezeichnungLikeIgnoreCase(String bezeichnung);
}

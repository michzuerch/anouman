package ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.repository;

import ch.internettechnik.anouman.backend.entity.report.css.ReportCSS;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportCSSSpringRepository extends JpaRepository<ReportCSS, Long> {
    List<ReportCSS> findByBezeichnungLikeIgnoreCase(String bezeichnung);

}

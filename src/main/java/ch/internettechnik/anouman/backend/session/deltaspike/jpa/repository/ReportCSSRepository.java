package ch.internettechnik.anouman.backend.session.deltaspike.jpa.repository;

import ch.internettechnik.anouman.backend.entity.report.css.ReportCSS;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@Repository(forEntity = ReportCSS.class)
public interface ReportCSSRepository extends EntityRepository<ReportCSS, Long> {
    List<ReportCSS> findByBezeichnungLikeIgnoreCase(String bezeichnung);

}

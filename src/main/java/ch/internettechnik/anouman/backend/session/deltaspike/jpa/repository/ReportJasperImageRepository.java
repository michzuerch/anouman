package ch.internettechnik.anouman.backend.session.deltaspike.jpa.repository;

import ch.internettechnik.anouman.backend.entity.report.jasper.ReportJasperImage;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@Repository(forEntity = ReportJasperImage.class)
public interface ReportJasperImageRepository extends EntityRepository<ReportJasperImage, Long> {
    List<ReportJasperImage> findByBezeichnungLikeIgnoreCase(String bezeichnung);
}

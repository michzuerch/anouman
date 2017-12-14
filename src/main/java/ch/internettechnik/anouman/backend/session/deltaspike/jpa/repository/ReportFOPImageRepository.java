package ch.internettechnik.anouman.backend.session.deltaspike.jpa.repository;

import ch.internettechnik.anouman.backend.entity.report.fop.ReportFOPImage;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@Repository(forEntity = ReportFOPImage.class)
public interface ReportFOPImageRepository extends EntityRepository<ReportFOPImage, Long> {
    List<ReportFOPImage> findByBezeichnungLikeIgnoreCase(String bezeichnung);
}

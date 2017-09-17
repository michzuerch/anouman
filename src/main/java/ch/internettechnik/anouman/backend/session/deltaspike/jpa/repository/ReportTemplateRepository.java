package ch.internettechnik.anouman.backend.session.deltaspike.jpa.repository;

import ch.internettechnik.anouman.backend.entity.ReportTemplate;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@Repository(forEntity = ReportTemplate.class)
public interface ReportTemplateRepository extends EntityRepository<ReportTemplate, Long> {
    List<ReportTemplate> findByBezeichnungLikeIgnoreCase(String bezeichnung);
}

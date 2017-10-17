package ch.internettechnik.anouman.backend.session.deltaspike.jpa.repository;


import ch.internettechnik.anouman.backend.entity.TemplateSammelkonto;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@Repository(forEntity = TemplateSammelkonto.class)
public interface TemplateSammelkontoRepository extends EntityRepository<TemplateSammelkonto, Long> {
    List<TemplateSammelkonto> findByBezeichnungLikeIgnoreCase(String bezeichnung);
}

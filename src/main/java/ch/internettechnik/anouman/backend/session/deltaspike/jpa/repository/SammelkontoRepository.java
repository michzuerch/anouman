package ch.internettechnik.anouman.backend.session.deltaspike.jpa.repository;


import ch.internettechnik.anouman.backend.entity.Sammelkonto;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@Repository(forEntity = Sammelkonto.class)
public interface SammelkontoRepository extends EntityRepository<Sammelkonto, Long> {
    List<Sammelkonto> findByBezeichnungLikeIgnoreCase(String bezeichnung);
}

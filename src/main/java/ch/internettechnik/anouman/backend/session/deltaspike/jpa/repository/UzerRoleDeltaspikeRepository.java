package ch.internettechnik.anouman.backend.session.deltaspike.jpa.repository;

import ch.internettechnik.anouman.backend.entity.UzerRole;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = UzerRole.class)
public interface UzerRoleDeltaspikeRepository extends EntityRepository<UzerRole, Long> {
}

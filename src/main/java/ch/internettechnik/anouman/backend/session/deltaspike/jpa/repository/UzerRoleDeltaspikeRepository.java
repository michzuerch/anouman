package ch.internettechnik.anouman.backend.session.deltaspike.jpa.repository;

import ch.internettechnik.anouman.backend.entity.Uzer;
import ch.internettechnik.anouman.backend.entity.UzerRole;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@Repository(forEntity = UzerRole.class)
public interface UzerRoleDeltaspikeRepository extends EntityRepository<UzerRole, Long> {
    List<UzerRole> findByUzerAndRoleLikeIgnoreCase(Uzer uzer, String role);

    List<UzerRole> findByUzer(Uzer uzer);

    List<UzerRole> findByRoleLikeIgnoreCase(String role);
}

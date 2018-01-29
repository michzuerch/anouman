package ch.internettechnik.anouman.backend.session.deltaspike.jpa.repository;

import ch.internettechnik.anouman.backend.entity.Uzer;
import ch.internettechnik.anouman.backend.entity.UzerRole;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@Repository(forEntity = Uzer.class)
public interface UzerDeltaspikeRepository extends EntityRepository<Uzer, Long> {
    List<Uzer> findByUzerRoleAndPrincipalLikeIgnoreCase(UzerRole uzerRole, String principal);

    List<Uzer> findByUzerRole(UzerRole uzerRole);

    List<Uzer> findByPrincipalLikeIgnoreCase(String principal);
}

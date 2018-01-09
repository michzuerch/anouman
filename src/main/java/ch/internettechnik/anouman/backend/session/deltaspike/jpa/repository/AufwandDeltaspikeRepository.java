package ch.internettechnik.anouman.backend.session.deltaspike.jpa.repository;

import ch.internettechnik.anouman.backend.entity.Aufwand;
import ch.internettechnik.anouman.backend.entity.Rechnung;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@Repository(forEntity = Aufwand.class)
public interface AufwandDeltaspikeRepository extends EntityRepository<Aufwand, Long> {
    List<Aufwand> findByRechnung(Rechnung rechnung);

    List<Aufwand> findByRechnungAndTitelLikeIgnoreCase(Rechnung rechnung, String titel);

    List<Aufwand> findByTitelLikeIgnoreCase(String titel);

}

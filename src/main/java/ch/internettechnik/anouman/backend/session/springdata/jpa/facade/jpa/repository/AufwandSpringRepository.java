package ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.repository;

import ch.internettechnik.anouman.backend.entity.Aufwand;
import ch.internettechnik.anouman.backend.entity.Rechnung;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AufwandSpringRepository extends JpaRepository<Aufwand, Long> {
    List<Aufwand> findByRechnung(Rechnung rechnung);

    List<Aufwand> findByRechnungAndTitelLikeIgnoreCase(Rechnung rechnung, String titel);

    List<Aufwand> findByTitelLikeIgnoreCase(String titel);

}

package ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.repository;


import ch.internettechnik.anouman.backend.entity.Kontogruppe;
import ch.internettechnik.anouman.backend.entity.Kontohauptgruppe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KontogruppeSpringRepository extends JpaRepository<Kontogruppe, Long> {
    List<Kontogruppe> findByBezeichnungLikeIgnoreCase(String bezeichnung);

    List<Kontogruppe> findByKontohauptgruppe(Kontohauptgruppe kontohauptgruppe);
}

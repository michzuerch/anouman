package ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.repository;


import ch.internettechnik.anouman.backend.entity.Kontohauptgruppe;
import ch.internettechnik.anouman.backend.entity.Kontoklasse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KontohauptgruppeSpringRepository extends JpaRepository<Kontohauptgruppe, Long> {
    List<Kontohauptgruppe> findByBezeichnungLikeIgnoreCase(String bezeichnung);

    List<Kontohauptgruppe> findByKontoklasse(Kontoklasse kontoklasse);
}

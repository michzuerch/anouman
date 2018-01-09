package ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.repository;


import ch.internettechnik.anouman.backend.entity.Buchhaltung;
import ch.internettechnik.anouman.backend.entity.Kontoklasse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KontoklasseSpringRepository extends JpaRepository<Kontoklasse, Long> {
    List<Kontoklasse> findByBezeichnungLikeIgnoreCase(String bezeichnung);

    List<Kontoklasse> findByBuchhaltung(Buchhaltung buchhaltung);
}

package ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.repository;


import ch.internettechnik.anouman.backend.entity.Konto;
import ch.internettechnik.anouman.backend.entity.Kontogruppe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KontoSpringRepository extends JpaRepository<Konto, Long> {
    List<Konto> findByBezeichnungLikeIgnoreCase(String bezeichnung);

    List<Konto> findByKontogruppe(Kontogruppe kontogruppe);
}

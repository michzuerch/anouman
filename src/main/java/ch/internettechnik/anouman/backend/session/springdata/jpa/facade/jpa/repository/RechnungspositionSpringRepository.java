package ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.repository;

import ch.internettechnik.anouman.backend.entity.Rechnung;
import ch.internettechnik.anouman.backend.entity.Rechnungsposition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RechnungspositionSpringRepository extends JpaRepository<Rechnungsposition, Long> {
    List<Rechnungsposition> findByRechnung(Rechnung rechnung);

    List<Rechnungsposition> findByBezeichnungLikeIgnoreCase(String bezeichnung);

    List<Rechnungsposition> findByRechnungAndBezeichnungLikeIgnoreCase(Rechnung rechnung, String bezeichnung);

}

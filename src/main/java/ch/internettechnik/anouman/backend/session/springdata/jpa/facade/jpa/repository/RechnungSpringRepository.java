package ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.repository;

import ch.internettechnik.anouman.backend.entity.Adresse;
import ch.internettechnik.anouman.backend.entity.Rechnung;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RechnungSpringRepository extends JpaRepository<Rechnung, Long> {
    List<Rechnung> findByAdresse(Adresse adresse);

    List<Rechnung> findByBezeichnungLikeIgnoreCase(String bezeichnung);

    List<Rechnung> findByAdresseAndBezeichnungLikeIgnoreCase(Adresse adresse, String bezeichnung);
}

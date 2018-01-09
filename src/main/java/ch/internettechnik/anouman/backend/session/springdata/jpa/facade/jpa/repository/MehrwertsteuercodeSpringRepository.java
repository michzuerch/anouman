package ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.repository;


import ch.internettechnik.anouman.backend.entity.Buchhaltung;
import ch.internettechnik.anouman.backend.entity.Mehrwertsteuercode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MehrwertsteuercodeSpringRepository extends JpaRepository<Mehrwertsteuercode, Long> {
    List<Mehrwertsteuercode> findByBuchhaltung(Buchhaltung buchhaltung);

    List<Mehrwertsteuercode> findByBezeichnungLikeIgnoreCase(String bezeichnung);
}

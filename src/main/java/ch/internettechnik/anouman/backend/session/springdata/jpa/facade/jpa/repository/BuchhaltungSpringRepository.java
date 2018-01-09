package ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.repository;


import ch.internettechnik.anouman.backend.entity.Buchhaltung;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BuchhaltungSpringRepository extends JpaRepository<Buchhaltung, Long> {
    List<Buchhaltung> findByBezeichnungLikeIgnoreCase(String bezeichnung);
}

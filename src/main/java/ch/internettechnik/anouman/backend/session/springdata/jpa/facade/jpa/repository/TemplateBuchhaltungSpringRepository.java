package ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.repository;


import ch.internettechnik.anouman.backend.entity.TemplateBuchhaltung;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TemplateBuchhaltungSpringRepository extends JpaRepository<TemplateBuchhaltung, Long> {
    List<TemplateBuchhaltung> findByBezeichnungLikeIgnoreCase(String bezeichnung);
}

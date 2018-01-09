package ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.repository;


import ch.internettechnik.anouman.backend.entity.TemplateBuchhaltung;
import ch.internettechnik.anouman.backend.entity.TemplateKontoklasse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TemplateKontoklasseSpringRepository extends JpaRepository<TemplateKontoklasse, Long> {
    List<TemplateKontoklasse> findByBezeichnungLikeIgnoreCase(String bezeichnung);

    List<TemplateKontoklasse> findByTemplateBuchhaltung(TemplateBuchhaltung templateBuchhaltung);
}

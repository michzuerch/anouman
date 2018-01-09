package ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.repository;


import ch.internettechnik.anouman.backend.entity.TemplateBuchhaltung;
import ch.internettechnik.anouman.backend.entity.TemplateKonto;
import ch.internettechnik.anouman.backend.entity.TemplateMehrwertsteuercode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TemplateMehrwertsteuercodeSpringRepository extends JpaRepository<TemplateMehrwertsteuercode, Long> {
    List<TemplateMehrwertsteuercode> findByTemplateBuchhaltung(TemplateBuchhaltung templateBuchhaltung);

    List<TemplateMehrwertsteuercode> findByBezeichnungLikeIgnoreCase(String bezeichnung);

    TemplateMehrwertsteuercode findByTemplateKonto(TemplateKonto templateKonto);
}

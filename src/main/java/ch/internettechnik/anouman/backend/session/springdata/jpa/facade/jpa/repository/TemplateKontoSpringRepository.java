package ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.repository;


import ch.internettechnik.anouman.backend.entity.TemplateBuchhaltung;
import ch.internettechnik.anouman.backend.entity.TemplateKonto;
import ch.internettechnik.anouman.backend.entity.TemplateKontogruppe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TemplateKontoSpringRepository extends JpaRepository<TemplateKonto, Long> {
    List<TemplateKonto> findByBezeichnungLikeIgnoreCase(String bezeichnung);

    List<TemplateKonto> findByTemplateBuchhaltung(TemplateBuchhaltung templateBuchhaltung);

    List<TemplateKonto> findByTemplateKontogruppe(TemplateKontogruppe templateKontogruppe);
}

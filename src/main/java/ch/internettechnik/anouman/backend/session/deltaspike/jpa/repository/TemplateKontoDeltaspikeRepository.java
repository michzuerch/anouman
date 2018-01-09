package ch.internettechnik.anouman.backend.session.deltaspike.jpa.repository;


import ch.internettechnik.anouman.backend.entity.TemplateBuchhaltung;
import ch.internettechnik.anouman.backend.entity.TemplateKonto;
import ch.internettechnik.anouman.backend.entity.TemplateKontogruppe;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@Repository(forEntity = TemplateKonto.class)
public interface TemplateKontoDeltaspikeRepository extends EntityRepository<TemplateKonto, Long> {
    List<TemplateKonto> findByBezeichnungLikeIgnoreCase(String bezeichnung);

    List<TemplateKonto> findByTemplateBuchhaltung(TemplateBuchhaltung templateBuchhaltung);

    List<TemplateKonto> findByTemplateKontogruppe(TemplateKontogruppe templateKontogruppe);
}

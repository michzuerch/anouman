package ch.internettechnik.anouman.backend.session.deltaspike.jpa.repository;


import ch.internettechnik.anouman.backend.entity.TemplateBuchhaltung;
import ch.internettechnik.anouman.backend.entity.TemplateMehrwertsteuercode;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@Repository(forEntity = TemplateMehrwertsteuercode.class)
public interface TemplateMehrwertsteuercodeRepository extends EntityRepository<TemplateMehrwertsteuercode, Long> {
    List<TemplateMehrwertsteuercode> findByTemplateBuchhaltung(TemplateBuchhaltung templateBuchhaltung);
    List<TemplateMehrwertsteuercode> findByBezeichnungLikeIgnoreCase(String bezeichnung);
}

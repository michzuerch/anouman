package ch.internettechnik.anouman.backend.session.deltaspike.jpa.repository;


import ch.internettechnik.anouman.backend.entity.TemplateBuchhaltung;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@Repository(forEntity = TemplateBuchhaltung.class)
public interface TemplateBuchhaltungRepository extends EntityRepository<TemplateBuchhaltung, Long> {
    List<TemplateBuchhaltung> findByBezeichnungLikeIgnoreCase(String bezeichnung);
}

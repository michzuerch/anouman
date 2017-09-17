package ch.internettechnik.anouman.backend.session.deltaspike.jpa.repository;


import ch.internettechnik.anouman.backend.entity.TemplateKontoart;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@Repository(forEntity = TemplateKontoart.class)
public interface TemplateKontoartRepository extends EntityRepository<TemplateKontoart, Long> {
    List<TemplateKontoart> findByBezeichnungLikeIgnoreCase(String bezeichnung);
}

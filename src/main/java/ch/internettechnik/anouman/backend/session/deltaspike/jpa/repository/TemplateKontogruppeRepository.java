package ch.internettechnik.anouman.backend.session.deltaspike.jpa.repository;


import ch.internettechnik.anouman.backend.entity.TemplateKontogruppe;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@Repository(forEntity = TemplateKontogruppe.class)
public interface TemplateKontogruppeRepository extends EntityRepository<TemplateKontogruppe, Long> {
    List<TemplateKontogruppe> findByBezeichnungLikeIgnoreCase(String bezeichnung);
}

package ch.internettechnik.anouman.backend.session.deltaspike.jpa.repository;


import ch.internettechnik.anouman.backend.entity.TemplateKontohauptgruppe;
import ch.internettechnik.anouman.backend.entity.TemplateKontoklasse;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@Repository(forEntity = TemplateKontohauptgruppe.class)
public interface TemplateKontohauptgruppeRepository extends EntityRepository<TemplateKontohauptgruppe, Long> {
    List<TemplateKontohauptgruppe> findByBezeichnungLikeIgnoreCase(String bezeichnung);

    List<TemplateKontohauptgruppe> findByTemplateKontoklasse(TemplateKontoklasse templateKontoklasse);
}

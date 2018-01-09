package ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.repository;


import ch.internettechnik.anouman.backend.entity.TemplateKontohauptgruppe;
import ch.internettechnik.anouman.backend.entity.TemplateKontoklasse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TemplateKontohauptgruppeSpringRepository extends JpaRepository<TemplateKontohauptgruppe, Long> {
    List<TemplateKontohauptgruppe> findByBezeichnungLikeIgnoreCase(String bezeichnung);

    List<TemplateKontohauptgruppe> findByTemplateKontoklasse(TemplateKontoklasse templateKontoklasse);
}

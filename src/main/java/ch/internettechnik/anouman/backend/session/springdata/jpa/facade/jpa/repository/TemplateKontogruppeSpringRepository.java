package ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.repository;


import ch.internettechnik.anouman.backend.entity.TemplateKontogruppe;
import ch.internettechnik.anouman.backend.entity.TemplateKontohauptgruppe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TemplateKontogruppeSpringRepository extends JpaRepository<TemplateKontogruppe, Long> {
    List<TemplateKontogruppe> findByBezeichnungLikeIgnoreCase(String bezeichnung);

    List<TemplateKontogruppe> findByTemplateKontohauptgruppe(TemplateKontohauptgruppe templateKontohauptgruppe);

}

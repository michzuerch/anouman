package ch.internettechnik.anouman.backend.session.deltaspike.jpa.repository;


import ch.internettechnik.anouman.backend.entity.Kontohauptgruppe;
import ch.internettechnik.anouman.backend.entity.Kontoklasse;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@Repository(forEntity = Kontohauptgruppe.class)
public interface KontohauptgruppeRepository extends EntityRepository<Kontohauptgruppe, Long> {
    List<Kontohauptgruppe> findByBezeichnungLikeIgnoreCase(String bezeichnung);

    List<Kontohauptgruppe> findByKontoklasse(Kontoklasse kontoklasse);
}

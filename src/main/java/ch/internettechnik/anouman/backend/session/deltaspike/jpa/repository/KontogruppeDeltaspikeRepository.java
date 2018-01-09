package ch.internettechnik.anouman.backend.session.deltaspike.jpa.repository;


import ch.internettechnik.anouman.backend.entity.Kontogruppe;
import ch.internettechnik.anouman.backend.entity.Kontohauptgruppe;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@Repository(forEntity = Kontogruppe.class)
public interface KontogruppeDeltaspikeRepository extends EntityRepository<Kontogruppe, Long> {
    List<Kontogruppe> findByBezeichnungLikeIgnoreCase(String bezeichnung);

    List<Kontogruppe> findByKontohauptgruppe(Kontohauptgruppe kontohauptgruppe);
}

package ch.internettechnik.anouman.backend.session.deltaspike.jpa.repository;


import ch.internettechnik.anouman.backend.entity.Konto;
import ch.internettechnik.anouman.backend.entity.Kontogruppe;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@Repository(forEntity = Konto.class)
public interface KontoRepository extends EntityRepository<Konto, Long> {
    List<Konto> findByBezeichnungLikeIgnoreCase(String bezeichnung);

    List<Konto> findByKontogruppe(Kontogruppe kontogruppe);
}

package ch.internettechnik.anouman.backend.session.deltaspike.jpa.repository;

import ch.internettechnik.anouman.backend.entity.Adresse;
import ch.internettechnik.anouman.backend.entity.Rechnung;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@Repository(forEntity = Rechnung.class)
public interface RechnungRepository extends EntityRepository<Rechnung, Long> {
    List<Rechnung> findByAdresse(Adresse adresse);

    List<Rechnung> findByBezeichnungLikeIgnoreCase(String bezeichnung);

    List<Rechnung> findByAdresseAndBezeichnungLikeIgnoreCase(Adresse adresse, String bezeichnung);
}

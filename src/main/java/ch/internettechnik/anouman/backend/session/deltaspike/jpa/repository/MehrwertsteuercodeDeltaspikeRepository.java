package ch.internettechnik.anouman.backend.session.deltaspike.jpa.repository;


import ch.internettechnik.anouman.backend.entity.Buchhaltung;
import ch.internettechnik.anouman.backend.entity.Mehrwertsteuercode;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@Repository(forEntity = Mehrwertsteuercode.class)
public interface MehrwertsteuercodeDeltaspikeRepository extends EntityRepository<Mehrwertsteuercode, Long> {
    List<Mehrwertsteuercode> findByBuchhaltung(Buchhaltung buchhaltung);

    List<Mehrwertsteuercode> findByBezeichnungLikeIgnoreCase(String bezeichnung);
}

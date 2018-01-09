package ch.internettechnik.anouman.backend.session.deltaspike.jpa.repository;


import ch.internettechnik.anouman.backend.entity.Buchhaltung;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@Repository(forEntity = Buchhaltung.class)
public interface BuchhaltungDeltaspikeRepository extends EntityRepository<Buchhaltung, Long> {
    List<Buchhaltung> findByBezeichnungLikeIgnoreCase(String bezeichnung);
}

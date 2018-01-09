package ch.internettechnik.anouman.backend.session.deltaspike.jpa.repository;

import ch.internettechnik.anouman.backend.entity.Artikelkategorie;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@Repository(forEntity = Artikelkategorie.class)
public interface ArtikelkategorieDeltaspikeRepository extends EntityRepository<Artikelkategorie, Long> {
    List<Artikelkategorie> findByBezeichnungLikeIgnoreCase(String bezeichnung);
}

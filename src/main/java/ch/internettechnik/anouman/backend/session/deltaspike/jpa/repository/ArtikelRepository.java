package ch.internettechnik.anouman.backend.session.deltaspike.jpa.repository;

import ch.internettechnik.anouman.backend.entity.Artikel;
import ch.internettechnik.anouman.backend.entity.Artikelkategorie;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@Repository(forEntity = Artikel.class)
public interface ArtikelRepository extends EntityRepository<Artikel, Long> {
    List<Artikel> findByBezeichnungLikeIgnoreCase(String bezeichnung);

    List<Artikel> findByArtikelkategorie(Artikelkategorie artikelkategorie);

    List<Artikel> findByArtikelkategorieAndBezeichnungLikeIgnoreCase(Artikelkategorie artikelkategorie, String bezeichnung);
}

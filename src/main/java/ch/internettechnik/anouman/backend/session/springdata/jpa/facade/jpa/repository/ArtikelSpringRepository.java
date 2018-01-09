package ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.repository;

import ch.internettechnik.anouman.backend.entity.Artikel;
import ch.internettechnik.anouman.backend.entity.Artikelkategorie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArtikelSpringRepository extends JpaRepository<Artikel, Long> {
    List<Artikel> findByBezeichnungLikeIgnoreCase(String bezeichnung);

    List<Artikel> findByArtikelkategorie(Artikelkategorie artikelkategorie);

    List<Artikel> findByArtikelkategorieAndBezeichnungLikeIgnoreCase(Artikelkategorie artikelkategorie, String bezeichnung);
}

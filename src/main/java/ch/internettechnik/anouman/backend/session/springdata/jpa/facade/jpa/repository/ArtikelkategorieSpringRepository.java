package ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.repository;

import ch.internettechnik.anouman.backend.entity.Artikelkategorie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArtikelkategorieSpringRepository extends JpaRepository<Artikelkategorie, Long> {
    List<Artikelkategorie> findByBezeichnungLikeIgnoreCase(String bezeichnung);
}

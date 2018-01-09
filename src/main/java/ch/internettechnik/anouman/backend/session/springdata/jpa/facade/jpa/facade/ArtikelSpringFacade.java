package ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.facade;

import ch.internettechnik.anouman.backend.entity.Artikel;
import ch.internettechnik.anouman.backend.entity.Artikelkategorie;
import ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.repository.ArtikelSpringRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class ArtikelSpringFacade {
    @Inject
    ArtikelSpringRepository repo;

    public Artikel findBy(Long id) {
        return repo.getOne(id);
    }

    public void delete(Artikel val) {
        repo.delete(val);
    }

    public Artikel save(Artikel val) {
        return repo.save(val);
    }

    public List<Artikel> findAll() {
        return repo.findAll();
    }

    public List<Artikel> findByBezeichnungLikeIgnoreCase(String bezeichnung) {
        return repo.getOneBezeichnungLikeIgnoreCase(bezeichnung);
    }

    public List<Artikel> findByArtikelkategorieAndBezeichnungLikeIgnoreCase(Artikelkategorie artikelkategorie, String bezeichnung) {
        return repo.getOneArtikelkategorieAndBezeichnungLikeIgnoreCase(artikelkategorie, bezeichnung);
    }

    public List<Artikel> findByArtikelkategorie(Artikelkategorie artikelkategorie) {
        return repo.getOneArtikelkategorie(artikelkategorie);
    }
}

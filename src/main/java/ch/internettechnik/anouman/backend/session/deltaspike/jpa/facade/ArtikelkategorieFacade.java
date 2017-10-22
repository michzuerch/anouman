package ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade;

import ch.internettechnik.anouman.backend.entity.Artikelkategorie;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.repository.ArtikelkategorieRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class ArtikelkategorieFacade {
    @Inject
    ArtikelkategorieRepository repo;

    public Artikelkategorie findBy(Long id) {
        return repo.findBy(id);
    }

    public void delete(Artikelkategorie val) {
        repo.attachAndRemove(val);
    }

    public Artikelkategorie save(Artikelkategorie val) {
        return repo.save(val);
    }

    public List<Artikelkategorie> findAll() {
        return repo.findAll();
    }

    public List<Artikelkategorie> findByBezeichnungLikeIgnoreCase(String bezeichnung) {
        return repo.findByBezeichnungLikeIgnoreCase(bezeichnung);
    }

}

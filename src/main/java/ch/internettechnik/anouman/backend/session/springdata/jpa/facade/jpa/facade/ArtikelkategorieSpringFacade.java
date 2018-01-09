package ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.facade;

import ch.internettechnik.anouman.backend.entity.Artikelkategorie;
import ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.repository.ArtikelkategorieSpringRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class ArtikelkategorieSpringFacade {
    @Inject
    ArtikelkategorieSpringRepository repo;

    public Artikelkategorie findBy(Long id) {
        return repo.getOne(id);
    }

    public void delete(Artikelkategorie val) {
        repo.delete(val);
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

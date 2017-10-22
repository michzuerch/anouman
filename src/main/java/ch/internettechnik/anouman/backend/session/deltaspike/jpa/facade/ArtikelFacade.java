package ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade;

import ch.internettechnik.anouman.backend.entity.Artikel;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.repository.ArtikelRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class ArtikelFacade {
    @Inject
    ArtikelRepository repo;

    public Artikel findBy(Long id) {
        return repo.findBy(id);
    }

    public void delete(Artikel val) {
        repo.attachAndRemove(val);
    }

    public Artikel save(Artikel val) {
        return repo.save(val);
    }

    public List<Artikel> findAll() {
        return repo.findAll();
    }

    public List<Artikel> findByBezeichnungLikeIgnoreCase(String bezeichnung) {
        return repo.findByBezeichnungLikeIgnoreCase(bezeichnung);
    }

}

package ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.facade;

import ch.internettechnik.anouman.backend.entity.Artikelbild;
import ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.repository.ArtikelbildSpringRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class ArtikelbildSpringFacade {
    @Inject
    ArtikelbildSpringRepository repo;

    public Artikelbild findBy(Long id) {
        return repo.getOne(id);
    }

    public void delete(Artikelbild val) {
        repo.delete(val);
    }

    public Artikelbild save(Artikelbild val) {
        return repo.save(val);
    }

    public List<Artikelbild> findAll() {
        return repo.findAll();
    }

    public List<Artikelbild> findByTitelLikeIgnoreCase(String titel) {
        return repo.findByTitelLikeIgnoreCase(titel);
    }

}

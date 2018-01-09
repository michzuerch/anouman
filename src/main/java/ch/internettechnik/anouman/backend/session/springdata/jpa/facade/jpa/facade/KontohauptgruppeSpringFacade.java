package ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.facade;

import ch.internettechnik.anouman.backend.entity.Kontohauptgruppe;
import ch.internettechnik.anouman.backend.entity.Kontoklasse;
import ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.repository.KontohauptgruppeSpringRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class KontohauptgruppeSpringFacade {
    @Inject
    KontohauptgruppeSpringRepository repo;

    public Kontohauptgruppe findBy(Long id) {
        return repo.getOne(id);
    }

    public void delete(Kontohauptgruppe val) {
        repo.delete(val);
    }

    public Kontohauptgruppe save(Kontohauptgruppe val) {
        return repo.save(val);
    }

    public List<Kontohauptgruppe> findAll() {
        return repo.findAll();
    }

    public List<Kontohauptgruppe> findByBezeichnungLikeIgnoreCase(String bezeichnung) {
        return repo.getOneBezeichnungLikeIgnoreCase(bezeichnung);
    }

    public List<Kontohauptgruppe> findByKontoklasse(Kontoklasse kontoklasse) {
        return repo.getOneKontoklasse(kontoklasse);
    }
}

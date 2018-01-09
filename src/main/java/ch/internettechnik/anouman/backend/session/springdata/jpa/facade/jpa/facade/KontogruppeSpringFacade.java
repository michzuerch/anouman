package ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.facade;

import ch.internettechnik.anouman.backend.entity.Kontogruppe;
import ch.internettechnik.anouman.backend.entity.Kontohauptgruppe;
import ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.repository.KontogruppeSpringRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class KontogruppeSpringFacade {
    @Inject
    KontogruppeSpringRepository repo;

    public Kontogruppe findBy(Long id) {
        return repo.getOne(id);
    }

    public void delete(Kontogruppe val) {
        repo.delete(val);
    }

    public Kontogruppe save(Kontogruppe val) {
        return repo.save(val);
    }

    public List<Kontogruppe> findAll() {
        return repo.findAll();
    }

    public List<Kontogruppe> findByBezeichnungLikeIgnoreCase(String bezeichnung) {
        return repo.getOneBezeichnungLikeIgnoreCase(bezeichnung);
    }

    public List<Kontogruppe> findByKontohauptgruppe(Kontohauptgruppe kontohauptgruppe) {
        return repo.getOneKontohauptgruppe(kontohauptgruppe);
    }

}

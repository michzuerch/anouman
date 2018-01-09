package ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.facade;

import ch.internettechnik.anouman.backend.entity.Konto;
import ch.internettechnik.anouman.backend.entity.Kontogruppe;
import ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.repository.KontoSpringRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class KontoSpringFacade {
    @Inject
    KontoSpringRepository repo;

    public Konto findBy(Long id) {
        return repo.getOne(id);
    }

    public void delete(Konto val) {
        repo.delete(val);
    }

    public Konto save(Konto val) {
        return repo.save(val);
    }

    public List<Konto> findAll() {
        return repo.findAll();
    }

    public List<Konto> findByBezeichnungLikeIgnoreCase(String bezeichnung) {
        return repo.getOneBezeichnungLikeIgnoreCase(bezeichnung);
    }

    public List<Konto> findByKontogruppe(Kontogruppe kontogruppe) {
        return repo.getOneKontogruppe(kontogruppe);
    }

}

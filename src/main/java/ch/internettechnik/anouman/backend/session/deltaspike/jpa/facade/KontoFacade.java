package ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade;

import ch.internettechnik.anouman.backend.entity.Konto;
import ch.internettechnik.anouman.backend.entity.Kontogruppe;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.repository.KontoRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class KontoFacade {
    @Inject
    KontoRepository repo;

    public Konto findBy(Long id) {
        return repo.findBy(id);
    }

    public void delete(Konto val) {
        repo.attachAndRemove(val);
    }

    public Konto save(Konto val) {
        return repo.save(val);
    }

    public List<Konto> findAll() {
        return repo.findAll();
    }

    public List<Konto> findByBezeichnungLikeIgnoreCase(String bezeichnung) {
        return repo.findByBezeichnungLikeIgnoreCase(bezeichnung);
    }

    public List<Konto> findByKontogruppe(Kontogruppe kontogruppe) {
        return repo.findByKontogruppe(kontogruppe);
    }

}

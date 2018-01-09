package ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade;

import ch.internettechnik.anouman.backend.entity.Buchhaltung;
import ch.internettechnik.anouman.backend.entity.Mehrwertsteuercode;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.repository.MehrwertsteuercodeDeltaspikeRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class MehrwertsteuercodeDeltaspikeFacade {
    @Inject
    MehrwertsteuercodeDeltaspikeRepository repo;

    public Mehrwertsteuercode findBy(Long id) {
        return repo.findBy(id);
    }

    public void delete(Mehrwertsteuercode val) {
        repo.attachAndRemove(val);
    }

    public Mehrwertsteuercode save(Mehrwertsteuercode val) {
        return repo.save(val);
    }

    public List<Mehrwertsteuercode> findAll() {
        return repo.findAll();
    }

    public List<Mehrwertsteuercode> findByBuchhaltung(Buchhaltung buchhaltung) {
        return repo.findByBuchhaltung(buchhaltung);
    }

    public List<Mehrwertsteuercode> findByBezeichnungLikeIgnoreCase(String bezeichnung) {
        return repo.findByBezeichnungLikeIgnoreCase(bezeichnung);
    }

}

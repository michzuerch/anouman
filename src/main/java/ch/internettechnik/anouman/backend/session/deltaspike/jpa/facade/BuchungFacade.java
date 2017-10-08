package ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade;

import ch.internettechnik.anouman.backend.entity.Buchung;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.repository.BuchungRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class BuchungFacade {
    @Inject
    BuchungRepository repo;

    public Buchung findBy(Long id) {
        return repo.findBy(id);
    }

    public void delete(Buchung val) {
        repo.attachAndRemove(val);
    }

    public Buchung save(Buchung val) {
        return repo.save(val);
    }

    public List<Buchung> findAll() {
        return repo.findAll();
    }


}

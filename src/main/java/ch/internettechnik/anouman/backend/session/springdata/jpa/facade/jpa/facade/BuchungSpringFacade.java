package ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.facade;

import ch.internettechnik.anouman.backend.entity.Buchung;
import ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.repository.BuchungSpringRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class BuchungSpringFacade {
    @Inject
    BuchungSpringRepository repo;

    public Buchung findBy(Long id) {
        return repo.getOne(id);
    }

    public void delete(Buchung val) {
        repo.delete(val);
    }

    public Buchung save(Buchung val) {
        return repo.save(val);
    }

    public List<Buchung> findAll() {
        return repo.findAll();
    }


}

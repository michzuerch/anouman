package ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.facade;

import ch.internettechnik.anouman.backend.entity.Buchhaltung;
import ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.repository.BuchhaltungSpringRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class BuchhaltungSpringFacade {
    @Inject
    BuchhaltungSpringRepository repo;

    public Buchhaltung findBy(Long id) {
        return repo.getOne(id);
    }

    public void delete(Buchhaltung val) {
        repo.delete(val);
    }

    public Buchhaltung save(Buchhaltung val) {
        return repo.save(val);
    }

    public List<Buchhaltung> findAll() {
        return repo.findAll();
    }

    public List<Buchhaltung> findByBezeichnungLikeIgnoreCase(String bezeichnung) {
        return repo.getOneBezeichnungLikeIgnoreCase(bezeichnung);
    }

}

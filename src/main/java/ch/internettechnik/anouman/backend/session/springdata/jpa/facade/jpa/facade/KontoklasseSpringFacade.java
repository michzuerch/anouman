package ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.facade;

import ch.internettechnik.anouman.backend.entity.Buchhaltung;
import ch.internettechnik.anouman.backend.entity.Kontoklasse;
import ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.repository.KontoklasseSpringRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class KontoklasseSpringFacade {
    @Inject
    KontoklasseSpringRepository repo;

    public Kontoklasse findBy(Long id) {
        return repo.getOne(id);
    }

    public void delete(Kontoklasse val) {
        repo.delete(val);
    }

    public Kontoklasse save(Kontoklasse val) {
        return repo.save(val);
    }

    public List<Kontoklasse> findAll() {
        return repo.findAll();
    }

    public List<Kontoklasse> findByBezeichnungLikeIgnoreCase(String bezeichnung) {
        return repo.getOneBezeichnungLikeIgnoreCase(bezeichnung);
    }

    public List<Kontoklasse> findByBuchhaltung(Buchhaltung buchhaltung) {
        return repo.getOneBuchhaltung(buchhaltung);
    }
}

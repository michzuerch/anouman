package ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.facade;

import ch.internettechnik.anouman.backend.entity.Buchhaltung;
import ch.internettechnik.anouman.backend.entity.Mehrwertsteuercode;
import ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.repository.MehrwertsteuercodeSpringRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class MehrwertsteuercodeSpringFacade {
    @Inject
    MehrwertsteuercodeSpringRepository repo;

    public Mehrwertsteuercode findBy(Long id) {
        return repo.getOne(id);
    }

    public void delete(Mehrwertsteuercode val) {
        repo.delete(val);
    }

    public Mehrwertsteuercode save(Mehrwertsteuercode val) {
        return repo.save(val);
    }

    public List<Mehrwertsteuercode> findAll() {
        return repo.findAll();
    }

    public List<Mehrwertsteuercode> findByBuchhaltung(Buchhaltung buchhaltung) {
        return repo.getOneBuchhaltung(buchhaltung);
    }

    public List<Mehrwertsteuercode> findByBezeichnungLikeIgnoreCase(String bezeichnung) {
        return repo.getOneBezeichnungLikeIgnoreCase(bezeichnung);
    }

}

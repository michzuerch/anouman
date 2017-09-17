package ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade;

import ch.internettechnik.anouman.backend.entity.Adresse;
import ch.internettechnik.anouman.backend.entity.Rechnung;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.repository.RechnungRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class RechnungFacade {
    @Inject
    RechnungRepository repo;

    public Rechnung findBy(Long id) {
        return repo.findBy(id);
    }

    public void delete(Rechnung val) {
        repo.attachAndRemove(val);
    }

    public Rechnung save(Rechnung val) {
        return repo.save(val);
    }

    public List<Rechnung> findAll() {
        return repo.findAll();
    }

    public List<Rechnung> findByBezeichnungLikeIgnoreCase(String bezeichnung) {
        return repo.findByBezeichnungLikeIgnoreCase(bezeichnung);
    }

    public List<Rechnung> findByAdresse(Adresse adresse) {
        return repo.findByAdresse(adresse);
    }

    public List<Rechnung> findByAdresseAndBezeichnungLikeIgnoreCase(Adresse adresse, String bezeichnung) {
        return repo.findByAdresseAndBezeichnungLikeIgnoreCase(adresse, bezeichnung);
    }


}

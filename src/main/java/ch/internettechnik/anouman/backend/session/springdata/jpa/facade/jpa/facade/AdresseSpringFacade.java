package ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.facade;

import ch.internettechnik.anouman.backend.entity.Adresse;
import ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.repository.AdresseSpringRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class AdresseSpringFacade {
    @Inject
    AdresseSpringRepository repo;

    public List<Adresse> findAll() {
        return repo.findAll();
    }

    public Adresse findBy(Long id) {
        return repo.getOne(id);
    }

    public void delete(Adresse val) {
        repo.delete(val);
    }

    public Adresse save(Adresse val) {
        return repo.save(val);
    }

    public List<Adresse> findByFirmaLikeIgnoreCaseAndNachnameLikeIgnoreCaseAndOrtLikeIgnoreCase(String firma, String nachname, String ort) {
        return repo.findByFirmaLikeIgnoreCaseAndNachnameLikeIgnoreCaseAndOrtLikeIgnoreCase(firma, nachname, ort);
    }

    public List<Adresse> findByFirmaLikeIgnoreCaseAndNachnameLikeIgnoreCase(String firma, String nachname) {
        return repo.findByFirmaLikeIgnoreCaseAndNachnameLikeIgnoreCase(firma, nachname);
    }

    public List<Adresse> findByNachnameLikeIgnoreCaseAndOrtLikeIgnoreCase(String nachname, String ort) {
        return repo.findByNachnameLikeIgnoreCaseAndOrtLikeIgnoreCase(nachname, ort);
    }

    public List<Adresse> findByFirmaLikeIgnoreCaseAndOrtLikeIgnoreCase(String firma, String ort) {
        return repo.findByFirmaLikeIgnoreCaseAndOrtLikeIgnoreCase(firma, ort);
    }

    public List<Adresse> findByNachnameLikeIgnoreCase(String nachname) {
        return repo.findByNachnameLikeIgnoreCase(nachname);
    }

    public List<Adresse> findByFirmaLikeIgnoreCase(String firma) {
        return repo.findByFirmaLikeIgnoreCase(firma);
    }

    public List<Adresse> findByOrtLikeIgnoreCase(String ort) {
        return repo.findByOrtLikeIgnoreCase(ort);
    }
}

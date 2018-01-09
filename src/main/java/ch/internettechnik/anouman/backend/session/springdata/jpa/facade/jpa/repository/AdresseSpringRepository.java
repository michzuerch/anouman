package ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.repository;

import ch.internettechnik.anouman.backend.entity.Adresse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdresseSpringRepository extends JpaRepository<Adresse, Long> {
    List<Adresse> findByFirmaLikeIgnoreCaseAndNachnameLikeIgnoreCaseAndOrtLikeIgnoreCase(String firma, String nachname, String ort);

    List<Adresse> findByNachnameLikeIgnoreCaseAndOrtLikeIgnoreCase(String nachname, String ort);

    List<Adresse> findByFirmaLikeIgnoreCaseAndNachnameLikeIgnoreCase(String firma, String nachname);

    List<Adresse> findByFirmaLikeIgnoreCaseAndOrtLikeIgnoreCase(String firma, String Ort);

    List<Adresse> findByNachnameLikeIgnoreCase(String nachname);

    List<Adresse> findByFirmaLikeIgnoreCase(String firma);

    List<Adresse> findByOrtLikeIgnoreCase(String ort);
}

package ch.internettechnik.anouman.backend.session.deltaspike.jpa.repository;

import ch.internettechnik.anouman.backend.entity.Adresse;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@Repository(forEntity = Adresse.class)
public interface AdresseDeltaspikeRepository extends EntityRepository<Adresse, Long> {
    List<Adresse> findByFirmaLikeIgnoreCaseAndNachnameLikeIgnoreCaseAndOrtLikeIgnoreCase(String firma, String nachname, String ort);

    List<Adresse> findByNachnameLikeIgnoreCaseAndOrtLikeIgnoreCase(String nachname, String ort);

    List<Adresse> findByFirmaLikeIgnoreCaseAndNachnameLikeIgnoreCase(String firma, String nachname);

    List<Adresse> findByFirmaLikeIgnoreCaseAndOrtLikeIgnoreCase(String firma, String Ort);

    List<Adresse> findByNachnameLikeIgnoreCase(String nachname);

    List<Adresse> findByFirmaLikeIgnoreCase(String firma);

    List<Adresse> findByOrtLikeIgnoreCase(String ort);
}

package ch.internettechnik.anouman.backend.session.jpa.api;

import ch.internettechnik.anouman.backend.entity.Adresse;

import javax.ejb.Local;
import java.util.List;

/**
 * Created by michzuerch on 20.03.15.
 */
@Local
public interface AdresseService {
    void saveOrPersist(Adresse adresse);

    void delete(Adresse adresse);

    List<Adresse> findAll();

    List<Adresse> findByFirma(String firma);

    List<Adresse> findByFirmaNachname(String firma, String nachname);

    List<Adresse> findByFirmaNachnameOrt(String firma, String nachname, String ort);

    List<Adresse> findByNachnameOrt(String nachname, String ort);

    List<Adresse> findByFirmaOrt(String firma, String Ort);

    List<Adresse> findByNachname(String nachname);

    List<Adresse> findByOrt(String ort);

    List<Adresse> findByCriteria(String id, String firma, String nachname, String ort);


    Adresse findById(Long id);

}

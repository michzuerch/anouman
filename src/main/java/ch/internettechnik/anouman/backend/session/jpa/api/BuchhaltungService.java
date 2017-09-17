package ch.internettechnik.anouman.backend.session.jpa.api;

import ch.internettechnik.anouman.backend.entity.Buchhaltung;
import ch.internettechnik.anouman.backend.entity.Kontoklasse;

import javax.ejb.Local;
import java.util.List;

/**
 * Created by michzuerch on 07.08.15.
 */
@Local
public interface BuchhaltungService {
    void saveOrPersist(Buchhaltung buchhaltung);

    void delete(Buchhaltung buchhaltung);

    List<Buchhaltung> findAll();

    List<Buchhaltung> findByKontoklasse(Kontoklasse val);

    Buchhaltung findById(Long id);
}

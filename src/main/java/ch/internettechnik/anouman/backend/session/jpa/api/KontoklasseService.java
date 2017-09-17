package ch.internettechnik.anouman.backend.session.jpa.api;

import ch.internettechnik.anouman.backend.entity.Buchhaltung;
import ch.internettechnik.anouman.backend.entity.Kontoklasse;

import javax.ejb.Local;
import java.util.List;

@Local
public interface KontoklasseService {
    void saveOrPersist(Kontoklasse kontoklasse);

    void delete(Kontoklasse kontoklasse);

    List<Kontoklasse> findAll();

    Kontoklasse findById(Long id);

    Kontoklasse findByBuchhaltung(Buchhaltung value);
}

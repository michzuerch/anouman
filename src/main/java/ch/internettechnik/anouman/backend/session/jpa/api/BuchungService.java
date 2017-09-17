package ch.internettechnik.anouman.backend.session.jpa.api;

import ch.internettechnik.anouman.backend.entity.Buchhaltung;
import ch.internettechnik.anouman.backend.entity.Buchung;

import javax.ejb.Local;
import java.util.List;

/**
 * Created by michzuerch on 07.08.15.
 */
@Local
public interface BuchungService {
    void saveOrPersist(Buchung buchung);

    void delete(Buchung buchung);

    List<Buchung> findAll();

    List<Buchung> findByCriteria(Buchhaltung buchhaltung);

    Buchung findById(Long id);
}

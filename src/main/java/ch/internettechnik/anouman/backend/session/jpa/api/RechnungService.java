package ch.internettechnik.anouman.backend.session.jpa.api;

import ch.internettechnik.anouman.backend.entity.Adresse;
import ch.internettechnik.anouman.backend.entity.Rechnung;

import javax.ejb.Local;
import java.util.Date;
import java.util.List;

/**
 * Created by michzuerch on 22.02.15.
 */
@Local
public interface RechnungService {
    void saveOrPersist(Rechnung rechnung);

    void delete(Rechnung rechnung);

    List<Rechnung> findAll();

    List<Rechnung> findByCriteria(String id, String bezeichnung, Adresse adresse, Date startdatum, Date enddatum);

    List<Rechnung> findByCriteriaAdresse(Adresse adresse);

    List<Rechnung> findByCriteriaBezeichnungAdresse(String bezeichnung, Adresse adresse);

    List<Rechnung> findByBezeichnung(String bezeichnung);

    Rechnung findById(Long id);

}

package ch.internettechnik.anouman.backend.session.jpa.api;

import ch.internettechnik.anouman.backend.entity.Rechnung;
import ch.internettechnik.anouman.backend.entity.Rechnungsposition;

import javax.ejb.Local;
import java.util.List;

/**
 * Created by michzuerch on 20.03.15.
 */
@Local
public interface RechnungspositionService {
    void saveOrPersist(Rechnungsposition rechnungsposition);

    void delete(Rechnungsposition rechnungsposition);

    List<Rechnungsposition> findAll();

    List<Rechnungsposition> findByCriteria(String id, Rechnung rechnung);

    List<Rechnungsposition> findByCriteria(Rechnung rechnung);

    List<Rechnungsposition> findByBezeichnung(String bezeichnung);

    Rechnungsposition findById(Long id);

}

package ch.internettechnik.anouman.backend.session.jpa.api;

import ch.internettechnik.anouman.backend.entity.Aufwand;
import ch.internettechnik.anouman.backend.entity.Rechnung;

import javax.ejb.Local;
import java.util.Date;
import java.util.List;

@Local
public interface AufwandService {
    void saveOrPersist(Aufwand aufwand);

    void delete(Aufwand aufwand);

    List<Aufwand> findAll();

    List<Aufwand> findByCriteria(String id, Rechnung rechnung);

    List<Aufwand> findByCriteria(Rechnung rechnung);

    List<Aufwand> findByCriteria(Date start, Date end);

    List<Aufwand> findByStartEndDate(Date start, Date ende);

    List<Aufwand> findByTitelLike(String titel);

    Aufwand findById(Long id);

}

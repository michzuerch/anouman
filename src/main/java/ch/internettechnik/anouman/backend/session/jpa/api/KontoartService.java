package ch.internettechnik.anouman.backend.session.jpa.api;

import ch.internettechnik.anouman.backend.entity.Kontoart;
import ch.internettechnik.anouman.backend.entity.Kontogruppe;

import javax.ejb.Local;
import java.util.List;

@Local
public interface KontoartService {
    void saveOrPersist(Kontoart kontoart);

    void delete(Kontoart kontoart);

    List<Kontoart> findAll();

    List<Kontoart> findByCriteria(Kontogruppe kontogruppe);

    Kontoart findById(Long id);

}

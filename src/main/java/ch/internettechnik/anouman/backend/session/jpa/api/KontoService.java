package ch.internettechnik.anouman.backend.session.jpa.api;

import ch.internettechnik.anouman.backend.entity.Konto;
import ch.internettechnik.anouman.backend.entity.Kontoart;

import javax.ejb.Local;
import java.util.List;

/**
 * Created by michzuerch on 07.08.15.
 */
@Local
public interface KontoService {
    void saveOrPersist(Konto konto);

    void delete(Konto konto);

    List<Konto> findAll();

    List<Konto> findByCriteria(Kontoart kontoart);

    Konto findById(Long id);

}

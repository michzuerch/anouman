package ch.internettechnik.anouman.backend.session.jpa.api;

import ch.internettechnik.anouman.backend.entity.Uzer;

import javax.ejb.Local;
import java.util.List;

/**
 * Created by michzuerch on 20.03.15.
 */
@Local
public interface UzerService {
    void saveOrPersist(Uzer uzer);

    void delete(Uzer uzer);

    List<Uzer> findAll();

    Uzer findById(Long id);

    Uzer findByName(String name);

}

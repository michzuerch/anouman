package ch.internettechnik.anouman.backend.session.jpa.api;

import ch.internettechnik.anouman.backend.entity.Buchhaltung;
import ch.internettechnik.anouman.backend.entity.Mehrwertsteuercode;

import javax.ejb.Local;
import java.util.List;

/**
 * Created by michzuerch on 07.08.15.
 */
@Local
public interface MehrwertsteuercodeService {
    void saveOrPersist(Mehrwertsteuercode mehrwertsteuercode);

    void delete(Mehrwertsteuercode mehrwertsteuercode);

    List<Mehrwertsteuercode> findAll();

    List<Mehrwertsteuercode> findByCriteria(Buchhaltung val);

    Mehrwertsteuercode findById(Long id);
}

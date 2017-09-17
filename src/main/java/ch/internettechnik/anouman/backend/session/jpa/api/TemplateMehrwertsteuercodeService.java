package ch.internettechnik.anouman.backend.session.jpa.api;

import ch.internettechnik.anouman.backend.entity.TemplateMehrwertsteuercode;

import javax.ejb.Local;
import java.util.List;

/**
 * Created by michzuerch on 07.08.15.
 */
@Local
public interface TemplateMehrwertsteuercodeService {
    void saveOrPersist(TemplateMehrwertsteuercode val);

    void delete(TemplateMehrwertsteuercode val);

    List<TemplateMehrwertsteuercode> findAll();

    //List<TemplateMehrwertsteuercode> findByCriteriaAdresse(Buchhaltung buchhaltung);

    TemplateMehrwertsteuercode findById(Long id);
}

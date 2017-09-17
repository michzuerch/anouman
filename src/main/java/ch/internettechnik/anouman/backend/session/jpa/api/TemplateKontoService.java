package ch.internettechnik.anouman.backend.session.jpa.api;

import ch.internettechnik.anouman.backend.entity.TemplateKonto;
import ch.internettechnik.anouman.backend.entity.TemplateKontoart;

import javax.ejb.Local;
import java.util.List;

/**
 * Created by michzuerch on 07.08.15.
 */
@Local
public interface TemplateKontoService {
    TemplateKonto saveOrPersist(TemplateKonto val);

    void delete(TemplateKonto val);

    List<TemplateKonto> findAll();

    List<TemplateKonto> findByCriteria(TemplateKontoart val);

    TemplateKonto findById(Long id);

}

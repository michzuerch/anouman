package ch.internettechnik.anouman.backend.session.jpa.api;

import ch.internettechnik.anouman.backend.entity.TemplateKontoart;
import ch.internettechnik.anouman.backend.entity.TemplateKontogruppe;

import javax.ejb.Local;
import java.util.List;

@Local
public interface TemplateKontoartService {
    TemplateKontoart saveOrPersist(TemplateKontoart kontoart);

    void delete(TemplateKontoart kontoart);

    List<TemplateKontoart> findAll();

    List<TemplateKontoart> findByCriteria(TemplateKontogruppe kontogruppe);

    TemplateKontoart findById(Long id);

}

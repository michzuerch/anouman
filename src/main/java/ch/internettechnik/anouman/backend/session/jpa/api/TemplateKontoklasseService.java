package ch.internettechnik.anouman.backend.session.jpa.api;

import ch.internettechnik.anouman.backend.entity.TemplateKontoklasse;

import javax.ejb.Local;
import java.util.List;

@Local
public interface TemplateKontoklasseService {
    TemplateKontoklasse saveOrPersist(TemplateKontoklasse kontoklasse);

    void delete(TemplateKontoklasse kontoklasse);

    List<TemplateKontoklasse> findAll();

    TemplateKontoklasse findById(Long id);

}

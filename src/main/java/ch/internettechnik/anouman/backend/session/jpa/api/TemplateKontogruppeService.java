package ch.internettechnik.anouman.backend.session.jpa.api;

import ch.internettechnik.anouman.backend.entity.TemplateKontogruppe;
import ch.internettechnik.anouman.backend.entity.TemplateKontoklasse;

import javax.ejb.Local;
import java.util.List;

@Local
public interface TemplateKontogruppeService {
    TemplateKontogruppe saveOrPersist(TemplateKontogruppe kontogruppe);

    void delete(TemplateKontogruppe kontogruppe);

    List<TemplateKontogruppe> findAll();

    List<TemplateKontogruppe> findByTemplateKontoklasse(TemplateKontoklasse kontoklasse);

    TemplateKontogruppe findById(Long id);

}

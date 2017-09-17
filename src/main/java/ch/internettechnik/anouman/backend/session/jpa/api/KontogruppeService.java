package ch.internettechnik.anouman.backend.session.jpa.api;

import ch.internettechnik.anouman.backend.entity.Kontogruppe;
import ch.internettechnik.anouman.backend.entity.Kontoklasse;

import javax.ejb.Local;
import java.util.List;

@Local
public interface KontogruppeService {
    void saveOrPersist(Kontogruppe kontogruppe);

    void delete(Kontogruppe kontogruppe);

    List<Kontogruppe> findAll();

    List<Kontogruppe> findByKontoklasse(Kontoklasse kontoklasse);

    Kontogruppe findById(Long id);

}

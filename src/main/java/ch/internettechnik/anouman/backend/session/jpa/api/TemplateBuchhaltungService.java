package ch.internettechnik.anouman.backend.session.jpa.api;

import ch.internettechnik.anouman.backend.entity.TemplateBuchhaltung;

import javax.ejb.Local;
import java.util.List;

/**
 * Created by michzuerch on 07.08.15.
 */
@Local
public interface TemplateBuchhaltungService {
    void saveOrPersist(TemplateBuchhaltung templateBuchhaltung);

    void delete(TemplateBuchhaltung templateBuchhaltung);

    List<TemplateBuchhaltung> findAll();

    TemplateBuchhaltung findById(Long id);
}

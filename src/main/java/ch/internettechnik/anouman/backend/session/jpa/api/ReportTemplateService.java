package ch.internettechnik.anouman.backend.session.jpa.api;

import ch.internettechnik.anouman.backend.entity.ReportTemplate;

import javax.ejb.Local;
import java.util.List;

/**
 * Created by michzuerch on 20.03.15.
 */
@Local
public interface ReportTemplateService {
    void saveOrPersist(ReportTemplate reportTemplate);

    void delete(ReportTemplate reportTemplate);

    List<ReportTemplate> findAll();

    List<ReportTemplate> findByCriteria(String id);


    ReportTemplate findById(Long id);
}

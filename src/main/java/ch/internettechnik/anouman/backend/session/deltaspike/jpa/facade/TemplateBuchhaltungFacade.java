package ch.internettechnik.anouman.backend.session.deltaspike.jpa.facade;

import ch.internettechnik.anouman.backend.entity.TemplateBuchhaltung;
import ch.internettechnik.anouman.backend.session.deltaspike.jpa.repository.TemplateBuchhaltungRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class TemplateBuchhaltungFacade {
    @Inject
    TemplateBuchhaltungRepository repo;

    public TemplateBuchhaltung findBy(Long id) {
        return repo.findBy(id);
    }

    public void delete(TemplateBuchhaltung val) {
        repo.attachAndRemove(val);
    }

    public TemplateBuchhaltung save(TemplateBuchhaltung val) {
        return repo.save(val);
    }

    public List<TemplateBuchhaltung> findAll() {
        return repo.findAll();
    }

    public List<TemplateBuchhaltung> findByBezeichnungLikeIgnoreCase(String bezeichnung) {
        return repo.findByBezeichnungLikeIgnoreCase(bezeichnung);
    }

}

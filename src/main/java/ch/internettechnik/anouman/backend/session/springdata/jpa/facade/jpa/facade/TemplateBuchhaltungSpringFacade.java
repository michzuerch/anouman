package ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.facade;

import ch.internettechnik.anouman.backend.entity.TemplateBuchhaltung;
import ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.repository.TemplateBuchhaltungSpringRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class TemplateBuchhaltungSpringFacade {
    @Inject
    TemplateBuchhaltungSpringRepository repo;

    public TemplateBuchhaltung findBy(Long id) {
        return repo.getOne(id);
    }

    public void delete(TemplateBuchhaltung val) {
        repo.delete(val);
    }

    public TemplateBuchhaltung save(TemplateBuchhaltung val) {
        return repo.save(val);
    }

    public List<TemplateBuchhaltung> findAll() {
        return repo.findAll();
    }

    public List<TemplateBuchhaltung> findByBezeichnungLikeIgnoreCase(String bezeichnung) {
        return repo.getOneBezeichnungLikeIgnoreCase(bezeichnung);
    }

}

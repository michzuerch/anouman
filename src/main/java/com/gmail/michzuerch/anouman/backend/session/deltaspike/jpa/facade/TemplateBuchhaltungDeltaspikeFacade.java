package com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.facade;

import com.gmail.michzuerch.anouman.backend.entity.TemplateBuchhaltung;
import com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.repository.TemplateBuchhaltungDeltaspikeRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class TemplateBuchhaltungDeltaspikeFacade {
    @Inject
    TemplateBuchhaltungDeltaspikeRepository repo;

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

package com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.facade;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class TemplateKontoklasseDeltaspikeFacade {
    @Inject
    com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.repository.TemplateKontoklasseDeltaspikeRepository repo;

    public com.gmail.michzuerch.anouman.backend.entity.TemplateKontoklasse findBy(Long id) {
        return repo.findBy(id);
    }

    public void delete(com.gmail.michzuerch.anouman.backend.entity.TemplateKontoklasse val) {
        repo.attachAndRemove(val);
    }

    public com.gmail.michzuerch.anouman.backend.entity.TemplateKontoklasse save(com.gmail.michzuerch.anouman.backend.entity.TemplateKontoklasse val) {
        return repo.save(val);
    }

    public List<com.gmail.michzuerch.anouman.backend.entity.TemplateKontoklasse> findAll() {
        return repo.findAll();
    }

    public List<com.gmail.michzuerch.anouman.backend.entity.TemplateKontoklasse> findByBezeichnungLikeIgnoreCase(String bezeichnung) {
        return repo.findByBezeichnungLikeIgnoreCase(bezeichnung);
    }

    public List<com.gmail.michzuerch.anouman.backend.entity.TemplateKontoklasse> findByTemplateBuchhaltung(com.gmail.michzuerch.anouman.backend.entity.TemplateBuchhaltung templateBuchhaltung) {
        return repo.findByTemplateBuchhaltung(templateBuchhaltung);
    }
}

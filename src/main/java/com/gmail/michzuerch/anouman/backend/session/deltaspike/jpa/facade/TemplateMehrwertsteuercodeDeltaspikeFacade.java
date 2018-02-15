package com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.facade;

import com.gmail.michzuerch.anouman.backend.entity.TemplateBuchhaltung;
import com.gmail.michzuerch.anouman.backend.entity.TemplateKonto;
import com.gmail.michzuerch.anouman.backend.entity.TemplateMehrwertsteuercode;
import com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.repository.TemplateMehrwertsteuercodeDeltaspikeRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class TemplateMehrwertsteuercodeDeltaspikeFacade {
    @Inject
    TemplateMehrwertsteuercodeDeltaspikeRepository repo;

    public TemplateMehrwertsteuercode findBy(Long id) {
        return repo.findBy(id);
    }

    public void delete(TemplateMehrwertsteuercode val) {
        repo.attachAndRemove(val);
    }

    public TemplateMehrwertsteuercode save(TemplateMehrwertsteuercode val) {
        return repo.save(val);
    }

    public List<TemplateMehrwertsteuercode> findAll() {
        return repo.findAll();
    }

    public List<TemplateMehrwertsteuercode> findByTemplateBuchhaltung(TemplateBuchhaltung templateBuchhaltung) {
        return repo.findByTemplateBuchhaltung(templateBuchhaltung);
    }

    public List<TemplateMehrwertsteuercode> findByBezeichnungLikeIgnoreCase(String bezeichnung) {
        return repo.findByBezeichnungLikeIgnoreCase(bezeichnung);
    }

    public TemplateMehrwertsteuercode findByTemplateKonto(TemplateKonto templateKonto) {
        return repo.findByTemplateKonto(templateKonto);
    }


}

package com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.facade;

import com.gmail.michzuerch.anouman.backend.entity.Kontohauptgruppe;
import com.gmail.michzuerch.anouman.backend.entity.Kontoklasse;
import com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.repository.KontohauptgruppeDeltaspikeRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class KontohauptgruppeDeltaspikeFacade {
    @Inject
    KontohauptgruppeDeltaspikeRepository repo;

    public Kontohauptgruppe findBy(Long id) {
        return repo.findBy(id);
    }

    public void delete(Kontohauptgruppe val) {
        repo.attachAndRemove(val);
    }

    public Kontohauptgruppe save(Kontohauptgruppe val) {
        return repo.save(val);
    }

    public List<Kontohauptgruppe> findAll() {
        return repo.findAll();
    }

    public List<Kontohauptgruppe> findByBezeichnungLikeIgnoreCase(String bezeichnung) {
        return repo.findByBezeichnungLikeIgnoreCase(bezeichnung);
    }

    public List<Kontohauptgruppe> findByKontoklasse(Kontoklasse kontoklasse) {
        return repo.findByKontoklasse(kontoklasse);
    }
}

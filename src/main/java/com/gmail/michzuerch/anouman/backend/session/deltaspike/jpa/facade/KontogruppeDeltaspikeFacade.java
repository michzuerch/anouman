package com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.facade;

import com.gmail.michzuerch.anouman.backend.entity.Kontogruppe;
import com.gmail.michzuerch.anouman.backend.entity.Kontohauptgruppe;
import com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.repository.KontogruppeDeltaspikeRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class KontogruppeDeltaspikeFacade {
    @Inject
    KontogruppeDeltaspikeRepository repo;

    public Kontogruppe findBy(Long id) {
        return repo.findBy(id);
    }

    public void delete(Kontogruppe val) {
        repo.attachAndRemove(val);
    }

    public Kontogruppe save(Kontogruppe val) {
        return repo.save(val);
    }

    public List<Kontogruppe> findAll() {
        return repo.findAll();
    }

    public List<Kontogruppe> findByBezeichnungLikeIgnoreCase(String bezeichnung) {
        return repo.findByBezeichnungLikeIgnoreCase(bezeichnung);
    }

    public List<Kontogruppe> findByKontohauptgruppe(Kontohauptgruppe kontohauptgruppe) {
        return repo.findByKontohauptgruppe(kontohauptgruppe);
    }

}

package com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.facade;

import com.gmail.michzuerch.anouman.backend.entity.Artikel;
import com.gmail.michzuerch.anouman.backend.entity.Artikelbild;
import com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.repository.ArtikelbildDeltaspikeRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class ArtikelbildDeltaspikeFacade {
    @Inject
    ArtikelbildDeltaspikeRepository repo;

    public Artikelbild findBy(Long id) {
        return repo.findBy(id);
    }

    public void delete(Artikelbild val) {
        repo.attachAndRemove(val);
    }

    public Artikelbild save(Artikelbild val) {
        return repo.save(val);
    }

    public List<Artikelbild> findAll() {
        return repo.findAll();
    }

    public List<Artikelbild> findByTitelLikeIgnoreCase(String titel) {
        return repo.findByTitelLikeIgnoreCase(titel);
    }

    public List<Artikelbild> findByArtikelAndTitelLikeIgnoreCase(Artikel artikel, String titel) {
        return repo.findByArtikelAndTitelLikeIngoreCase(artikel, titel);
    }

    public List<Artikelbild> findByArtikel(Artikel artikel) {
        return repo.findByArtikel(artikel);
    }
}

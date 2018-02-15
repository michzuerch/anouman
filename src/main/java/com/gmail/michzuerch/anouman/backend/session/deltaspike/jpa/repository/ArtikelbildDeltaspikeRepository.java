package com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.repository;

import com.gmail.michzuerch.anouman.backend.entity.Artikel;
import com.gmail.michzuerch.anouman.backend.entity.Artikelbild;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@Repository(forEntity = com.gmail.michzuerch.anouman.backend.entity.Artikelbild.class)
public interface ArtikelbildDeltaspikeRepository extends EntityRepository<com.gmail.michzuerch.anouman.backend.entity.Artikelbild, Long> {
    List<Artikelbild> findByTitelLikeIgnoreCase(String titel);

    List<Artikelbild> findByArtikelAndTitelLikeIngoreCase(Artikel artikel, String titel);

    List<Artikelbild> findByArtikel(Artikel artikel);

}

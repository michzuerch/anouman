package com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.repository;


import com.gmail.michzuerch.anouman.backend.entity.Buchhaltung;
import com.gmail.michzuerch.anouman.backend.entity.Kontoklasse;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@Repository(forEntity = Kontoklasse.class)
public interface KontoklasseDeltaspikeRepository extends EntityRepository<Kontoklasse, Long> {
    List<Kontoklasse> findByBezeichnungLikeIgnoreCase(String bezeichnung);

    List<Kontoklasse> findByBuchhaltung(Buchhaltung buchhaltung);
}

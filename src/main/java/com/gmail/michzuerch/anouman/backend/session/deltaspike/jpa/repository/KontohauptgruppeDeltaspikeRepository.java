package com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.repository;


import com.gmail.michzuerch.anouman.backend.entity.Kontohauptgruppe;
import com.gmail.michzuerch.anouman.backend.entity.Kontoklasse;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@Repository(forEntity = Kontohauptgruppe.class)
public interface KontohauptgruppeDeltaspikeRepository extends EntityRepository<Kontohauptgruppe, Long> {
    List<Kontohauptgruppe> findByBezeichnungLikeIgnoreCase(String bezeichnung);

    List<Kontohauptgruppe> findByKontoklasse(Kontoklasse kontoklasse);
}

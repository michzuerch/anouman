package com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.repository;


import com.gmail.michzuerch.anouman.backend.entity.Kontogruppe;
import com.gmail.michzuerch.anouman.backend.entity.Kontohauptgruppe;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@Repository(forEntity = Kontogruppe.class)
public interface KontogruppeDeltaspikeRepository extends EntityRepository<Kontogruppe, Long> {
    List<Kontogruppe> findByBezeichnungLikeIgnoreCase(String bezeichnung);

    List<Kontogruppe> findByKontohauptgruppe(Kontohauptgruppe kontohauptgruppe);
}

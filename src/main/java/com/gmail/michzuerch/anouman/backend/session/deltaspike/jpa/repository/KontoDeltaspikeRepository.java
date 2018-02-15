package com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.repository;


import com.gmail.michzuerch.anouman.backend.entity.Konto;
import com.gmail.michzuerch.anouman.backend.entity.Kontogruppe;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@Repository(forEntity = Konto.class)
public interface KontoDeltaspikeRepository extends EntityRepository<Konto, Long> {
    List<Konto> findByBezeichnungLikeIgnoreCase(String bezeichnung);

    List<Konto> findByKontogruppe(Kontogruppe kontogruppe);
}

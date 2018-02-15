package com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.repository;

import com.gmail.michzuerch.anouman.backend.entity.Rechnung;
import com.gmail.michzuerch.anouman.backend.entity.Rechnungsposition;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@Repository(forEntity = Rechnungsposition.class)
public interface RechnungspositionDeltaspikeRepository extends EntityRepository<Rechnungsposition, Long> {
    List<Rechnungsposition> findByRechnung(Rechnung rechnung);

    List<Rechnungsposition> findByBezeichnungLikeIgnoreCase(String bezeichnung);

    List<Rechnungsposition> findByRechnungAndBezeichnungLikeIgnoreCase(Rechnung rechnung, String bezeichnung);

}

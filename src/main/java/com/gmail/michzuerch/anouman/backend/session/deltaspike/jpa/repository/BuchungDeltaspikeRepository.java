package com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.repository;


import com.gmail.michzuerch.anouman.backend.entity.Buchung;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = Buchung.class)
public interface BuchungDeltaspikeRepository extends EntityRepository<Buchung, Long> {
}

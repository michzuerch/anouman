package com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.repository;


import com.gmail.michzuerch.anouman.backend.entity.TemplateBuchhaltung;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@Repository(forEntity = TemplateBuchhaltung.class)
public interface TemplateBuchhaltungDeltaspikeRepository extends EntityRepository<TemplateBuchhaltung, Long> {
    List<TemplateBuchhaltung> findByBezeichnungLikeIgnoreCase(String bezeichnung);
}

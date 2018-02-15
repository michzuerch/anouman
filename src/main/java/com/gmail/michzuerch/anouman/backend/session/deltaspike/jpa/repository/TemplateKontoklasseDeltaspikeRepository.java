package com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.repository;


import com.gmail.michzuerch.anouman.backend.entity.TemplateBuchhaltung;
import com.gmail.michzuerch.anouman.backend.entity.TemplateKontoklasse;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@Repository(forEntity = TemplateKontoklasse.class)
public interface TemplateKontoklasseDeltaspikeRepository extends EntityRepository<TemplateKontoklasse, Long> {
    List<TemplateKontoklasse> findByBezeichnungLikeIgnoreCase(String bezeichnung);

    List<TemplateKontoklasse> findByTemplateBuchhaltung(TemplateBuchhaltung templateBuchhaltung);
}

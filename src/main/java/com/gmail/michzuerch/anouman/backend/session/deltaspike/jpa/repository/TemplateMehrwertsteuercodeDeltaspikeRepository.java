package com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.repository;


import com.gmail.michzuerch.anouman.backend.entity.TemplateBuchhaltung;
import com.gmail.michzuerch.anouman.backend.entity.TemplateKonto;
import com.gmail.michzuerch.anouman.backend.entity.TemplateMehrwertsteuercode;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@Repository(forEntity = TemplateMehrwertsteuercode.class)
public interface TemplateMehrwertsteuercodeDeltaspikeRepository extends EntityRepository<TemplateMehrwertsteuercode, Long> {
    List<TemplateMehrwertsteuercode> findByTemplateBuchhaltung(TemplateBuchhaltung templateBuchhaltung);

    List<TemplateMehrwertsteuercode> findByBezeichnungLikeIgnoreCase(String bezeichnung);

    TemplateMehrwertsteuercode findByTemplateKonto(TemplateKonto templateKonto);
}

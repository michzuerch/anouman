package com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.repository;


import com.gmail.michzuerch.anouman.backend.entity.TemplateKontogruppe;
import com.gmail.michzuerch.anouman.backend.entity.TemplateKontohauptgruppe;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@Repository(forEntity = TemplateKontogruppe.class)
public interface TemplateKontogruppeDeltaspikeRepository extends EntityRepository<TemplateKontogruppe, Long> {
    List<TemplateKontogruppe> findByBezeichnungLikeIgnoreCase(String bezeichnung);

    List<TemplateKontogruppe> findByTemplateKontohauptgruppe(TemplateKontohauptgruppe templateKontohauptgruppe);

}

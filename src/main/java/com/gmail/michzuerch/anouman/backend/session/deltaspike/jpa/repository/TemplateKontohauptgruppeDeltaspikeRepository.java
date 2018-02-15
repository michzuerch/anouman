package com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.repository;


import com.gmail.michzuerch.anouman.backend.entity.TemplateKontohauptgruppe;
import com.gmail.michzuerch.anouman.backend.entity.TemplateKontoklasse;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@Repository(forEntity = TemplateKontohauptgruppe.class)
public interface TemplateKontohauptgruppeDeltaspikeRepository extends EntityRepository<TemplateKontohauptgruppe, Long> {
    List<TemplateKontohauptgruppe> findByBezeichnungLikeIgnoreCase(String bezeichnung);

    List<TemplateKontohauptgruppe> findByTemplateKontoklasse(TemplateKontoklasse templateKontoklasse);
}

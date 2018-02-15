package com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.repository;


import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@Repository(forEntity = com.gmail.michzuerch.anouman.backend.entity.EditorTest.class)
public interface EditorTestDeltaspikeRepository extends EntityRepository<com.gmail.michzuerch.anouman.backend.entity.EditorTest, Long> {
    List<com.gmail.michzuerch.anouman.backend.entity.EditorTest> findByErster(String erster);

    List<com.gmail.michzuerch.anouman.backend.entity.EditorTest> findByErsterLikeIgnoreCase(String filter);
}

package com.gmail.michzuerch.anouman.backend.session.deltaspike.jpa.repository;


import com.gmail.michzuerch.anouman.backend.entity.ImageTest;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@Repository(forEntity = ImageTest.class)
public interface ImageTestDeltaspikeRepository extends EntityRepository<ImageTest, Long> {
    List<ImageTest> findByTitelLikeIgnoreCase(String titel);
}

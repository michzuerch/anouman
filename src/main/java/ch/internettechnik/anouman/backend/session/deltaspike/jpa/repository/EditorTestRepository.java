package ch.internettechnik.anouman.backend.session.deltaspike.jpa.repository;


import ch.internettechnik.anouman.backend.entity.EditorTest;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@Repository(forEntity = EditorTest.class)
public interface EditorTestRepository extends EntityRepository<EditorTest, Long> {
    List<EditorTest> findByErster(String erster);

    List<EditorTest> findByErsterLikeIgnoreCase(String filter);
}

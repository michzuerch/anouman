package ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.repository;


import ch.internettechnik.anouman.backend.entity.EditorTest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EditorTestSpringRepository extends JpaRepository<EditorTest, Long> {
    List<EditorTest> findByErster(String erster);

    List<EditorTest> findByErsterLikeIgnoreCase(String filter);
}

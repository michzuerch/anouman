package ch.internettechnik.anouman.backend.session.jpa.api;

import ch.internettechnik.anouman.backend.entity.EditorTest;

import javax.ejb.Local;
import java.util.List;

/**
 * Created by michzuerch on 20.03.15.
 */
@Local
public interface EditorTestService {
    void saveOrPersist(EditorTest val);

    void delete(EditorTest val);

    List<EditorTest> findAll();

    EditorTest findById(Long id);

    List<EditorTest> findByErsterLike(String erster);


}

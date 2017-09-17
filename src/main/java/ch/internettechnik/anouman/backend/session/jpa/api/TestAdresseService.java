package ch.internettechnik.anouman.backend.session.jpa.api;

import ch.internettechnik.anouman.backend.entity.TestAdresse;

import javax.ejb.Local;
import java.util.List;

@Local
public interface TestAdresseService {
    void saveOrPersist(TestAdresse adresse);

    void delete(TestAdresse adresse);

    List<TestAdresse> findAll();

    TestAdresse findById(Long id);
}

package ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.repository;

import ch.internettechnik.anouman.backend.entity.Uzer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UzerSpringRepository extends JpaRepository<Uzer, Long> {
}

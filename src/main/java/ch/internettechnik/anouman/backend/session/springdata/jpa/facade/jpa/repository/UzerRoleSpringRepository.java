package ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.repository;

import ch.internettechnik.anouman.backend.entity.UzerRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UzerRoleSpringRepository extends JpaRepository<UzerRole, Long> {
}

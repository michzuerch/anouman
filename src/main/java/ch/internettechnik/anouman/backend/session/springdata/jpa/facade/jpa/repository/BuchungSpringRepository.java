package ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.repository;


import ch.internettechnik.anouman.backend.entity.Buchung;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuchungSpringRepository extends JpaRepository<Buchung, Long> {
}

package ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.repository;

import ch.internettechnik.anouman.backend.entity.Artikelbild;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArtikelbildSpringRepository extends JpaRepository<Artikelbild, Long> {
    List<Artikelbild> findByTitelLikeIgnoreCase(String titel);
}

package ch.internettechnik.anouman.backend.session.deltaspike.jpa.repository;

import ch.internettechnik.anouman.backend.entity.Artikelbild;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@Repository(forEntity = Artikelbild.class)
public interface ArtikelbildRepository extends EntityRepository<Artikelbild, Long> {
    List<Artikelbild> findByTitelLikeIgnoreCase(String titel);
}

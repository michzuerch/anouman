package ch.internettechnik.anouman.backend.session.jpa.api;

import ch.internettechnik.anouman.backend.entity.UzerRole;

import javax.ejb.Local;
import java.util.List;

/**
 * Created by michzuerch on 20.03.15.
 */
@Local
public interface UzerRoleService {
    void saveOrPersist(UzerRole uzerRole);

    void delete(UzerRole uzerRole);

    List<UzerRole> findAll();

    UzerRole findById(Long id);

    List<UzerRole> findByRole(String role);

}

package ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.facade;

import ch.internettechnik.anouman.backend.entity.Adresse;
import ch.internettechnik.anouman.backend.entity.Rechnung;
import ch.internettechnik.anouman.backend.session.springdata.jpa.facade.jpa.repository.RechnungSpringRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Stateless
@Path("/RechnungDeltaspikeFacade")
public class RechnungSpringFacade {
    @Inject
    RechnungSpringRepository repo;

    @GET
    @Path("/findBy/{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Rechnung findBy(@PathParam(value = "id") Long id) {
        return repo.getOne(id);
    }

    public void delete(Rechnung val) {
        repo.delete(val);
    }

    public Rechnung save(Rechnung val) {
        return repo.save(val);
    }

    @GET
    @Path("/findAll")
    @Produces(MediaType.APPLICATION_XML)
    public List<Rechnung> findAll() {
        return repo.findAll();
    }

    public List<Rechnung> findByBezeichnungLikeIgnoreCase(String bezeichnung) {
        return repo.getOneBezeichnungLikeIgnoreCase(bezeichnung);
    }

    public List<Rechnung> findByAdresse(Adresse adresse) {
        return repo.getOneAdresse(adresse);
    }

    public List<Rechnung> findByAdresseAndBezeichnungLikeIgnoreCase(Adresse adresse, String bezeichnung) {
        return repo.getOneAdresseAndBezeichnungLikeIgnoreCase(adresse, bezeichnung);
    }


}

package ch.internettechnik.anouman.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by michzuerch on 04.02.16.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "Uzer.findAll", query = "SELECT u FROM Uzer u"),
        @NamedQuery(name = "Uzer.findById", query = "SELECT u FROM Uzer u where u.id = :id"),
        @NamedQuery(name = "Uzer.findByName", query = "SELECT u FROM Uzer u where u.principal = :name")
})
public class Uzer extends AbstractEntity {

    //@todo unique entfernt für Testdaten
    //@Column(unique = true)
    @NotNull
    @Size(min = 2)
    private String principal;

    @Column
    @NotNull
    @Size(min = 3)
    private String pazzword;

    @Column
    private String description;

    @ManyToMany(mappedBy = "uzers", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private Set<UzerRole> roles = new HashSet<UzerRole>();

    @Transient
    public int getAnzahlUzerRoles() {
        int t = getRoles().size();
        return t;
    }


    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getPazzword() {
        return pazzword;
    }

    public void setPazzword(String pazzword) {
        this.pazzword = pazzword;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<UzerRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UzerRole> roles) {
        this.roles = roles;
    }

    public void addUzerRole(UzerRole uzerRole) {
        roles.add(uzerRole);
        uzerRole.getUzers().add(this);
    }

    public void removeUzerRole(UzerRole uzerRole) {
        roles.remove(uzerRole);
        uzerRole.getUzers().remove(this);
    }

    public void remove() {
        for (UzerRole uzerRole : new ArrayList<>(roles)) {
            removeUzerRole(uzerRole);
        }
    }

    @Override
    public String toString() {
        return "Uzer{" +
                "principal='" + principal + '\'' +
                ", pazzword='" + pazzword + '\'' +
                ", roles=" + roles +
                '}';
    }
}
